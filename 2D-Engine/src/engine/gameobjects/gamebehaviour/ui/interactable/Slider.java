package engine.gameobjects.gamebehaviour.ui.interactable;

import java.awt.Color;
import java.util.ArrayList;

import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.Text;
import engine.input.listener.ButtonListener;
import engine.input.listener.SliderListener;
import engine.math.Vector2;

public class Slider extends GameBehaviour {
	public static int JUST_MOVING;
	public static int COLOR_CHANGING;
	static {
		Slider.JUST_MOVING = 0;
		Slider.COLOR_CHANGING = 1;
	}
	private Vector2 size;
	public boolean Xoriented;
	private GameObject buttonObj;
	private Button b;
	private Text t;
	private Color backColor;
	private float value;
	public int type;
	private Color oldColor;

	private ArrayList<SliderListener> listener;

	public Slider(Vector2 size, final Color backColor) {
		this.Xoriented = true;
		this.value = 0.0f;
		this.listener = new ArrayList<SliderListener>();
		this.size = size;
		this.backColor = backColor;
	}

	public void addSliderListener(final SliderListener sl) {
		this.listener.add(sl);
	}

	public void addText(final Text t) {
		(this.t = t).setAlignment(Text.CENTER);
	}

	@Override
	public void render() {
		d.setColor(this.backColor);
		d.fillRect(this.size);
	}

	public void setValue(final float value) {
		if (value >= 0.0f || value <= 1.0f) {
			this.value = value;
		}
		this.buttonObj.setPosition(new Vector2(value * (this.size.x - this.size.y), 0.0f));
		if (this.type == Slider.COLOR_CHANGING) {
			final Color newC = new Color(Math.round(this.oldColor.getRed() * value),
					Math.round(this.oldColor.getGreen() * value), Math.round(this.oldColor.getBlue() * value));
			this.b.setBaseColor(newC);
		}
		if (this.t != null) {
			this.t.text = new StringBuilder(String.valueOf(Math.round(value * 100.0f) / 100.0f)).toString();
		}
	}

	@Override
	public void start() {
		this.buttonObj = new GameObject(new Vector2((size.x) / 2, 0), this.gameObject);
		System.out.println(this.buttonObj.getTransform());
		this.b = new Button(new Vector2(size.y, size.y));
		this.oldColor = this.b.getBaseColor();
		this.b.addButtonListener(new ButtonListener() {
			@Override
			public void ButtonClicked() {
			}

			@Override
			public void ButtonHover() {
			}

			@Override
			public void ButtonPress() {
				final int mouseX = (int) (Slider.this.gc.getInput().getMousePos().x - Slider.this.size.y / 2);
				if (mouseX > Slider.this.gameObject.getTransformWithCaution().position.x && mouseX <= Slider.this.size.x
						+ Slider.this.gameObject.getTransformWithCaution().position.x - Slider.this.size.y) {
					Slider.this.buttonObj.setPosition(
							new Vector2(mouseX - Slider.this.gameObject.getTransformWithCaution().position.x, 0));
				} else if (mouseX < Slider.this.gameObject.getTransformWithCaution().position.x
						+ Slider.this.size.y / 2) {
					Slider.this.buttonObj.setPosition(new Vector2(0));
				} else if (mouseX > Slider.this.size.x + Slider.this.gameObject.getTransformWithCaution().position.x
						- Slider.this.size.y / 2) {
					Slider.this.buttonObj.setPosition(new Vector2(Slider.this.size.x - Slider.this.size.y, 0.0f));
				}
				Slider.this.updateValue();
				for (int i = 0; i < Slider.this.listener.size(); ++i) {
					Slider.this.listener.get(i).SliderMoved(Slider.this.value);
				}
			}
		});
		if (this.t != null) {
			this.b.addText(this.t);
		}
		this.buttonObj.addComponent(this.b);
		this.updateValue();
	}

	@Override
	public void update() {

	}

	private void updateValue() {
		this.value = (this.buttonObj.getTransformWithCaution().position.x
				- this.gameObject.getTransformWithCaution().position.x) / (this.size.x - this.size.y);
		if (this.type == Slider.COLOR_CHANGING) {
			final Color newC = new Color(Math.round(this.oldColor.getRed() * this.value),
					Math.round(this.oldColor.getGreen() * this.value),
					Math.round(this.oldColor.getBlue() * this.value));
			this.b.setBaseColor(newC);
		}
		if (this.t != null) {
			this.t.text = new StringBuilder(String.valueOf(Math.round(this.value * 100.0f) / 100.0f)).toString();
		}
	}
}
