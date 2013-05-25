package orre.resources.loaders.obj;

import java.util.List;

import orre.resources.FileToLoad;
import orre.resources.twoStageLoadables.BlueprintModel;
import orre.resources.twoStageLoadables.PartiallyLoadableModelPart;
import util.XMLLoader;

import nu.xom.Document;
import nu.xom.Element;

public class ModelLoader {
	public static BlueprintModel loadModel(FileToLoad file)
	{
		Document modelXMLDocument = XMLLoader.readXML(file.getPath());
		Element rootElement = modelXMLDocument.getRootElement();
		BlueprintModel model = new BlueprintModel(file.name);
		ModelPartTreeBuilder.generatePartTree(model, rootElement);
		List<PartiallyLoadableModelPart> parts = loadOBJFile(model, rootElement);
		linkPartsToPartTree(model, parts);
		finalizeParts(parts);
		return model;
	}
	
	private static List<PartiallyLoadableModelPart> loadOBJFile(BlueprintModel model, Element rootElement) {
		
		Element modelFileElement = rootElement.getFirstChildElement("modelFile");
		List<PartiallyLoadableModelPart> parts = OBJLoader.load(modelFileElement.getAttributeValue("src"));
		return parts;
	}
	
	private static void linkPartsToPartTree(BlueprintModel model, List<PartiallyLoadableModelPart> parts) {
		for(PartiallyLoadableModelPart part : parts) {
			model.linkGeometryPartToModelPart(part.name, part);
		}
	}
	
	private static void finalizeParts(List<PartiallyLoadableModelPart> parts) {
		for(PartiallyLoadableModelPart part : parts) {
			part.finalizeResource();
		}
	}
}
