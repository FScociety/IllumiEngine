package engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import engine.game.GameContainer;
import engine.gameobjects.gamebehaviour.camera.Camera;
import engine.math.Vector2;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	private final int NUM_KEYS = 256;
	private final int NUM_BUTTONS = 6;
	private int[] keys;
	private int[] buttons;
	
	private Vector2 mousePos;
	private int scroll;

	public Input(final GameContainer gc) {
		this.keys = new int[NUM_KEYS];
		
		this.buttons = new int[NUM_BUTTONS];
		
		this.mousePos = new Vector2();
		this.scroll = 0;
		
		GameContainer.window.addKeyListener(this);
		GameContainer.window.addMouseMotionListener(this);
		GameContainer.window.addMouseListener(this);
		GameContainer.window.addMouseWheelListener(this);
	}
	
	public Vector2 getMousePos() {
		return this.mousePos;
	}
	
	public Vector2 getMousePosToWorld() {
		Vector2 newMousePos = Vector2.add(mousePos, Camera.activeCam.gameObject.getTransformWithCaution().position); //MousePos relative to Camera
		newMousePos.substract(Vector2.divide(GameContainer.windowSize, 2)); //Center the MousePos to Screen
		newMousePos.scale(Camera.activeCam.gameObject.getTransformWithCaution().position, 1 / Camera.activeCam.zoom); //Apply Camera Zoom to Mouse Pos
		return newMousePos;
	}
	
	public int getScroll() {
		return this.scroll;
	}

	public boolean isButton(final int button) {
		return this.buttons[button] == 1;
	}

	public boolean isButtonDown(final int button) {
		return this.buttons[button] > 1;
	}

	public boolean isButtonUp(final int button) {
		return this.buttons[button] == -1;
	}
	
	public boolean isKey(final int keyCode) {
		return this.keys[keyCode] == 1;
	}

	public boolean isKeyDown(final int keyCode) {
		return this.keys[keyCode] > 1;
	}

	public boolean isKeyUp(final int keyCode) {
		return this.keys[keyCode] == -1;
	}
	
	@Override
	public void keyPressed(final KeyEvent e) {
		if (e.getKeyCode() > this.keys.length) {
			return;
		}
		this.keys[e.getKeyCode()] += 1;
	}

	@Override
	public void keyReleased(final KeyEvent e) {
		if (e.getKeyCode() > this.keys.length) {
			return;
		}
		this.keys[e.getKeyCode()] = -1;

	}

	@Override
	public void keyTyped(final KeyEvent e) {

	}

	@Override
	public void mouseClicked(final MouseEvent e) {
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		this.mousePos.x = e.getX();
		this.mousePos.y = e.getY();
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		this.mousePos.x = e.getX();
		this.mousePos.y = e.getY();
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		if (e.getButton() > this.keys.length) {
			return;
		}
		
		this.buttons[e.getButton()] = 1;
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (e.getButton() > this.keys.length) {
			return;
		}
		
		this.buttons[e.getButton()] = -1;
	}

	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		this.scroll = e.getWheelRotation();
	}

	public void update() {
		this.scroll = 0;
		
		for (int i = 0; i < NUM_KEYS; ++i) {
			if (this.keys[i] == 1) { //Taste wurde gedrückt 
				this.keys[i]+=1;
			} else if (this.keys[i] == -1) { //Taste wurde released
				this.keys[i] = 0;
			}
		}
		
		for (int i = 0; i < NUM_BUTTONS; ++i) {
			if (this.buttons[i] == 1) { //Taste wurde gedrückt 
				this.buttons[i]+=1;
			} else if (this.buttons[i] == -1) { //Taste wurde released
				this.buttons[i] = 0;
			}
		}
		
	}
}