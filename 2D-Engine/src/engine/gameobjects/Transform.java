package engine.gameobjects;

import java.io.Serializable;

import engine.math.Vector2;

public class Transform implements Serializable {

	public Vector2 position = new Vector2();

	public float rotation = 0;

	public Vector2 scale = new Vector2();

	public Transform() {
	}

	public Transform(Transform transform) {
		this.position = transform.position;
		this.rotation = transform.rotation;
		this.scale = transform.scale;

	}

	public Transform(Vector2 position, float rotation, Vector2 scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public static Transform substract(Transform transform, Transform transform2) {
		Vector2 newPos = Vector2.substract(transform.position, transform2.position);
		float newRotation = transform.rotation - transform2.rotation;
		Vector2 newScale = Vector2.substract(transform.scale, transform2.scale);
		
		return new Transform(newPos, newRotation, newScale);
	}

	public void parentTransform(Transform transform) {
		this.position.add(transform.position);
		this.rotation += transform.rotation;
		this.scale.multiply(transform.scale);
	}
	
	public Transform getCopy() {
		// System.out.println(this.position + " " + this.rotation + " " + this.scale);
		return new Transform(this.position.getCopy(), this.rotation, this.scale.getCopy());
	}

	@Override
	public String toString() {
		return ("[Position:" + this.position + ", Rotation:" + this.rotation + ", Scale:" + this.scale + "]");
	}
}