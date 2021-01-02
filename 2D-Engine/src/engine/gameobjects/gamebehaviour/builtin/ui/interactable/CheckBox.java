package engine.gameobjects.gamebehaviour.builtin.ui.interactable;

import java.awt.Color;
import java.util.ArrayList;

import engine.game.GameContainer;
import engine.gameobjects.gamebehaviour.Bounds;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.gameobjects.gamebehaviour.type.UIGameBehaviour;
import engine.input.listener.ButtonListener;
import engine.input.listener.CheckBoxListener;
import engine.math.Vector2;

public class CheckBox extends UIGameBehaviour implements ButtonListener {
	private int size;
	private Button b;
	public boolean clicked;
	private Color standaloneC;
	private Color fillC;
	private boolean textb;
	private ArrayList<CheckBoxListener> listener = new ArrayList<CheckBoxListener>();

	public CheckBox(final int size) {
		this.setStandaloneC(Color.WHITE);
		this.setFillC(Color.GREEN);
		this.size = size;
	}

	public CheckBox(final int size, final Color standaloneC, final Color fillC) {
		this.setStandaloneC(standaloneC);
		this.setFillC(fillC);
		this.size = size;
	}

	public void addCheckBoxListener(final CheckBoxListener cbl) {
		this.listener.add(cbl);
	}

	@Override
	public void ButtonClicked() {
		this.clicked = !this.clicked;
		if (this.clicked) {
			on();
		} else {
			off();
		}
	}

	@Override
	public void ButtonHover() {
	}

	@Override
	public void ButtonPress() {
	}
	
	private void on() {
		for (int i = 0; i < this.listener.size(); ++i) {
			this.listener.get(i).CheckBoxON();
		}
	}
	
	private void off() {
		for (int i = 0; i < this.listener.size(); ++i) {
			this.listener.get(i).CheckBoxOFF();
		}
	}

	public void Clicked() {
		this.setClicked(!this.isClicked());
	}

	public Button getB() {
		return this.b;
	}

	public Color getFillC() {
		return this.fillC;
	}

	public Color getStandaloneC() {
		return this.standaloneC;
	}

	public boolean isClicked() {
		return this.clicked;
	}

	@Override
	public void render() {
		if (this.isClicked()) {
			this.d.setColor(this.getFillC());
			this.d.fillRect(this.b.bounds.getSize());
		}
	}

	public void setB(final Button b) {
		this.b = b;
	}

	public void setClicked(final boolean clicked) {
		this.clicked = clicked;
	}

	public void setFillC(final Color fillC) {
		this.fillC = fillC;
	}

	public void setStandaloneC(final Color standaloneC) {
		this.standaloneC = standaloneC;
	}

	@Override
	public void start() {
		Bounds b = new Bounds(new Vector2(this.size));
		this.b = new Button(this.standaloneC, b);
		this.b.setBaseColor(this.getStandaloneC());
		this.b.addButtonListener(this);
		this.b.setWire(true);
		this.gameObject.addComponent(this.b);
	}

	@Override
	public void update() {
		if (GameContainer.input.isButton(1) && this.b.state == 0) {
			this.clicked = false;
			off();
		}
	}
}
