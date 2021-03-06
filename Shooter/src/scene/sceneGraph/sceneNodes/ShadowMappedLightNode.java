package scene.sceneGraph.sceneNodes;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import core.FrameUtils;
import core.GameSettings;

import geom.Point;
import gl.FrameBufferUtils;
import gl.shader.ShadowMapShader;
import gl.shadowMap.ShadowMapTextureGenerator;
import render.RenderContext;
import render.RenderPass;
import scene.sceneGraph.SceneNode;

public class ShadowMappedLightNode extends EmptyCoordinateNode implements SceneNode {
	private static final int SHADOW_MAP_WIDTH = 1024;
	private static final int SHADOW_MAP_HEIGHT = 1024;

	public final int shadowMapTextureID;
	private final int frameBufferID;
	private final FloatBuffer modelViewMatrix = BufferUtils.createFloatBuffer(16);
	private final FloatBuffer lightModelViewMatrix = BufferUtils.createFloatBuffer(16);
	private final EmptyCoordinateNode controlledNode;
	private final FloatBuffer buffer;

	private ShadowMapShader shader;
	private boolean shaderHasBeenEnabled = false;
	
	public ShadowMappedLightNode(EmptyCoordinateNode controlledNode) {
		this.buffer = BufferUtils.createFloatBuffer(4);
		this.controlledNode = controlledNode;
		this.shadowMapTextureID = ShadowMapTextureGenerator.generateShadowMapTexture(SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT);
		this.frameBufferID = FrameBufferUtils.generateShadowMapFrameBuffer(shadowMapTextureID);
		try {
			this.shader = ShadowMapShader.createShadowMapShader(shadowMapTextureID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void preRender(RenderContext context) {
		modelViewMatrix.clear();
		lightModelViewMatrix.clear();
		context.pushMatrix();
		//inverse transformation getting down to "eye level"/first person to the player model
		//it completely depends on the structure of the sceneGraph. 
		//A better implementation: separate Light scene node that supplies the light transformation matrix and a Camera scene node that supplies the view matrix.
		//Then you can tell the light to render from its position, and also get its transformation matrix.
		Point mapLocation = controlledNode.getLocation();
		context.translate((float) -mapLocation.x, (float) -mapLocation.y, 0);
		context.rotate((float) (-1*controlledNode.getRotationZ()), 0, 0, 1);
		setLightPosition(0);
		context.translate(0,(float) -GameSettings.playerYOffset, 10);
		context.rotate(-90, 1, 0, 0);
		context.rotate((float) controlledNode.getRotationZ(), 0, 0, 1);
		context.translate((float) mapLocation.x, (float) (mapLocation.y), -0.5f);
		context.storeModelViewMatrix(lightModelViewMatrix);
		context.pushMatrix();
		renderDepthTexture(context);
		context.popMatrix();
		context.popMatrix();
	}

	private void setLightPosition(double lightDirection) {
		
		glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer)buffer.put(new float[]{0.0f, 0.0f, 0.0f, 1}).rewind());
		glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer)buffer.put(new float[]{1f, 1f, 1f, 1}).rewind());
		glLight(GL_LIGHT0, GL_SPECULAR, (FloatBuffer)buffer.put(new float[]{1f, 1f, 1f, 1}).rewind());
		glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer)buffer.put(new float[]{0, 0, 1f, 1}).rewind());
		glLight(GL_LIGHT0, GL_SPOT_CUTOFF, (FloatBuffer)buffer.put(new float[]{30}).rewind());
		glLight(GL_LIGHT0, GL_SPOT_DIRECTION, (FloatBuffer)buffer.put(new float[]{0, 1, 0}).rewind());
	}

	private void renderDepthTexture(RenderContext context) {
		if(!GameSettings.shadowsEnabled) {
			return;
		}
		glBindFramebuffer(GL_FRAMEBUFFER, frameBufferID);
		glViewport(0, 0, SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT);
		glClear(GL_DEPTH_BUFFER_BIT);
		glColorMask(false, false, false, false); //disable color rendering
		renderAllChildNodes(context);
		glBindFramebuffer(GL_FRAMEBUFFER, 0); //reset the frame buffer
		FrameUtils.setViewport();//reset the viewport
		glColorMask(true, true, true, true); //re-enable color rendering
	}

	private void renderAllChildNodes(RenderContext context) {
		for(SceneNode child : children) {
			RenderPass.render(child, context);
		}
	}

	public void render(RenderContext context) {
		//context.storeModelViewMatrix(modelViewMatrix);
		//context.pushMatrix();
		//context.setIdentity();
		if(GameSettings.shadowsEnabled) {			
			shader.enable(modelViewMatrix, lightModelViewMatrix, controlledNode.getLocation());
			this.shaderHasBeenEnabled = true;
		}
	}

	public void postRender(RenderContext context) {
		//context.popMatrix();
		if(shaderHasBeenEnabled) {			
			shader.disable();
		}
		shaderHasBeenEnabled = false;
	}

	public void destroy() {

	}

}
