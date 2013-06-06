package gl.shadowMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class ShadowMapTextureGenerator {
	private static final FloatBuffer colourBuffer = BufferUtils.createFloatBuffer(4);
	
	public static int generateShadowMapTexture(int width, int height) {
		int textureID = generateTextureID();
		System.out.println("generated ID for shadow map texture: " + textureID);
		
		glEnable(GL_TEXTURE_2D);
		
		//binding the texture for configuration
		glBindTexture(GL_TEXTURE_2D, textureID);
		
		//the texture will be filled when rendering the scene. This just creates an empty one.
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer)null);
		
		colourBuffer.put(new float[]{0, 0, 0, 1}).rewind();
		
		//texture settings
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameter(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, colourBuffer);
		return textureID;
	}

	private static int generateTextureID() {
		IntBuffer textureBuffer = BufferUtils.createIntBuffer(1);
		glGenTextures(textureBuffer);
		return textureBuffer.get(0);
	}
}
