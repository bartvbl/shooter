package gl.vbo;

import static org.lwjgl.opengl.ARBBufferObject.glBindBufferARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

import java.util.ArrayList;

import render.RenderContext;
import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyCoordinateNode;

public class GeometryBuffer extends EmptyCoordinateNode implements SceneNode {
	private final int indexBuffer;
	private final int vertexBuffer;
	private final BufferDataFormatType dataFormat;
	private int numberOfVertices;

	public GeometryBuffer(int indexBuffer, int vertexBuffer, BufferDataFormatType dataFormat, int numVertices)
	{
		this.indexBuffer = indexBuffer;
		this.vertexBuffer = vertexBuffer;
		this.dataFormat = dataFormat;
		this.numberOfVertices = numVertices;
	}
	
	public void render(RenderContext context)
	{
		this.drawBufferCombo(this.indexBuffer, this.vertexBuffer);
	}
	
	private void drawBufferCombo(int indexBufferID, int vertexBufferID)
	{
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vertexBufferID);
		glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, indexBufferID);
		this.setDataPointers();
		
		glDrawElements(GL_TRIANGLES, this.numberOfVertices, GL_UNSIGNED_INT, 0);
		//glDrawRangeElements(GL_TRIANGLES, 0, this.numberOfVertices - 1, this.numberOfVertices - 1, GL_UNSIGNED_INT, 0);
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_NORMAL_ARRAY);
	}

	private void setDataPointers() {
		//format: 0    1    2    3   4   5    6    7
		//format: vert vert vert tex tex norm norm norm	
		int bytesPerDouble = 8;
		int stride = this.dataFormat.elementsPerVertex * bytesPerDouble;
		glEnableClientState(GL_VERTEX_ARRAY);
		glVertexPointer(3, GL_DOUBLE, stride, 0 * bytesPerDouble);
		switch(this.dataFormat)
		{
			case VERTICES:
				break;//already set pointer.
			case VERTICES_AND_TEXTURES:
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glTexCoordPointer(2, GL_DOUBLE, stride, 3 * bytesPerDouble);
				break;
			case VERTICES_AND_NORMALS:
				glEnableClientState(GL_NORMAL_ARRAY);
				glNormalPointer(GL_DOUBLE, stride, 3 * bytesPerDouble);
				break;
			case VERTICES_TEXTURES_NORMALS:
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glTexCoordPointer(2, GL_DOUBLE, stride, 3 * bytesPerDouble);
				
				glEnableClientState(GL_NORMAL_ARRAY);
				glNormalPointer(GL_DOUBLE, stride, (3 + 2) * bytesPerDouble);	
				break;
		default:
			break;
		}
	}

	@Override
	public void destroy() {
		
		
	}
}
