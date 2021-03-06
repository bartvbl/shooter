package reused.resources.twoStageLoadables;

import reused.gl.texture.Texture;
import reused.gl.texture.TextureLoader;
import reused.resources.Finalizable;
import scene.sceneGraph.SceneNode;

public class PartiallyLoadableTexture extends Finalizable{
	private byte[] imageData;
	private final int width;
	private final int height;
	private Texture tex;
	public final String name;
	private boolean hasBeenFinalized = false;

	public PartiallyLoadableTexture(String name, byte[] imageData, int width, int height) {
		this.imageData = imageData;
		this.width = width;
		this.height = height;
		this.name = name;
	}

	public void finalizeResource() {
		if(hasBeenFinalized) return;
		this.hasBeenFinalized = true;
		Texture tex = TextureLoader.createTexture(imageData, width, height);
		this.tex = tex;
		this.imageData = null;
	}
	
	public SceneNode createSceneNode()
	{
		System.out.println("WARNING: textures are not supported for scene nodes. Maybe they will ato some point, though.");
		return null;
	}
	
	public Texture getTexture()
	{
		return this.tex;
	}

}
