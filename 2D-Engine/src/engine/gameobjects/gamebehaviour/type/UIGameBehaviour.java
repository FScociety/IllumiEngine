package engine.gameobjects.gamebehaviour.type;

import engine.gameobjects.gamebehaviour.Bounds;

public abstract class UIGameBehaviour extends GameBehaviour {
	
	public Bounds bounds; //Vllt protected
	
	public void uiUpdate() {}
	
	//void update();
	
	//void render();
	
	public String getType() {
		return "ui";
	}
}
