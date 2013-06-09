package reused.gl.vbo;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL15.*;
import org.lwjgl.opengl.GLContext;

public class VBOUtils {
	
	public static int createBuffer() {
		if (supportsBuffers()) 
		{
			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			glGenBuffers(buffer);
			return buffer.get(0);
		}
		return 0;
	}
	
	public static void storeVertexData(int bufferIndex, DoubleBuffer geometryData) {
		if (supportsBuffers()) 
		{
			glBindBuffer(GL_ARRAY_BUFFER, bufferIndex);
			glBufferData(GL_ARRAY_BUFFER, geometryData, GL_STATIC_DRAW);
		}
	}
	
	public static void storeIndexData(int bufferIndex, IntBuffer indexes) {
		if (supportsBuffers()) 
		{
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferIndex);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexes, GL_STATIC_DRAW);
		}
	}
	
	public static void destroyBuffers(ArrayList<Integer> bufferList) {
		if(!supportsBuffers())
		{
			return;
		}
		IntBuffer bufferIDBuffer = BufferUtils.createIntBuffer(bufferList.size());
		for(int i = 0; i < bufferList.size(); i++)
		{
			bufferIDBuffer.put(bufferList.get(i));
		}
		bufferIDBuffer.rewind();
		glDeleteBuffers(bufferIDBuffer);
	}
	
	private static boolean supportsBuffers()
	{
		return GLContext.getCapabilities().OpenGL15;
	}
}
