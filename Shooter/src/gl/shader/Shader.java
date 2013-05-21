package gl.shader;

import org.lwjgl.opengl.ARBShaderObjects;

public class Shader {
	private final int programID;
	private final int textureID;

	public Shader(int ARBProgramID, int textureID) {
		this.programID = ARBProgramID;
		this.textureID = textureID;
	}
	
	public void enable() {
		int location = ARBShaderObjects.glGetUniformLocationARB(programID, "tex");
		ARBShaderObjects.glUniform1iARB(location, textureID);
		ARBShaderObjects.glUseProgramObjectARB(programID);
	}

	public void disable() {
		ARBShaderObjects.glUseProgramObjectARB(0);
	}
}
