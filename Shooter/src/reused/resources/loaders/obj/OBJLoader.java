package reused.resources.loaders.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import reused.resources.twoStageLoadables.PartiallyLoadableModelPart;
import reused.util.StringUtils;

public class OBJLoader {
	public static List<PartiallyLoadableModelPart> load(String src)
	{
		try {
			return loadObjFile(src);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static List<PartiallyLoadableModelPart> loadObjFile(String src) throws FileNotFoundException, IOException {
		BufferedReader objFileAnalysisReader = openObjFile(src);
		OBJStatsContext statsContext = new OBJStatsContext();
		analyseObjFile(objFileAnalysisReader, statsContext);
		objFileAnalysisReader.close();
		
		BufferedReader objFileParsingReader = openObjFile(src);
		OBJLoadingContext context = new OBJLoadingContext(new File(src).getParentFile(), statsContext);
		List<PartiallyLoadableModelPart> parts = parseOBJFile(objFileParsingReader, context);
		objFileParsingReader.close();
		
		context.destroy();
		return parts;
	}

	private static BufferedReader openObjFile(String src) throws FileNotFoundException {
		FileReader fileReader = new FileReader(src);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		return bufferedReader;
	}
	
	private static void analyseObjFile(BufferedReader bufferedReader, OBJStatsContext context) throws IOException {
		while(bufferedReader.ready())
		{
			String line = bufferedReader.readLine();
			line = StringUtils.stripString(line);
			OBJStatsLineReader.readOBJLine(context, line);
		}
	}
	
	private static List<PartiallyLoadableModelPart> parseOBJFile(BufferedReader bufferedReader, OBJLoadingContext context) throws IOException {
		while(bufferedReader.ready())
		{
			String line = bufferedReader.readLine();
			line = StringUtils.stripString(line);
			context.setCurrentLine(line);
			parseOBJLine(context);
		}
		return context.getModelParts();
	}

	private static void parseOBJLine(OBJLoadingContext context) {
		if((context.getCurrentLine().length() == 0) || (context.getCurrentLine().charAt(0) == '#'))
		{
			return;
		}
		OBJFileLineReader.readOBJLine(context);
	}
}
