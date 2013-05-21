package gl.shader;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShadowMapShader {
	private static final String vertexShaderSrc = "res/shaders/shadowMap.vert";
	private static final String fragmentShaderSrc = "res/shaders/shadowMap.frag";

	private final int shaderProgramID;

	private ShadowMapShader(int shaderProgramID) {
		this.shaderProgramID = shaderProgramID;
	}

	public static ShadowMapShader createShadowMapShader() throws Exception {
		int programID = ShaderLoader.fromFiles(vertexShaderSrc, fragmentShaderSrc);
		return new ShadowMapShader(programID);
	}

	public void enable() {
		int textureLocation = ARBShaderObjects.glGetUniformLocationARB(shaderProgramID, "DepthMap");
		ARBShaderObjects.glUseProgramObjectARB(shaderProgramID);
	}

	public void disable() {
		ARBShaderObjects.glUseProgramObjectARB(0);
	}

	
}
