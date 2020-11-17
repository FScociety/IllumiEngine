// 
// Decompiled by Procyon v0.5.36
// 

package engine.gameobjects.gamebehaviour.ui.graphs.curves;

public class LinearGraph extends BaseGraph {
	private float gradient;
	private float offset;

	public LinearGraph(final float gradient, final float offset) {
		this.gradient = gradient;
		this.offset = offset;
	}

	@Override
	public float calcPoint(final float x) {
		final float y = this.gradient * x + this.offset;
		return y;
	}
}
