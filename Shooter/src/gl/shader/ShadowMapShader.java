package gl.shader;

import geom.Point;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class ShadowMapShader {
	private static final String vertexShaderSrc = "res/shaders/shadowMap.vert";
	private static final String fragmentShaderSrc = "res/shaders/shadowMap.frag";
	private static final FloatBuffer lightPositionBuffer = BufferUtils.createFloatBuffer(4);
	
	private final int shaderProgramID;
	private final int shadowMapTextureID;

	private ShadowMapShader(int shaderProgramID, int shadowMapTextureID) {
		this.shadowMapTextureID = shadowMapTextureID;
		this.shaderProgramID = shaderProgramID;
	}

	public static ShadowMapShader createShadowMapShader(int shadowMapTextureID) throws Exception {
		int programID = ShaderLoader.fromFiles(vertexShaderSrc, fragmentShaderSrc);
		return new ShadowMapShader(programID, shadowMapTextureID);
	}

	public void enable(FloatBuffer modelViewMatrix, FloatBuffer lightModelViewMatrix, Point mapLocation) {
		ARBShaderObjects.glUseProgramObjectARB(shaderProgramID);
 
		modelViewMatrix.rewind();
		lightModelViewMatrix.rewind();
		lightPositionBuffer.put(new float[]{(float) -mapLocation.x, (float) -mapLocation.y, -0.5f, 1}).rewind();
		
		int textureLocation = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "depthMap");
		int lightMatrixLocation = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "LightMatrixValue");
		int modelMatrixLocation = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "ViewMatrixValue");
		int lightPosition = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "LightPosition");
		
		ARBShaderObjects.glUniformMatrix4ARB(lightMatrixLocation, false, lightModelViewMatrix);
		ARBShaderObjects.glUniformMatrix4ARB(modelMatrixLocation, false, modelViewMatrix);
		ARBShaderObjects.glUniform4ARB(lightPosition, lightPositionBuffer);
		
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, shadowMapTextureID);
		ARBShaderObjects.glUniform1iARB(textureLocation, 1);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void disable() {
		ARBShaderObjects.glUseProgramObjectARB(0);
	}

	
}
