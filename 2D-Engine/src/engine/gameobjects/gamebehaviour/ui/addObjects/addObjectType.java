package engine.gameobjects.gamebehaviour.ui.addObjects;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.TransformController;
import engine.gameobjects.gamebehaviour.ui.interactable.Button;
import engine.input.listener.ButtonListener;
import engine.math.Vector2;
import engine.scenes.SceneManager;

public class addObjectType extends GameBehaviour implements ButtonListener {
	
	GameObject instance;

	public addObjectType(GameObject instance) {
		this.instance = instance;
	}
	
	@Override
	public void ButtonClicked() {
		GameObject instance = this.instance.getCopy();
		instance.setPosition(GameContainer.input.getMousePos(true));
		SceneManager.activeScene.addGameObject(instance);
		
		((addObjects)this.gameObject.getParent().getComponent(addObjects.class)).close();
	}

	@Override
	public void ButtonHover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ButtonPress() {
		// TODO Auto-generated method stub
		
	}

}
