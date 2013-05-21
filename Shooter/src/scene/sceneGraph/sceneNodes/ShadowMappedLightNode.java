package scene.sceneGraph.sceneNodes;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import core.FrameUtils;

import gl.FrameBufferUtils;
import gl.shader.ShadowMapShader;
import gl.shadowMap.ShadowMapTextureGenerator;
import render.RenderContext;
import render.RenderPass;
import scene.sceneGraph.ContainerNode;
import scene.sceneGraph.SceneNode;

public class ShadowMappedLightNode extends ContainerNode implements SceneNode {
	private static final int SHADOW_MAP_WIDTH = 1024;
	private static final int SHADOW_MAP_HEIGHT = 1024;
	
	public final int shadowMapTextureID;
	private final int frameBufferID;
	private final FloatBuffer modelViewMatrix = BufferUtils.createFloatBuffer(16);
	private final FloatBuffer lightModelViewMatrix = BufferUtils.createFloatBuffer(16);

	private ShadowMapShader shader;
	
	public ShadowMappedLightNode() {
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
		
//		context.pushMatrix();
//		context.storeModelViewMatrix(modelViewMatrix);
		//inverse transformation getting down to "eye level" to the player model
//		context.translate(0, 0, 10);
//		context.rotate(-90, 1, 0, 0);
//		context.translate(0, 0, -0.5f);
//		context.storeModelViewMatrix(lightModelViewMatrix);
//		renderDepthTexture(context);
//		context.popMatrix();
	}

	private void renderDepthTexture(RenderContext context) {
//		glBindFramebuffer(GL_FRAMEBUFFER, frameBufferID);
//		glViewport(0, 0, SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT);
//		glClear(GL_DEPTH_BUFFER_BIT);
//		glColorMask(false, false, false, false); //disable color rendering
//		renderAllChildNodes(context);
//		glBindFramebuffer(GL_FRAMEBUFFER, 0); //reset the frame buffer
//		FrameUtils.setViewport();
//		glColorMask(true, true, true, true); //re-enable color rendering
	}

	private void renderAllChildNodes(RenderContext context) {
		for(SceneNode child : children) {
			RenderPass.render(child, context);
		}
	}

	public void render(RenderContext context) {
		//shader.enable(modelViewMatrix, lightModelViewMatrix);
	}

	public void postRender(RenderContext context) {
		//shader.disable();
	}

	public void destroy() {

	}

}
