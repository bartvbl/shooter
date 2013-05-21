package gl.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;

public class ShaderLoader {
	public static int fromFiles(String vertexShaderSrc, String fragmentShaderSrc) throws Exception {		
		int vertShader = 0; 
		int fragShader = 0;
		int shaderProgramID = ARBShaderObjects.glCreateProgramObjectARB();
		
		if(shaderProgramID == 0) {
			throw new Exception("Failed to create ARB shader program!");
		}

		try {
			vertShader = createShader(vertexShaderSrc, ARBVertexShader.GL_VERTEX_SHADER_ARB);
			fragShader = createShader(fragmentShaderSrc, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if(vertShader == 0 || fragShader == 0) {
				throw new Exception("Failed to create vertex and fragment shader! ");
			}
		}

		ARBShaderObjects.glAttachObjectARB(shaderProgramID, vertShader);
		ARBShaderObjects.glAttachObjectARB(shaderProgramID, fragShader);

		ARBShaderObjects.glLinkProgramARB(shaderProgramID);
		if (ARBShaderObjects.glGetObjectParameteriARB(shaderProgramID, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL_FALSE) {
			System.err.println(getLogInfo(shaderProgramID));
			throw new Exception("Failed to link ARB shader program!");
		}

		ARBShaderObjects.glValidateProgramARB(shaderProgramID);
		if (ARBShaderObjects.glGetObjectParameteriARB(shaderProgramID, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL_FALSE) {
			System.err.println(getLogInfo(shaderProgramID));
			throw new Exception("Failed to validate ARB shader program!");
		}

		return shaderProgramID;
	}
	
	private static int createShader(String fileName, int shaderType) throws Exception {
		System.out.println("Loading ARB shader object: " + fileName);
		
		int shader = 0;
		shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

		if(shader == 0)
			return 0;

		ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(fileName));
		ARBShaderObjects.glCompileShaderARB(shader);

		if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL_FALSE) {
			throw new Exception("Error creating shader: " + getLogInfo(shader));				
		}
		System.out.println("created shader with ID: " + shader);
		
		return shader;
	}

	private static String getLogInfo(int obj) {
		return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
	}

	private static String readFileAsString(String filename) throws Exception {
		StringBuilder source = new StringBuilder();

		FileInputStream in = new FileInputStream(filename);

		BufferedReader reader;
		try{
			InputStreamReader streamReader = new InputStreamReader(in,"UTF-8");
			reader = new BufferedReader(streamReader);
			try {				
				while(reader.ready()) {
					String line = reader.readLine();				
					source.append(line);
					source.append('\n');
				}
			} catch(IOException e) {
				throw new Exception(e);
			} finally {
				reader.close();
				streamReader.close();
			}			
		} catch(IOException e) {
			throw new Exception(e);
		} finally {
			in.close();
		}

		return source.toString();
	}
}
