package gl.shader;

import geom.Point;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;

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
		
		glActiveTexture(GL_TEXTURE0);
		
		int textureLocation = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "DepthMap");
		int lightMatrixLocation = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "LightMatrixValue");
		int modelMatrixLocation = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "ViewMatrixValue");
		int lightPosition = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "LightPosition");
		int texture0Location = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "texture0");
		
		ARBShaderObjects.glUniform1iARB(textureLocation, shadowMapTextureID);
		ARBShaderObjects.glUniformMatrix4ARB(lightMatrixLocation, false, lightModelViewMatrix);
		ARBShaderObjects.glUniformMatrix4ARB(modelMatrixLocation, false, modelViewMatrix);
		ARBShaderObjects.glUniform4ARB(lightPosition, lightPositionBuffer);
		ARBShaderObjects.glUniform1iARB(texture0Location, 0);
	}

	public void disable() {
		ARBShaderObjects.glUseProgramObjectARB(0);
	}

	
}
