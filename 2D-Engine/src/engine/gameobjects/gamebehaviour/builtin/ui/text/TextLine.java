package engine.gameobjects.gamebehaviour.builtin.ui.text;

import java.awt.Color;
import java.awt.Font;

public class TextLine {

	public char[] plainText;
	private TextNew parent;
	TextSnipped[] textSnipped;
	
	public TextLine(String text, TextNew parent) {
		this.plainText = text.toCharArray();
		this.parent = parent;
	}
	
	public void generateSnippeds() {
		Color defaultColor = Color.WHITE;
		int fontAttribute = Font.PLAIN;
		
		
	}
	
	public void render() {
		
	}
}