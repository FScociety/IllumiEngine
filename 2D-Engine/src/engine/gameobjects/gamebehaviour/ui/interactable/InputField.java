package engine.gameobjects.gamebehaviour.ui.interactable;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import engine.game.GameContainer;
import engine.game.Window;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.Text;
import engine.input.listener.ButtonListener;
import engine.input.listener.InputFieldListener;
import engine.math.Vector2;

public class InputField extends GameBehaviour implements KeyListener, ButtonListener {
	
	private Vector2 size;
	private boolean clicked;
	private Color[] colors = {Color.GRAY, Color.BLUE};
	private Button b;
	private String text;
	private int posInText;
	
	public InputField(Vector2 size) {
		//Reading the user input
		GameContainer.window.addKeyListener(this); 
		
		this.size = size;
	}
	
	public void start() {
		//Create Button, so its clickable
		b = new Button(Vector2.substract(size, 5)); 
		b.addButtonListener(this);
		this.gameObject.addComponent(b);
	}
	
	public void update() {
		
	}
	
	public void render() {
		//RENDER: Background (Select State)
		this.d.setColor(colors[clicked ? 1 : 0]);
		this.d.fillRect(this.size);
		
		//RENDER: Typer
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ButtonClicked() {
		this.clicked = !this.clicked; //Flip the click state
	}

	@Override
	public void ButtonHover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ButtonPress() {
		// TODO Auto-generated method stub
		
	}
	
	
}
/*	public static InputField inputFieldSelected;
	public static int MODE_EDIT = 0;
	public static int MODE_REPLACE = 1;
	private Button button;
	private boolean captureText;
	private Vector2 size;
	private int maxLength;
	private int valueInText;
	private int cursorX;
	private float blinking;
	private Color c;
	public int type;

	private ArrayList<InputFieldListener> listener;

	public InputField(final Color c, final Vector2 size, final int maxLength) {
		this.captureText = false;
		this.valueInText = 0;
		this.cursorX = 0;
		this.blinking = 0.0f;
		this.listener = new ArrayList<InputFieldListener>();
		this.size = size;
		this.setMaxLength(maxLength);
		this.c = c;
	}

	public void addInputFieldListener(final InputFieldListener ifl) {
		this.listener.add(ifl);
	}

	public int getMaxLength() {
		return this.maxLength;
	}

	public String getText() {
		return this.button.text;
	}

	@Override
	public void keyPressed(final KeyEvent e) {
	}

	@Override
	public void keyReleased(final KeyEvent e) {
	}

	@Override
	public void keyTyped(final KeyEvent e) {
		if (this.captureText && e != null) {
			if (e.getKeyChar() == '\b' && this.button.text.length() >= 1) {
				this.button.text = ((this.valueInText != 0)
						? (String.valueOf(this.button.text.substring(0, this.valueInText - 1))
								+ this.button.text.substring(this.valueInText, this.button.text.length()))
						: this.button.text);
				this.button.updateTextSize();
				this.valueInText -= ((this.valueInText > 0) ? 1 : 0);
				this.updateCursorX();
			} else if (e.getKeyChar() == '\n') {
				this.setCapture(false);
			} else if (e.getKeyChar() != '\b'
					&& this.button.text.length() < ((this.maxLength == 0) ? Integer.MAX_VALUE : this.maxLength)) {
				if (this.valueInText == this.button.text.length()) {
					this.button.text = String.valueOf(this.button.text) + e.getKeyChar();
				} else {
					this.button.text = String.valueOf(this.button.text.substring(0, this.valueInText))
							+ e.getKeyChar()
							+ this.button.text.substring(this.valueInText, this.button.text.length());
				}
				++this.valueInText;
				this.button.updateTextSize();
				this.updateCursorX();
			}
		}
	}

	@Override
	public void render() {
		if (this.blinking >= 2.0f) {
			this.d.setColor(Color.WHITE);
			this.d.fillRect(new Vector2(this.cursorX, (int) this.gameObject.getTransformWithCaution().position.y), new Vector2(3, this.size.y));
		}
	}

	private void setCapture(final boolean Capture) {
		if (Capture && InputField.inputFieldSelected == null) {
			InputField.inputFieldSelected = this;
			if (this.type == InputField.MODE_REPLACE) {
				this.button.text = "";
			}
		} else if (!Capture) {
			InputField.inputFieldSelected = null;
			this.blinking = 0.0f;
			for (int i = 0; i < this.listener.size(); ++i) {
				this.listener.get(i).TextEdited(this.getText());
			}
		} else if (InputField.inputFieldSelected != null) {
			return;
		}
		this.captureText = Capture;
	}

	public void setMaxLength(final int maxLength) {
		this.maxLength = maxLength;
	}

	public void setText(final String text) {
		this.button.text = text;
		this.button.updateTextSize();
	}

	@Override
	public void start() {
		GameContainer.window.addKeyListener(this);
		final GameObject buttonObj = new GameObject(new Vector2(0), this.gameObject);
		(this.button = new Button(this.size)).setBaseColor(this.c);
		this.button.setWire(true);
		buttonObj.addComponent(this.button);
		this.button.addButtonListener(new ButtonListener() {
			@Override
			public void ButtonClicked() {
				InputField.this.setCapture(true);
				InputField.this.updateCursorX();
			}

			@Override
			public void ButtonHover() {
			}

			@Override
			public void ButtonPress() {
			}
		});
	}

	@Override
	public void update() {
		if (GameContainer.input.isButtonDown(1) && (this.gc.getInput().getMouseX() < this.gameObject.getPosX()
				|| GameContainer.input.getMouseX() > this.gameObject.getPosX() + this.width
				|| GameContainer.input.getMouseY() < this.gameObject.getPosY()
				|| GameContainer.input.getInput().getMouseY() > this.gameObject.getPosY() + this.height)) {
			this.setCapture(false);
		} else if (this.gc.getInput().isKeyDown(37)) {
			this.valueInText -= ((this.valueInText > 0) ? 1 : 0);
			this.updateCursorX();
		} else if (this.gc.getInput().isKeyDown(39)) {
			this.valueInText += ((this.valueInText < this.button.text.text.length()) ? 1 : 0);
			this.updateCursorX();
		} else if (this.captureText) {
			this.blinking += 0.1f;
			if (this.blinking >= 5.0f) {
				this.blinking = 0.0f;
			}
		}
	}

	private void updateCursorX() {
		// System.out.println(this.g.getFontMetrics(this.button.text.getFont()).stringWidth(this.button.text.text.substring(0,
		// this.valueInText)));
		this.cursorX = (int) (this.button.text.getGameObject().getPosX()
				- this.g.getFontMetrics(this.button.text.getFont()).stringWidth(this.button.text.text) / 2
				+ this.g.getFontMetrics(this.button.text.getFont())
						.stringWidth(this.button.text.text.substring(0, this.valueInText)));
	}
}*/