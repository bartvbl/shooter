package render;

import java.nio.FloatBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import reused.util.Stack;


import static org.lwjgl.opengl.GL11.*;

public class RenderContext {
	private Matrix4f modelViewMatrix;
	private final Stack<Matrix4f> modelViewMatrixStack = new Stack<Matrix4f>();
	private final FloatBuffer buff = BufferUtils.createFloatBuffer(4*4);

	public RenderContext() {
		this.modelViewMatrix = new Matrix4f();
		this.modelViewMatrix.setIdentity();
		this.pushMatrix();
	}

	public void setIdentity() {
		this.modelViewMatrix.setIdentity();
		//glLoadIdentity();
	}
	
	public void rotate(float angle, float x, float y, float z) {
		glRotated(angle, x, y, z);
		this.modelViewMatrix.rotate(angle, new Vector3f(x, y, z));
	}
	
	public void translate(float x, float y, float z) {
		glTranslated(x, y, z);
		this.modelViewMatrix.translate(new Vector3f(x, y, z));
	}

	public void scale(float x, float y, float z) {
		glScaled(x, y, z);
		this.modelViewMatrix.scale(new Vector3f(x, y, z));
	}
		
	public void pushMatrix() {
		glPushMatrix();
		this.modelViewMatrixStack.push(modelViewMatrix);
	}
	
	public void popMatrix() {
		glPopMatrix();
		this.modelViewMatrix = this.modelViewMatrixStack.pop();
	}

	public void storeModelViewMatrix(FloatBuffer buffer) {
		buff.rewind();
		glGetFloat(GL_MODELVIEW_MATRIX, this.buff);
		buff.rewind();
		buffer.rewind();
		buffer.put(buff);
		buffer.rewind();
		
	}

	public void loadMatrix(Matrix4f modelViewMatrix) {
		this.modelViewMatrix.load(modelViewMatrix);
	}

	public int stackLevel() {
		return modelViewMatrixStack.size();
	}

}
