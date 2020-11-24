package engine.gameobjects;

import java.util.ArrayList;

import engine.game.GameContainer;
import engine.gameobjects.gamebehaviour.GameBehaviour;
import engine.math.Vector2;
import engine.scenes.Scene;

public class GameObject {

	public static int CENTER = 0, LEFT_TOP = 1;
	private Transform localTransform = new Transform();
	private Transform globalTransform = new Transform();
	private ArrayList<GameBehaviour> components;
	private ArrayList<GameObject> children;
	private GameObject parent;
	public boolean started = false;
	public boolean updatesOutOfView = false;
	
	public boolean inWorld = true;
	public int aligment = 0;
	
	public int viewRange = 50; //Change if needed

	public GameObject(Transform transform) {
		this.localTransform = transform;
		this.globalTransform = transform;
		this.components = new ArrayList<GameBehaviour>();
		this.children = new ArrayList<GameObject>();
		this.setTransform(transform);
	}

	public GameObject(Transform transform, final GameObject parent) {
		this.localTransform = transform;
		this.globalTransform = transform;
		this.components = new ArrayList<GameBehaviour>();
		this.children = new ArrayList<GameObject>();
		this.setParent(parent);
		this.setTransform(transform);
	}

	public GameObject(Vector2 pos) {
		this.localTransform.position = pos;
		this.globalTransform.position = pos;
		this.components = new ArrayList<GameBehaviour>();
		this.children = new ArrayList<GameObject>();
		this.setTransform(new Transform(pos, 0, new Vector2(1), new Vector2(100)));
	}

	public GameObject(Vector2 pos, final GameObject parent) {
		this.localTransform.position = pos;
		this.globalTransform.position = pos;
		this.components = new ArrayList<GameBehaviour>();
		this.children = new ArrayList<GameObject>();
		this.setParent(parent);
		this.setTransform(new Transform(pos, 0, new Vector2(1), new Vector2(100)));
	}
	
	public void addChildren(final GameObject child) {
		this.children.add(child);
	}
	
	public void addComponent(final GameBehaviour gb) {
		gb.gameObject = this;
		this.components.add(gb);
		
		if (this.started == true) {
			gb.d = GameContainer.d;
			gb.start();
			gb.started = true;
		}
	}
	
	public void addPosition(final Vector2 position) {
		this.setTransform(new Transform(Vector2.add(position, this.localTransform.position),
				this.localTransform.rotation, this.localTransform.scale, this.localTransform.defaultScale));
	}

	public void divideScale(final Vector2 scale) {
		this.setTransform(new Transform(this.localTransform.position, this.localTransform.rotation,
				Vector2.divide(scale, this.localTransform.scale), this.localTransform.defaultScale));
	}

	public GameObject getChild() {
		return this.children.get(0);
	}

	public ArrayList<GameObject> getChildren() {
		return this.children;
	}

	public int getChildrenCount() {
		int children = 0;
		for (int i = 0; i < this.children.size(); i++) {
			children += this.children.get(i).getChildrenCount() + 1;
		}
		return children;
	}

	public <GB> GB getComponent(Class<?> gb) {
		for (final GameBehaviour component2 : this.components) {
			if (component2.getClass() == gb) {
				return (GB) component2;
			}
		}
		return null;
	}

	public int getComponentCount() {
		return this.components.size();
	}

	public Transform getLocalTransform() {
		return this.localTransform.getCopy();
	}
	
	public GameObject getParent() {
		return this.parent;
	}

	public Transform getTransform() {
		return this.globalTransform.getCopy();
	}

	public Transform getTransformWithCaution() {
		return this.globalTransform;
	}

	public void render() {
		GameContainer.d.applyTransforms(this);
		for (final GameBehaviour component : this.components) {
			component.render();
		}
		GameContainer.d.resetTransform();

		for (final GameObject child : this.children) {
			child.render();
		}

		/*GameContainer.d.setColor(Color.GREEN);
		
		Vector2 pos = Vector2.add(Vector2.substract(this.getTransform().position, GameContainer.d.cameraOffset), Vector2.divide(GameContainer.windowSize, new Vector2(2)));
		GameContainer.d.fillRect(pos, new Vector2(4));*/
	}

