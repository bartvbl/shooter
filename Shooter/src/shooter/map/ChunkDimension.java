package shooter.map;

public class ChunkDimension {

	public final int chunkLeft;
	public final int chunkRight;
	public final int chunkTop;
	public final int chunkBottom;

	public ChunkDimension(int chunkLeft, int chunkRight, int chunkBottom, int chunkTop) {
		this.chunkLeft = chunkLeft;
		this.chunkRight = chunkRight;
		this.chunkTop = chunkTop;
		this.chunkBottom = chunkBottom;
	}

}
