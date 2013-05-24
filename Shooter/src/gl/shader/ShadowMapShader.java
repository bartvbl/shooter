package gl.shader;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;

public class ShadowMapShader {
	private static final String vertexShaderSrc = "res/shaders/shadowMap.vert";
	private static final String fragmentShaderSrc = "res/shaders/shadowMap.frag";
	private static final FloatBuffer lightPositionBuffer = BufferUtils.createFloatBuffer(4);
	private static final float[] lightPosition = new float[]{0, 0, 0.5f, 1};
	
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

	public void enable(FloatBuffer modelViewMatrix, FloatBuffer lightModelViewMatrix) {
		modelViewMatrix.rewind();
		lightModelViewMatrix.rewind();
		lightPositionBuffer.put(lightPosition).rewind();
		
		int textureLocation = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "DepthMap");
		int lightMatrixLocation = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "LightMatrixValue");
		int modelMatrixLocation = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "ViewMatrixValue");
		int lightPosition = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "LightPosition");
		
		ARBShaderObjects.glUniform1iARB(textureLocation, shadowMapTextureID);
		ARBShaderObjects.glUniformMatrix4ARB(lightMatrixLocation, false, lightModelViewMatrix);
		ARBShaderObjects.glUniformMatrix4ARB(modelMatrixLocation, false, modelViewMatrix);
		ARBShaderObjects.glUniform4ARB(lightPosition, lightPositionBuffer);
		
		ARBShaderObjects.glUseProgramObjectARB(shaderProgramID);
	}

	public void disable() {
		ARBShaderObjects.glUseProgramObjectARB(0);
	}

	
}
