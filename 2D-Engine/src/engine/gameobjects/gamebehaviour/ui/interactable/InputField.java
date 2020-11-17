// 
// Decompiled by Procyon v0.5.36
// 

package engine.gameobjects.gamebehaviour.ui.interactable;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import engine.game.Window;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.Text;
import engine.input.listener.ButtonListener;
import engine.input.listener.InputFieldListener;

public class InputField extends GameBehaviour implements KeyListener {
	public static InputField inputFieldSelected;
	public static int MODE_EDIT;
	public static int MODE_REPLACE;
	static {
		InputField.MODE_EDIT = 0;
		InputField.MODE_REPLACE = 1;
	}
	private Button button;
	private boolean captureText;
	private int width;
	private int height;
	private int maxLength;
	private int valueInText;
	private int cursorX;
	private float blinking;
	private Color c;
	public int type;

	private ArrayList<InputFieldListener> listener;

	public InputField(final Color c, final int width, final int height, final int maxLength) {
		this.captureText = false;
		this.valueInText = 0;
		this.cursorX = 0;
		this.blinking = 0.0f;
		this.listener = new ArrayList<InputFieldListener>();
		this.width = width;
		this.height = height;
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
		return this.button.text.text;
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
			if (e.getKeyChar() == '\b' && this.button.text.text.length() >= 1) {
				this.button.text.text = ((this.valueInText != 0)
						? (String.valueOf(this.button.text.text.substring(0, this.valueInText - 1))
								+ this.button.text.text.substring(this.valueInText, this.button.text.text.length()))
						: this.button.text.text);
				this.button.updateTextSize();
				this.valueInText -= ((this.valueInText > 0) ? 1 : 0);
				this.updateCursorX();
			} else if (e.getKeyChar() == '\n') {
				this.setCapture(false);
			} else if (e.getKeyChar() != '\b'
					&& this.button.text.text.length() < ((this.maxLength == 0) ? Integer.MAX_VALUE : this.maxLength)) {
				if (this.valueInText == this.button.text.text.length()) {
					this.button.text.text = String.valueOf(this.button.text.text) + e.getKeyChar();
				} else {
					this.button.text.text = String.valueOf(this.button.text.text.substring(0, this.valueInText))
							+ e.getKeyChar()
							+ this.button.text.text.substring(this.valueInText, this.button.text.text.length());
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
			this.g.setColor(Color.WHITE);
			this.g.fillRect(this.cursorX, (int) this.gameObject.getPosY(), 3, this.height);
		}
	}

	private void setCapture(final boolean Capture) {
		if (Capture && InputField.inputFieldSelected == null) {
			InputField.inputFieldSelected = this;
			if (this.type == InputField.MODE_REPLACE) {
				this.button.text.text = "";
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
		this.button.text.text = text;
		this.button.updateTextSize();
	}

	@Override
	public void start() {
		this.gc.getWindow().getCanvas().addKeyListener(this);
		final GameObject buttonObj = new GameObject(0, 0, this.gameObject);
		(this.button = new Button(this.width, this.height)).setBaseColor(this.c);
		this.button.setWire(true);
		this.button.addText(new Text("", 20, Window.standartFont, Color.WHITE));
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
		if (this.gc.getInput().isButtonDown(1) && (this.gc.getInput().getMouseX() < this.gameObject.getPosX()
				|| this.gc.getInput().getMouseX() > this.gameObject.getPosX() + this.width
				|| this.gc.getInput().getMouseY() < this.gameObject.getPosY()
				|| this.gc.getInput().getMouseY() > this.gameObject.getPosY() + this.height)) {
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
}
