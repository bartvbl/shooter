package scene.sceneGraph;

import static org.lwjgl.opengl.GL11.*;
import render.RenderContext;
import geom.Point;

public abstract class CoordinateNode extends ContainerNode {
	protected float rotationX;
	protected float rotationY;
	protected float rotationZ;
	protected float x, y, z;
	protected boolean visible = true;
	
	public void translate(double x, double y, double z) 
	{
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void setLocation(float x, float y, float z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void rotate(double angle) 
	{
		this.rotationZ += angle;
	}
	
	public void setRotationX(double rotation) {
		this.rotationX = (float) rotation;
	}
	
	public void setRotationY(double rotation) {
		this.rotationY = (float) rotation;
	}
	
	public void setRotationZ(double rotation) 
	{
		this.rotationZ = (float) rotation;
	}

	public void preRender(RenderContext context) {
		context.pushMatrix();
		context.rotate(rotationX, 1, 0, 0);
		context.rotate(rotationY, 0, 1, 0);
		context.rotate(rotationZ, 0, 0, 1);
		context.translate(x, y, z);
	}
	
	public void postRender(RenderContext context) {
		context.popMatrix();
	}

	public abstract void render(RenderContext context);
	public abstract void destroy();

	public Point getLocation() {
		return new Point(x, y);
	}
}
