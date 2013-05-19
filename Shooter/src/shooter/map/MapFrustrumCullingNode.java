package shooter.map;

import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;
import static org.lwjgl.opengl.GL11.*;

public class MapFrustrumCullingNode extends CoordinateNode implements SceneNode {

	private final double renderRadius;
	private final double centerX;
	private final double centerY;
	private final double centerZ;

	public MapFrustrumCullingNode(double renderRadius, double centerX, double centerY, double centerZ) {
		this.renderRadius = renderRadius;
		this.centerX = centerX;
		this.centerY = centerY;
		this.centerZ = centerZ;
	}

	public void render() {
		
		
	}
	public void destroy() {}

	public boolean isVisible() {
		return shouldRender();
	}

	private boolean shouldRender() {
		return true;
	}

}
