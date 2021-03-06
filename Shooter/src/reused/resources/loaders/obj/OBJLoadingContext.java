package reused.resources.loaders.obj;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import reused.resources.twoStageLoadables.BlueprintMaterial;
import reused.resources.twoStageLoadables.PartiallyLoadableModelPart;

public class OBJLoadingContext {
	private String currentLine;
	private BlueprintMaterial currentMaterial;
	private HashMap<String, BlueprintMaterial> materials;
	private List<PartiallyLoadableModelPart> modelParts;
	private TemporaryVertexBuffer temporaryVertesBuffer;
	private File containingDirectory;
	private PartiallyLoadableModelPart currentModelPart = null;
	
	public OBJLoadingContext(File containingDirectory, OBJStatsContext statsContext)
	{
		this.temporaryVertesBuffer = new TemporaryVertexBuffer(statsContext.getTotalVertices(), statsContext.getTotalTexCoords(), statsContext.getTotalNormals(), statsContext.getBufferDataFormat());
		this.materials = new HashMap<String, BlueprintMaterial>(5);
		this.modelParts = statsContext.generateModelParts();
		this.containingDirectory = containingDirectory;
	}

	public void setCurrentLine(String line) {
		this.currentLine = line;
	}
	public String getCurrentLine() {
		return this.currentLine;
	}
	
	public void addMaterial(BlueprintMaterial material) {
		this.materials.put(material.name, material);
	}
	public void setMaterial(String materialName) {
		this.currentMaterial = this.materials.get(materialName);
		if(this.currentModelPart != null) {
			this.currentModelPart.setMaterial(this.currentMaterial);
		}
	}
	public BlueprintMaterial getCurrentMaterial() {
		return this.currentMaterial;
	}
	
	public void setCurrentModelPart(String partName) {
		for(PartiallyLoadableModelPart part : this.modelParts) {
			if(part.name.equals(partName)) {
				this.currentModelPart = part;
				part.setMaterial(this.currentMaterial);
				return;
			}
		}
	}
	public List<PartiallyLoadableModelPart> getModelParts() {
		return this.modelParts;
	}
	
	public File getContainingDirectory() {
		return this.containingDirectory;
	}
	
	public TemporaryVertexBuffer getBuffergenerator() {
		return this.temporaryVertesBuffer;
	}

	public void addVertexToCurrentModelPart(double[] vertex) {
		this.currentModelPart.addVertex(vertex);
	}

	public void destroy() {
		this.temporaryVertesBuffer.destroy();
		this.temporaryVertesBuffer = null;
		this.modelParts = null;
		this.materials = null;
		this.currentMaterial = null;
		this.currentModelPart = null;
	}
}