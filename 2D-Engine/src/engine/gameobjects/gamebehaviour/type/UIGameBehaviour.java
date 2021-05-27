package engine.gameobjects.gamebehaviour.type;

import engine.gameobjects.gamebehaviour.builtin.ui.RectTransform;

public abstract class UIGameBehaviour extends GameBehaviour {
	
	public RectTransform bounds; //Vllt protected
	
	public void uiUpdate() {}
	
	//void update();
	
	//void render();
	
	public String getType() {
		return "ui";
	}
}
