package scene.sceneGraph.sceneNodes;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import core.FrameUtils;

import geom.Point;
import gl.FrameBufferUtils;
import gl.shader.ShadowMapShader;
import gl.shadowMap.ShadowMapTextureGenerator;
import render.RenderContext;
import render.RenderPass;
import scene.sceneGraph.ContainerNode;
import scene.sceneGraph.SceneNode;
import shooter.GameWorld;

public class ShadowMappedLightNode extends ContainerNode implements SceneNode {
	private static final int SHADOW_MAP_WIDTH = 1024;
	private static final int SHADOW_MAP_HEIGHT = 1024;
	
	public final int shadowMapTextureID;
	private final int frameBufferID;
	private final FloatBuffer modelViewMatrix = BufferUtils.createFloatBuffer(16);
	private final FloatBuffer lightModelViewMatrix = BufferUtils.createFloatBuffer(16);
	private final EmptyCoordinateNode controlledNode;

	private ShadowMapShader shader;
	
	public ShadowMappedLightNode(EmptyCoordinateNode controlledNode) {
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
		context.storeModelViewMatrix(modelViewMatrix);
		//inverse transformation getting down to "eye level"/first person to the player model
		Point mapLocation = controlledNode.getLocation();
		context.translate((float) -mapLocation.x, (float) -mapLocation.y, 0);
		context.rotate((float) (-1*controlledNode.getRotationZ()), 0, 0, 1);
		context.translate(0, 0, 10);
		context.rotate(-90, 1, 0, 0);
		context.rotate((float) controlledNode.getRotationZ(), 0, 0, 1);
		context.translate((float) mapLocation.x, (float) mapLocation.y, -0.1f);
		context.storeModelViewMatrix(lightModelViewMatrix);
		renderDepthTexture(context.copyOf());
		context.popMatrix();
	}

	private void renderDepthTexture(RenderContext context) {
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
		shader.enable(modelViewMatrix, lightModelViewMatrix, controlledNode.getLocation());
	}

	public void postRender(RenderContext context) {
		shader.disable();
	}

	public void destroy() {

	}

}
