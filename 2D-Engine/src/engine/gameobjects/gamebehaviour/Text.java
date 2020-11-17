package engine.gameobjects.gamebehaviour;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import engine.game.Window;
import engine.math.Vector2;

public class Text extends GameBehaviour {
	public String text;
	private Font font;
	private int size;
	private Color color;

	private Vector2 offset = new Vector2();

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
