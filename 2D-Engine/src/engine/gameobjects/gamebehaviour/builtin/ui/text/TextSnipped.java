package engine.gameobjects.gamebehaviour.builtin.ui.text;

import java.awt.Color;
import java.awt.Font;

public class TextSnipped {
	
	char[] textSnipped;
	
	Color customColor = null;
	int customFontStyle = 0;
	
	public TextSnipped nextSnipped;
 	
	public TextSnipped(char[] snipped) {
		for (int i = 0; i < textSnipped.length; i++) {
			if (this.textSnipped[i] == '§') {
				//RESET COLOR
				if (this.textSnipped[i] == 0) {
					this.customColor = Color.WHITE;
				} else if (this.textSnipped[i] == 'B') {
					this.customFontStyle = Font.BOLD;
				} else if (this.textSnipped[i] == 'I') {
					this.customFontStyle = Font.ITALIC;
				} else if (this.textSnipped[i] == 'S') {
					this.customFontStyle = Font.Bo
				}
			}
		}
	}

}