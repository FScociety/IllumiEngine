package engine.gameobjects.gamebehaviour.ui.interactable;

import java.awt.Color;
import java.util.ArrayList;

import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.Text;
import engine.input.listener.ButtonListener;
import engine.input.listener.CheckBoxListener;
import engine.math.Vector2;

public class CheckBox extends GameBehaviour implements ButtonListener {
	private int size;
	private Text t;
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

	public void addText(final Text t) {
		this.t = t;
		this.textb = true;
		if (this.started) {
			this.createText();
		}
	}

	@Override
	public void ButtonClicked() {
		this.clicked = !this.clicked;
		if (this.clicked) {
			for (int i = 0; i < this.listener.size(); ++i) {
				this.listener.get(i).CheckBoxON();
			}
		} else {
			for (int i = 0; i < this.listener.size(); ++i) {
				this.listener.get(i).CheckBoxOFF();
			}
		}
	}

	@Override
	public void ButtonHover() {
	}

	@Override
	public void ButtonPress() {
	}

	public void Clicked() {
		this.setClicked(!this.isClicked());
	}

	private void createText() {
		final GameObject textG = new GameObject(new Vector2(this.size * 3 / 2, this.size), this.gameObject);
		this.t.setFont(this.t.getFont().deriveFont(this.size));
		textG.addComponent(this.t);
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
			this.d.fillRect((int) this.gameObject.getTransform().position.x,
					(int) this.gameObject.getTransform().position.y, this.size, this.size);
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
		(this.b = new Button(this.getStandaloneC(), new Vector2(this.size))).addButtonListener(this);
		this.b.setWire(true);
		this.gameObject.addComponent(this.b);
		if (this.textb) {
			this.createText();
		}
	}

	@Override
	public void update() {
		if (this.gc.getInput().isKey(48)) {
			this.gameObject.setPosition(new Vector2(0));
		}
	}
}
