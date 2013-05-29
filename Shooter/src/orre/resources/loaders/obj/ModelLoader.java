package orre.resources.loaders.obj;

import java.util.List;

import orre.resources.twoStageLoadables.BlueprintModel;
import orre.resources.twoStageLoadables.PartiallyLoadableModelPart;
import util.XMLLoader;

import nu.xom.Document;
import nu.xom.Element;

public class ModelLoader {
	public static BlueprintModel loadModel(String src, String name)
	{
		Document modelXMLDocument = XMLLoader.readXML(src);
		Element rootElement = modelXMLDocument.getRootElement();
		BlueprintModel model = new BlueprintModel(name);
		ModelPartTreeBuilder.generatePartTree(model, rootElement);
		List<PartiallyLoadableModelPart> parts = loadOBJFile(model, rootElement);
		linkPartsToPartTree(model, parts);
		addPartsToFinalizationQueue(parts);
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
	
	private static void addPartsToFinalizationQueue(List<PartiallyLoadableModelPart> parts) {
		for(PartiallyLoadableModelPart part : parts) {
			part.finalizeResource();
		}
	}
}
