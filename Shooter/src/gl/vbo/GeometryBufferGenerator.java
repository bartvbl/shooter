package gl.vbo;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

public class GeometryBufferGenerator {
	
	public static GeometryBuffer generateGeometryBuffer(BufferDataFormatType dataFormat, DoubleBuffer vertexBuffer, IntBuffer indexBuffer) {
		int vertexCount = vertexBuffer.position() / dataFormat.elementsPerVertex;
		GeometryBuffer geometryBuffer = storeBuffersInVRAM(vertexBuffer, indexBuffer, dataFormat, vertexCount);
		return geometryBuffer;
	}
	
	public static GeometryBuffer generateGeometryBuffer(BufferDataFormatType dataFormat, double[] vertices, int[] indices) {
		int elementsPerVertex = dataFormat.elementsPerVertex;
		int vertexCount = vertices.length / elementsPerVertex;
		
		DoubleBuffer geometryData = BufferUtils.createDoubleBuffer(vertices.length);
		IntBuffer indexes = BufferUtils.createIntBuffer(indices.length);
		
		geometryData.put(vertices);
		indexes.put(indices);
		
		GeometryBuffer geometryBuffer = storeBuffersInVRAM(geometryData, indexes, dataFormat, vertexCount);
		
		return geometryBuffer;
	}

	private static GeometryBuffer storeBuffersInVRAM(DoubleBuffer geometryData, IntBuffer indexes, BufferDataFormatType dataFormat, int vertexCount)
	{
		geometryData.rewind();
		indexes.rewind();
		
		int vertexBufferID = VBOUtils.createBuffer();
		int indexBufferID = VBOUtils.createBuffer();
		
		VBOUtils.storeIndexData(indexBufferID, indexes);
		VBOUtils.storeVertexData(vertexBufferID, geometryData);
		
		return new GeometryBuffer(indexBufferID, vertexBufferID, dataFormat, vertexCount);
	}
}
