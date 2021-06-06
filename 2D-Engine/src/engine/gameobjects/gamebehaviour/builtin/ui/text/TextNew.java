package engine.gameobjects.gamebehaviour.builtin.ui.text;

import java.awt.Color;
import java.awt.Font;

import engine.gameobjects.gamebehaviour.type.UIGameBehaviour;
import engine.io.LocalFileLoader;
import engine.math.Vector2;

public class TextNew extends UIGameBehaviour {
	
	private Font f;
	private int fontSize;
	
	String text;
	
	public TextNew(String text, int fontSize) {
		f = LocalFileLoader.loadFont("/defaultfont/regular.otf");
		this.text = text;
		this.fontSize = fontSize;
	}
	
	public void uiUpdate() {
		this.d.g.setFont(f);
		int width = this.d.g.getFontMetrics().stringWidth(text);
		
		if (width <= this.bounds.getSize().x) {
			
		}
	}
	
	public int getString() {
		
	}
	
	public void render() {
		this.d.setFont(f);
		this.d.setColor(Color.WHITE);
		this.d.drawString(text, fontSize, Vector2.add(this.bounds.getPoint1(), new Vector2(0, this.fontSize)));
	}
	
}