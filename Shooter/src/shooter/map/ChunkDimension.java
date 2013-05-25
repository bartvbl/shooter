package shooter.map;

import org.lwjgl.util.Rectangle;

public class ChunkDimension {

	public final int chunkLeft;
	public final int chunkRight;
	public final int chunkTop;
	public final int chunkBottom;

	public ChunkDimension(Rectangle chunkDimension) {
		this.chunkLeft = chunkDimension.getX();
		this.chunkBottom = chunkDimension.getY();
		this.chunkRight = chunkLeft + chunkDimension.getWidth();
		this.chunkTop = chunkBottom + chunkDimension.getHeight();
	}

}
