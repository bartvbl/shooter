package scene.sceneGraph;


import geom.Point;
import render.RenderContext;
import shooter.GameWorld;
import static org.lwjgl.opengl.GL11.*;

public class FrustrumCullingNode extends CoordinateNode implements SceneNode {

	private final double renderRadius;
	private double centerX;
	private double centerY;
	private final GameWorld world;

	//yes. I know. TOTALLY cheating with frustrum culling here. Though it fits the definition, right? Not rendering something that isn't in view...
	public FrustrumCullingNode(GameWorld world, double renderRadius, double centerX, double centerY) {
		this.renderRadius = renderRadius;
		this.centerX = centerX;
		this.centerY = centerY;
		this.world = world;
	}

	public void render(RenderContext context) {
		
	}
	public void destroy() {}

	public boolean isVisible() {
		return shouldRender();
	}

	private boolean shouldRender() {
		Point mapLocation = this.world.controlledNode.getLocation().negate();
		//map scrolls in opposite direction of camera
		double dx = mapLocation.x - centerX;
		double dy = mapLocation.y - centerY;
		double distanceToCameraCenter = Math.sqrt(dx*dx + dy*dy);
		return distanceToCameraCenter <= renderRadius;
	}

	public void setDrawCenter(Point location) {
		this.centerX = location.x;
		this.centerY = location.y;
	}
	
	

}
