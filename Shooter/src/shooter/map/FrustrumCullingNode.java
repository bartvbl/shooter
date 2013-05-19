package shooter.map;

import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;

public class FrustrumCullingNode extends CoordinateNode implements SceneNode {

	private final double renderRadius;
	private final double centerX;
	private final double centerY;
	private final double centerZ;

	public FrustrumCullingNode(double renderRadius, double centerX, double centerY, double centerZ) {
		this.renderRadius = renderRadius;
		this.centerX = centerX;
		this.centerY = centerY;
		this.centerZ = centerZ;
	}

	public void render() {}
	public void destroy() {}

	public boolean isVisible() {
		return shouldRender();
	}

	private boolean shouldRender() {
		return true;
	}

}
