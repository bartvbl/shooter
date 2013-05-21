package scene.sceneGraph.sceneNodes;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import core.FrameUtils;

import gl.FrameBufferUtils;
import gl.shadowMap.ShadowMapTextureGenerator;
import render.RenderPass;
import scene.sceneGraph.ContainerNode;
import scene.sceneGraph.SceneNode;

public class ShadowMappedLightNode extends ContainerNode implements SceneNode {
	private static final int SHADOW_MAP_WIDTH = 1024;
	private static final int SHADOW_MAP_HEIGHT = 1024;
	
	private final int shadowMapTextureID;
	private final int frameBufferID;
	
	public ShadowMappedLightNode() {
		this.shadowMapTextureID = ShadowMapTextureGenerator.generateShadowMapTexture(SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT);
		this.frameBufferID = FrameBufferUtils.generateShadowMapFrameBuffer(shadowMapTextureID);
	}

	public void preRender() {
		glPushMatrix();
		renderDepthTexture();
		glPopMatrix();
	}

	private void renderDepthTexture() {
		glBindFramebuffer(GL_FRAMEBUFFER, frameBufferID);
		glViewport(0, 0, SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT);
		glClear(GL_DEPTH_BUFFER_BIT);
		glColorMask(false, false, false, false); //disable color rendering
		renderAllChildNodes();
		glBindFramebuffer(GL_FRAMEBUFFER, 0); //reset the frame buffer
		FrameUtils.setViewport();
		glColorMask(true, true, true, true); //re-enable color rendering
	}

	private void renderAllChildNodes() {
		for(SceneNode child : children) {
			RenderPass.render(child);
		}
	}

	public void render() {
		//set up scene for rendering with shadow map (enable shaders, reset frame buffer etc)
	}

	public void postRender() {
		//close down shaders
	}

	public void destroy() {

	}

}
