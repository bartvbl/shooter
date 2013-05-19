package scene.sceneGraph;

import static org.lwjgl.opengl.GL11.*;
import geom.Point;

public abstract class CoordinateNode extends ContainerNode {
	protected double rotation;
	protected double x, y;
	protected boolean visible = true;
	
	public void translate(double x, double y) 
	{
		this.x += x;
		this.y += y;
	}
	
	public void setLocation(double x, double y) 
	{
		this.x = x;
		this.y = y;
	}

	public void rotate(double angle) 
	{
		this.rotation += angle;
	}

	public void setRotation(double rotation) 
	{
		this.rotation = rotation;
	}

	public void preRender() {
		glRotated(rotation, 0, 0, 1);
		glTranslated(x, y, 0);
		glPushMatrix();
	}

	public abstract void render();
	public abstract void destroy();

	public Point getLocation() {
		return new Point(x, y);
	}
}
