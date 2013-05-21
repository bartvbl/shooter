package gl.shadowMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL14.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class ShadowMapTextureGenerator {
	public static int generateShadowMapTexture(int width, int height) {
		int textureID = generateTextureID();
		
		glEnable(GL_TEXTURE_2D);
		
		//binding the texture for configuration
		glBindTexture(GL_TEXTURE_2D, textureID);
		
		//texture settings
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		
		glTexParameteri(GL_TEXTURE_2D, GL_DEPTH_TEXTURE_MODE, GL_INTENSITY);
		
		//the texture will be filled when rendering the scene. This just creates an empty one.
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_UNSIGNED_BYTE, (ByteBuffer)null);
		
		return textureID;
	}

	private static int generateTextureID() {
		IntBuffer textureBuffer = BufferUtils.createIntBuffer(1);
		glGenTextures(textureBuffer);
		return textureBuffer.get(0);
	}
}
