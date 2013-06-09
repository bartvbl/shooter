package reused.resources.twoStageLoadables;

import java.io.File;

import reused.gl.material.AbstractMaterial;
import reused.gl.material.Material;
import reused.gl.texture.TextureLoader;
import reused.resources.Finalizable;
import reused.resources.loaders.obj.OBJLoadingContext;
import scene.sceneGraph.SceneNode;

public class BlueprintMaterial extends Finalizable implements AbstractMaterial {
	public final String name;
	private PartiallyLoadableTexture ambientTexture = null;
	private PartiallyLoadableTexture diffuseTexture = null;
	private PartiallyLoadableTexture specularTexture = null;
	private boolean isFinalized = false;
	
	private Material material;
	
	public BlueprintMaterial(String name)
	{
		this.name = name;
		this.material = new Material(name);
	}
	
	public void setAmbientColour(float[] colour) {
		this.material.setAmbientColour(colour);
	}
	public void setDiffuseColour(float[] colour) {
		this.material.setDiffuseColour(colour);
	}
	public void setSpecularColour(float[] colour) {
		this.material.setSpecularColour(colour);
	}
	public void setEmissionColour(float[] colour) {
		this.material.setEmissionColour(colour);
	}
	
	public void setShininess(float shininess) {
		this.material.setShininess(shininess);
	}
	
	public void setAlpha(float alpha) {
		this.material.setAlpha(alpha);
	}
	
	public void setAmbientTexture(String src, OBJLoadingContext context) {
		this.ambientTexture = TextureLoader.partiallyLoadTextureFromFile(context.getContainingDirectory().getPath() + File.separator + src);
	}
	public void setDiffuseTexture(String src, OBJLoadingContext context) {
		this.diffuseTexture = TextureLoader.partiallyLoadTextureFromFile(context.getContainingDirectory().getPath() + File.separator + src);
	}
	public void setSpecularTexture(String src, OBJLoadingContext context) {
		this.specularTexture = TextureLoader.partiallyLoadTextureFromFile(context.getContainingDirectory().getPath() + File.separator + src);
	}
	
	public void finalizeResource() {
		if(isFinalized){return;};
		if(this.ambientTexture != null) {			
			this.ambientTexture.finalizeResource();
			this.material.setAmbientTexture(this.ambientTexture.getTexture());
		}
		if(this.diffuseTexture != null) {	
			this.diffuseTexture.finalizeResource();
			this.material.setDiffuseTexture(this.diffuseTexture.getTexture());
		}
		if(this.specularTexture != null) {	
			this.specularTexture.finalizeResource();
			this.material.setSpecularTexture(this.specularTexture.getTexture());
		}
		this.isFinalized = true;
	}

	public SceneNode createSceneNode() {
		return null;
	}
	
	public Material convertToMaterial() {
		return this.material.clone();
	}

	public void addToCache() {}


	
}
