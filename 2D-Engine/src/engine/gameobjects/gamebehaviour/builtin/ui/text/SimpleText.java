package engine.gameobjects.gamebehaviour.builtin.ui.text;

import java.awt.Color;
import java.awt.Font;

import engine.game.GameContainer;
import engine.game.Window;
import engine.gameobjects.gamebehaviour.builtin.ui.Alignment;
import engine.gameobjects.gamebehaviour.type.UIGameBehaviour;
import engine.math.Vector2;

public class SimpleText extends UIGameBehaviour {
	
	private String text;

	private Color textColor = Color.WHITE;
	private int fontSize = 20;
	private Font f;
	
	private int alignmentX = Alignment.CENTER_int;
	private int offsetX;
	
	public SimpleText(String text) {
		this.text = text;
	}
	
	public SimpleText(String text, int fontSize, Color textColor) {
		this.text = text;
		this.fontSize = fontSize;
		this.textColor = textColor;
	}
	
	public void start() {
		this.f = Window.standartFont_regular;
	}
	
	public void uiUpdate() {
		//this.d.setFont(f);
		System.out.println("ui");
		int stringWidth = this.d.g.getFontMetrics().stringWidth(text);
		
		System.out.println(stringWidth);
		
		if (alignmentX == Alignment.CENTER_int) {
			offsetX = -stringWidth / 2;
		} else if (alignmentX == Alignment.RIGHT_int) {
			offsetX = 0;
		} else if (alignmentX == Alignment.LEFT_int) {
			offsetX = (int) (this.bounds.getSize().x - stringWidth);
		}
	}
	
	public void render() {
		this.d.setFont(f);
		this.d.setColor(this.textColor);
		this.d.drawString("text", fontSize, new Vector2(offsetX, fontSize / 2));
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
		this.uiUpdate();
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
		this.uiUpdate();
	}

	public int getAlignmentX() {
		return alignmentX;
	}

	public void setAlignmentX(int alignmentX) {
		this.alignmentX = alignmentX;
		this.uiUpdate();
	}
	
	public Color getColor() {
		return this.textColor;
	}
	
	public void setColor(Color c) {
		this.textColor = c;
		this.uiUpdate();
	}
}