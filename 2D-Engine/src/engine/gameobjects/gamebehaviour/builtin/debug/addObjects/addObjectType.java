package engine.gameobjects.gamebehaviour.builtin.debug.addObjects;

import engine.game.GameContainer;
import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.input.listener.ButtonListener;
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

		((addObjects) this.gameObject.getParent().getComponent(addObjects.class)).close();
	}

	@Override
	public void ButtonHover() {

	}

	@Override
	public void ButtonPress() {

	}

}