	/*public void addRotation(final float rotation) {
		this.setTransform(new Transform(this.localTransform.position, rotation + this.localTransform.rotation,
				this.localTransform.scale));
	}

	public void addScale(final Vector2 scale) {
		this.setTransform(new Transform(this.localTransform.position, this.localTransform.rotation,
				Vector2.add(scale, this.localTransform.scale)));
	}
	
	public void subtractPosition(final Vector2 position) {
		this.setTransform(new Transform(Vector2.substract(position, this.localTransform.position),
				this.localTransform.rotation, this.localTransform.scale));
	}

	public void subtractRotation(final int rotation) {
		this.setTransform(new Transform(this.localTransform.position, rotation - this.localTransform.rotation,
				this.localTransform.scale));
	}

	public void subtractScale(final Vector2 scale) {
		this.setTransform(new Transform(this.localTransform.position, this.localTransform.rotation,
				Vector2.substract(scale, this.localTransform.scale)));
	}
	
	public void multiplyPosition(final Vector2 position) {
		this.setTransform(new Transform(Vector2.multiply(position, this.localTransform.position),
				this.localTransform.rotation, this.localTransform.scale));
	}

	public void multiplyRotation(final int rotation) {
		this.setTransform(new Transform(this.localTransform.position, rotation * this.localTransform.rotation,
				this.localTransform.scale));
	}

	public void multiplyScale(final Vector2 scale) {
		this.setTransform(new Transform(this.localTransform.position, this.localTransform.rotation,
				Vector2.multiply(scale, this.localTransform.scale)));
	}

	public void dividePosition(final Vector2 position) {
		this.setTransform(new Transform(Vector2.divide(position, this.localTransform.position),
				this.localTransform.rotation, this.localTransform.scale), this.localTransform.defaultScale);
	}

	public void divideRotation(final int rotation) {
		this.setTransform(new Transform(this.localTransform.position, rotation / this.localTransform.rotation,
				this.localTransform.scale, this.localTransform, this.localTransform.defaultScale));
	}*/
	
	public void setChildren(final ArrayList<GameObject> children) {
		this.children = children;
	}
	
	public void setParent(final GameObject parent) {
		(this.parent = parent).addChildren(this);
	}
	
	public void addRotation(float r) {
		this.setTransform(new Transform(this.localTransform.position, this.localTransform.rotation + r, this.localTransform.scale, this.localTransform.defaultScale));
		
	}

	public void setPosition(final Vector2 position) {
		this.setTransform(new Transform(position, this.localTransform.rotation, this.localTransform.scale, this.localTransform.defaultScale));
	}

	public void setRotation(final float rotation) {
		this.setTransform(new Transform(this.localTransform.position, rotation, this.localTransform.scale, this.localTransform.defaultScale));
	}
	
	public void setScale(final Vector2 scale) {
		this.setTransform(new Transform(this.localTransform.position, this.localTransform.rotation, scale, this.localTransform.defaultScale));
	}
	
	public void setTransform(final Transform transform) {
		//Bei DiffTransform verbesserung: Ist dumm immer 'this.getTransform()' zu machen ist besser mit varibale
		Transform diffTransform = new Transform(Vector2.substract(transform.position, this.getTransform().position),
												transform.rotation - this.getTransform().rotation,
												Vector2.substract(transform.scale, this.getTransform().scale),
												new Vector2(0));
		this.localTransform = transform;
		if (this.parent != null) {
			this.globalTransform = new Transform(
					Vector2.add(this.parent.getTransform().position, this.localTransform.position), //Doesnt berehcnen the rotationg mit ein
					this.parent.getTransform().rotation + this.localTransform.rotation,
					Vector2.multiply(this.parent.getTransform().scale, this.localTransform.scale),
					this.localTransform.defaultScale);
		} else {
			this.globalTransform = this.localTransform;
		}
		
		if (this.children.size() > 0) {
			this.updateTransform(diffTransform);
		}
	}

	public void start(Scene loadingScene) {
		this.started = true;
		loadingScene.addObjectCount();
		for (int size = this.components.size(), i = 0; i < size; ++i) {
			final GameBehaviour gb = this.components.get(i);
			gb.gameObject = this;
			gb.d 		  = GameContainer.d;
			gb.start();
			gb.started 	  = true;
		} 
		if (this.children.size() > 0) {
			for (int i = 0; i < this.children.size(); ++i) {
				this.children.get(i).start(loadingScene);
			}
		}
		System.out.println("Loaded GameObject[" + this + "]");
	}

	@Override
	public String toString() {
		return ("[Transform: " + this.globalTransform + ", Children: " + this.children.size() + ", Parent: "
				+ (this.parent != null ? true : false));
	}

	public void update() {
		for (final GameBehaviour component : this.components) {
			component.update();
		}

		for (final GameObject child : this.children) {
			child.update();
		}
	}

	public void updateGlobalTransform(final GameObject parent, Transform diffTransform) {
		this.localTransform.position.rotate(diffTransform.rotation);

		this.globalTransform = new Transform(
				Vector2.add(this.parent.getTransform().position, this.localTransform.position),
				this.parent.getTransform().rotation + this.localTransform.rotation,
				Vector2.multiply(this.parent.getTransform().scale, this.localTransform.scale),
				this.localTransform.defaultScale);
		
		if (this.children.size() > 0) {
			this.updateTransform(diffTransform);
		}
	}
	
	public void updateTransform(Transform diffTransform) {
		for (int i = 0; i < this.children.size(); i++) {
			this.children.get(i).updateGlobalTransform(this, diffTransform);
		}
	}
}