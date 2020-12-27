package engine.gameobjects.gamebehaviour.builtin.ui;

import java.awt.Color;
import java.awt.Font;

import engine.game.Window;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;

public class Text extends GameBehaviour {
	public String text;
	private Font font;
	private int size;
	private Color color;

	public Text() {
		this.text = "Text Missing";
		this.setSize(5);
		this.setColor(Color.RED);
		this.setFont(Window.standartFont);
	}

	public Text(final String text, final int size, final Font font, final Color color) {
		this.text = text;
		this.setSize(size);
		this.setColor(color);
		this.setFont(font.deriveFont((float) size));
	}
	
	public Color getColor() {
		return this.color;
	}

	public Font getFont() {
		return this.font;
	}

	public int getSize() {
		return this.size;
	}

	@Override
	public void render() {
		d.setFont(this.font);
		d.setColor(this.color);
		d.drawString(this.text);
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public void setFont(final Font font) {
		this.font = font;
	}

	public void setSize(final int size) {
		this.size = size;
	}
}