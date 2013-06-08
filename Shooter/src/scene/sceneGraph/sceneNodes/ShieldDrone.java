package scene.sceneGraph.sceneNodes;

import org.lwjgl.util.glu.Sphere;

import render.RenderContext;
import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;

public class ShieldDrone extends CoordinateNode implements SceneNode {

	private final Sphere sphere;
	private double rotation = 0;

	public ShieldDrone() {
		this.sphere = new Sphere();
		this.setPivot(0, -0.5f, 0);
		this.setLocation(0, -0.7f, 0);
	}
	
	public void render(RenderContext context) {
		rotation+=2;
		this.setRotationZ(rotation);
		if(rotation >= 360) {
			rotation -= 360;
		}
		sphere.draw(0.1f, 10, 10);
	}
	
	public void destroy() {
		
	}

}
