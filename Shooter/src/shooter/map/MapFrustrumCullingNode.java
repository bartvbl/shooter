package shooter.map;


import geom.Point;
import render.RenderContext;
import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;
import shooter.GameWorld;
import static org.lwjgl.opengl.GL11.*;

public class MapFrustrumCullingNode extends CoordinateNode implements SceneNode {

	private final double renderRadius;
	private final double centerX;
	private final double centerY;
	private final GameWorld world;

	//yes. I know. TOTALLY cheating with frustrum culling here. But hey, it fits the definition, right? Not rendering something that isn't in view...
	public MapFrustrumCullingNode(GameWorld world, double renderRadius, double centerX, double centerY) {
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
		Point mapLocation = this.world.map.getLocation();
		//map scrolls in opposite direction of camera
		double dx = -mapLocation.x - centerX;
		double dy = -mapLocation.y - centerY;
		double distanceToCameraCenter = Math.sqrt(dx*dx + dy*dy);
		return distanceToCameraCenter <= renderRadius;
	}

	public void postRender(RenderContext context) {
		context.popMatrix();
	}
	
	

}
