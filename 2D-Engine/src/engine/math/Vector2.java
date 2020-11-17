package engine.math;

public class Vector2 {
	public float x, y;

	public Vector2() {
		x = 0;
		y = 0;
	}

	public Vector2(float value) {
		this.x = value;
		this.y = value;
	}

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Vector2 vec, Vector2 vec2) {
		this.x = vec.x - vec2.x;
		this.y = vec.y - vec2.y;
	}

	public void abs() {
		this.x = Math.abs(x);
		this.y = Math.abs(y);
	}

	public void add(Vector2 vec) {
		this.x += vec.x;
		this.y += vec.y;
	}
	
	public void add(float value) {
		this.x += value;
		this.y += value;
	}
	
	public void substract(Vector2 vec) {
		this.x -= vec.x;
		this.y -= vec.y;
	}
	
	public void substract(float value) {
		this.x -= value;
		this.y -= value;
	}
	
	public void multiply(Vector2 vec) {
		this.x *= vec.x;
		this.y *= vec.y;
	}
	
	public void multiply(float value) {
		this.x *= value;
		this.y *= value;
	}

	public void divide(Vector2 vec) {
		this.x /= vec.x;
		this.y /= vec.y;
	}
	
	public void divide(float value) {
		this.x /= value;
		this.y /= value;
	}

	public float length() {
		return (float) Math.sqrt(x*x + y*y);
	}

	public void normalize() {
		this.divide(new Vector2(length()));
	}

	public Vector2 normalized() {
		return Vector2.divide(this, new Vector2(length()));
	}

	public void rotate(double angle) {
		double newAngle = Math.toRadians(angle);
		float oldX = this.x;
		float oldY = this.y;
		this.x = (float) ((oldX) * Math.cos(newAngle) - (oldY) * Math.sin(newAngle) * 1);
		this.y = (float) ((oldX) * Math.sin(newAngle) + (oldY) * Math.cos(newAngle) * 1);
	}
	
	public void rotate(double angle, Vector2 pivot) {
		this.substract(pivot);
		this.rotate(angle);
		this.add(pivot);
	}
	
	public static float toAngle(Vector2 vec, Vector2 vec2) {
		Vector2 diffVec = Vector2.substract(vec, vec2);
		return 180 + (float) Math.toDegrees(Math.atan2(diffVec.y, diffVec.x));
		
	}
	
	public float toAngle() {
		return 180 + (float) Math.toDegrees(Math.atan2(this.y, this.x));
	}
	
	public void invert() {
		this.x = -this.x;
		this.y = -this.y;
	}
	
	public Vector2 inverted() {
		this.invert();
		return this;
	}
	
	public void scale(Vector2 pivot, Vector2 scaling) {
		this.substract(pivot);
		this.multiply(scaling);
		this.add(pivot);
	}
	
	public void scale(Vector2 pivot, float scaling) {
		this.substract(pivot);
		this.multiply(scaling);
		this.add(pivot);
	}
	
	public static Vector2 abs(Vector2 vec) {
		return new Vector2(Math.abs(vec.x), Math.abs(vec.y));
	}

	public static Vector2 add(Vector2 vec, Vector2 vec2) {
		return new Vector2(vec.x + vec2.x, vec.y + vec2.y);
	}
	
	public static Vector2 add(Vector2 vec, float value) {
		return new Vector2(vec.x + value, vec.y + value);
	}

	public static Vector2 divide(Vector2 vec, Vector2 vec2) {
		return new Vector2(vec.x / vec2.x, vec.y / vec2.y);
	}
	
	public static Vector2 divide(Vector2 vec, float value) {
		return new Vector2(vec.x / value, vec.y / value);
	}

	public static Vector2 multiply(Vector2 vec, Vector2 vec2) {
		return new Vector2(vec.x * vec2.x, vec.y * vec2.y);
	}
	
	public static Vector2 multiply(Vector2 vec, float value) {
		return new Vector2(vec.x * value, vec.y * value);
	}

	public static Vector2 substract(Vector2 vec, Vector2 vec2) {
		return new Vector2(vec.x - vec2.x, vec.y - vec2.y);
	}
	
	public static Vector2 substract(Vector2 vec, float value) {
		return new Vector2(vec.x + value, vec.y + value);
	}

	public String toString() {
		return ("[" + this.x + ", " + this.y + "]");
	}
	
	public Vector2 getCopy() {
		return new Vector2(this.x, this.y);
	}
}