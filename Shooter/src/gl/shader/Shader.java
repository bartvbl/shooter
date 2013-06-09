package gl.shader;

import org.lwjgl.opengl.ARBShaderObjects;

public class Shader {
	private final int programID;

	public Shader(int ARBProgramID) {
		this.programID = ARBProgramID;
	}
	
	public void enable() {
		ARBShaderObjects.glUseProgramObjectARB(programID);
	}

	public void disable() {
		ARBShaderObjects.glUseProgramObjectARB(0);
	}
}
