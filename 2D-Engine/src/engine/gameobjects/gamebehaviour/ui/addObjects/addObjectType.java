package engine.gameobjects.gamebehaviour.ui.addObjects;

import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.gameobjects.gamebehaviour.ui.interactable.Button;
import engine.input.listener.ButtonListener;
import engine.math.Vector2;

public class addObjectType extends GameBehaviour implements ButtonListener {
	
	Button b;
	GameObject objectToInstance;
	
	public void addObjectType(Vector2 size) {
		b = new Button(size);
		this.gameObject.addComponent(b);
	}

	@Override
	public void ButtonClicked() {
		((addObjects)this.gameObject.getParent().getComponent(addObjects.class)).close();;
		
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
