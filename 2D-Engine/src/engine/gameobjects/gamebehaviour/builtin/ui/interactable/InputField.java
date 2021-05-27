package engine.gameobjects.gamebehaviour.builtin.ui.interactable;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.builtin.ui.RectTransform;
import engine.gameobjects.gamebehaviour.builtin.ui.ColorLabel;
import engine.gameobjects.gamebehaviour.builtin.ui.Text2;
import engine.gameobjects.gamebehaviour.type.UIGameBehaviour;
import engine.input.listener.ButtonListener;
import engine.math.Vector2;

public class InputField extends UIGameBehaviour implements KeyListener, ButtonListener {
	
	private RectTransform bounds;
	
	private boolean clicked;
	private Color[] colors = {Color.GRAY, Color.BLUE};
	private Button b;
	private Text2 text;
	private GameObject pointer;
	private ColorLabel selectArea;
	
	private int pointerPos = 0;
	private double time;
	
	private int selectionPos = 0;
	private boolean selecting = false;

	private boolean key_shift = false;
	private boolean key_command = false;
	
	private Clipboard clipboard;
	
	private UIGameBehaviour pointerColorLabel;
	
	public InputField(RectTransform b) {
		this.prefferedInWorldState = 0; //Not in World
		
		//Adding this Object as an KeyListener
		GameContainer.window.addKeyListener(this); 
		
		//Applying Bounds
		this.bounds = b;
		
		//Getting InputField
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	@Override
	public void ButtonClicked() {
		//Flip the click state
		this.clicked = !this.clicked;
		
		//POINTER
		this.updatePointerPosInText();
		
		//SELECTION
		this.selecting = false;
		this.selectionPos = this.pointerPos;
		this.updateSelectedArea();
	}
	
	@Override
	public void ButtonHover() {}
	
	@Override
	public void ButtonPress() {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!this.clicked) { return; }
			
		//get key's text
		char input = e.getKeyChar();
		//get key's code
		int code = e.getExtendedKeyCode();
		//get existing text
		String text = this.text.getText();
		
		//find prefixes from commands
		if (code == KeyEvent.VK_SHIFT) {
			this.key_shift = true;
		} else if (code == KeyEvent.VK_CONTROL) {
			this.key_command = true;
		}
		
		//test for "command" prefix
		if (this.key_command) { // => Command pressed
			if (code == KeyEvent.VK_A) {
				this.pointerPos = text.length();
				this.selectionPos = 0;
				this.selecting = true;
				this.updateSelectedArea();
			} else if (code == KeyEvent.VK_C) { // => Copy
				int left = this.selectionPos < this.pointerPos ? this.selectionPos : this.pointerPos;
				int right = this.selectionPos < this.pointerPos ? this.pointerPos : this.selectionPos;
				
				String copyText = text.substring(left, right);
				
				Transferable transferable = new StringSelection(copyText);
				this.clipboard.setContents(transferable, null);
			} else if (code == KeyEvent.VK_V) { // => Paste
				try {
					this.addTextIntoText(text, (String) this.clipboard.getData(DataFlavor.stringFlavor));
					this.selecting = false;
					this.selectionPos = this.pointerPos;
					this.updateSelectedArea();
				} catch (UnsupportedFlavorException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		
		//test for "shift" prefix
		} else if (this.key_shift) { // => Shift pressed
			if (code > 40 && code != 157) { 
				// => not writing characters
				this.addCharIntoText(text, input);
			} else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {
				this.selecting = true;
				
				if (code == KeyEvent.VK_LEFT) {
					this.pointerPos = this.moveInText(this.pointerPos, -1);
				} else if (code == KeyEvent.VK_RIGHT) {
					this.pointerPos = this.moveInText(this.pointerPos, 1);
				}
				
				this.updateSelectedArea();
				this.updatePointerPosInText();
			}
			
		//test if arrow keys used to move the pointer
		} else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {
			if (code == KeyEvent.VK_LEFT) {
				this.pointerPos = this.moveInText(this.pointerPos, -1); //Move Left
			} else if (code == KeyEvent.VK_RIGHT) {
				this.pointerPos = this.moveInText(this.pointerPos, 1); //Move Right
			}
			this.selecting = false;
			this.updateSelectedArea();
			this.updatePointerPosInText();
			
		//just write text without any addition
		} else {
			if (code > 31 && code != 38 && code != 40 && code != 157) { //>31 => no tab,shift,alt... | !=38 => TOP_Arrow | !=40 => DOWN_Arrow | !=158 => no command
				this.addCharIntoText(text, input);
			} else if (code == 8) { //Wanna delete Text?
				//Remove Text where pointer is placed
				
				int left = this.selectionPos < this.pointerPos ? this.selectionPos : this.pointerPos;
				int right = this.selectionPos < this.pointerPos ? this.pointerPos : this.selectionPos;
				
				text = text.substring(0, left > 0 && this.selecting == false ? left-1 : left) + "" + text.substring(right, text.length());
				if (this.selecting == false) { //CAUSE IDK
					this.pointerPos = this.moveInText(this.pointerPos, -1);
				} else {
					this.selectionPos = left;
					this.pointerPos = left;
				}
				
				this.selecting = false;
				this.updatePointerPosInText();
				this.updateSelectedArea();
				
				//Apply new Text
				this.text.setText(text);
			}
		}
	}
	
	private void addCharIntoText(String text, char input) {
		//Add new Text to End
		if (this.text.getText().length() > 0) {
			text = text.substring(0, this.pointerPos) + input + text.substring(this.pointerPos, text.length());
		} else { //If nothing in there 'text.substring(..)' not really working
			text = input + "";
		}	
		
		this.pointerPos++; //Erlaubt ja sowieso eins hinzugefügt wird
		
		//Apply new Text
		this.text.setText(text);
		
		this.updatePointerPosInText();
	}
	
	private void addTextIntoText(String text, String input) {
		//Add new Text to End
		if (this.text.getText().length() > 0) {
			text = text.substring(0, this.pointerPos) + input + text.substring(this.pointerPos, text.length());
		} else { //If nothing in there 'text.substring(..)' not really working
			text = input + "";
		}	
		
		this.pointerPos += input.length(); //Erlaubt ja sowieso eins hinzugefügt wird
		
		//Apply new Text
		this.text.setText(text);
		
		this.updatePointerPosInText();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getExtendedKeyCode();
		
		if (code == KeyEvent.VK_SHIFT) {
			this.key_shift = false;
		} else if (code == KeyEvent.VK_CONTROL) {
			this.key_command = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void start() {
		
		//Creating Button so its clickable
		GameObject buttonObj = new GameObject(new Vector2(0), this.gameObject);
		RectTransform buttonBounds = new RectTransform(Vector2.add(this.bounds.getPoint1(), 3), Vector2.substract(this.bounds.getPoint2(), 3));
		buttonObj.addComponent(buttonBounds);
		this.b = new Button(Color.WHITE);
		buttonObj.addComponent(b);
		this.b.addButtonListener(this);
		
		//Creating Text for Displaying the text;
		this.text = new Text2(buttonBounds);
		buttonObj.addComponent(this.text);
		this.text.setSize(10);
		this.text.setColor(Color.BLACK);
		this.text.setAlignment(Text2.LEFT_CENTER);
		
		//Creating Pointer for "posInText"
		pointer = new GameObject(new Vector2(0), this.gameObject);
		RectTransform pointerBounds = new RectTransform(new Vector2(5,20));
		this.pointerColorLabel = new ColorLabel(Color.BLACK, pointerBounds);
		pointer.addComponent(this.pointerColorLabel);
		
		//Creating SelectArea for text
		GameObject selectObject = new GameObject(new Vector2(0), this.gameObject);
		RectTransform selectBounds = new RectTransform(new Vector2(5,20));
		selectArea = new ColorLabel(new Color(100, 100, 100, 50), selectBounds);
		selectObject.addComponent(selectArea);
		
		//POINTER
		this.updatePointerPosInText();
		
		//SELECTION AREA
		this.updateSelectedArea();
	}
	
	private int moveInText(int pos, int direction) {
		if (pos > 0 && direction == -1) {
			return pos - 1;
		} else if (pos < this.text.getText().length() && direction == 1) {
			return pos + 1;
		}
		
		return pos; //Doesnt move
	
	}
	@Override
	public void update() {
		if (this.clicked) {
			//Deactivade InputField selection when mouse is clicked outside the InputField
			if (GameContainer.input.isButton(1) && this.b.state == 0) {
				this.clicked = false;
				this.updatePointerPosInText();
				this.updateSelectedArea();
			}
			
			//Blinking start
			time += GameContainer.dt;
			
			if (time >= 0.3f) {
				if (this.pointer.getActive()) {
					this.pointer.deactivade();
				} else {
					this.pointer.activade();
				}
				time = 0;
			}
			//Blinking end
			
		}
	}
	
	private void updatePointerPosInText() {
		if (this.clicked) {
			this.pointer.activade();
			
			//get and apply where the pointer is in the text
			float posInText = this.d.getFontSize(this.text.getFont()).stringWidth(this.text.getText().substring(0, this.pointerPos)) * this.text.getSize();
			this.pointerColorLabel.gameObject.setPosition(new Vector2(posInText + this.text.getOffset().x, this.text.getOffset().y - this.text.getSize()/2));
			this.pointerColorLabel.bounds.setBounds(new Vector2(2, this.text.getSize()));
			
			//Update Selection to Pointer
			if (!this.selecting) {
			this.selectionPos = this.pointerPos;
			}
		} else {
			this.pointer.deactivade();
		}
	}
	
	private void updateSelectedArea() {
		if (this.clicked && this.selecting) {
			this.selectArea.gameObject.activade();
			
			//Calculate Offset of the Selection relative to the text
			float selectAreaStart   = this.d.getFontSize(this.text.getFont()).stringWidth(this.text.getText().substring(0, this.selectionPos)) * this.text.getSize();
			float selectAreaEnd = this.d.getFontSize(this.text.getFont()).stringWidth(this.text.getText().substring(0, this.pointerPos)) * this.text.getSize();
			
			//Flipp if selection is from right => left
			float flippBuffer = selectAreaEnd; 
			if (selectAreaEnd < selectAreaStart) {
				selectAreaEnd = selectAreaStart;
				selectAreaStart = flippBuffer;
			}
			
			//Applying Offset of the Selection, where text is
			this.selectArea.gameObject.setPosition(new Vector2(selectAreaStart + this.text.getOffset().x, this.text.getOffset().y - this.text.getSize()));
			this.selectArea.bounds.setBounds(new Vector2(0, 0), new Vector2(selectAreaEnd - selectAreaStart, this.text.getSize()));	
		} else {
			this.selectArea.gameObject.deactivade();
		}
	}
	
	@Override
	public void render() {
		//RENDER: Background (Select State)
		this.d.setColor(colors[clicked ? 1 : 0]);
		this.d.fillRect(this.bounds);
	}	
}