package engine.gameobjects.gamebehaviour;

import engine.gameobjects.GameObject;
import engine.gameobjects.gamebehaviour.type.GameBehaviour;
import engine.gameobjects.gamebehaviour.type.UIGameBehaviour;
import engine.math.Vector2;

public class UIGBBounds {
	
	private GameObject object;
	
	private Vector2 point1;
	private Vector2 point2;
	
	public UIGBBounds(Vector2 p1, Vector2 p2, GameObject obj) {
		this.point1 = p1;
		this.point2 = p2;
		this.object = obj;
	}
	
	public UIGBBounds getCopy(GameObject newObj) { //If you wanna copy to another object you wanna change the underliing UIGB
		return new UIGBBounds(this.point1.getCopy(), this.point2.getCopy(), newObj);
	}
	
	public void setBounds(Vector2 p1, Vector2 p2) {
		this.point1 = p1;
		this.point2 = p2;
		this.uigbUpdate();
	}
	
	public void setPoint1(Vector2 p1) {
		this.point1 = p1;
		this.uigbUpdate();
	}
	
	public void setPoint2(Vector2 p2) {
		this.point2 = p2;
		this.uigbUpdate();
	}
	
	private void uigbUpdate() {
		for (GameBehaviour gb : this.object.getComponents()) {
			if (gb.getClass().getSuperclass().getSimpleName().equals(UIGameBehaviour.class.getSimpleName())) {
				UIGameBehaviour uigb = (UIGameBehaviour)gb;
				uigb.uiUpdate();
				System.out.println("UIUPDATE IN UIGBBOUNDS");
			}
		}
	}
}