package orre.resources;

import java.io.File;


public class FileToLoad {
	private String src;
	public final ResourceFile fileType;
	public String pathPrefix = "";
	public final String name;
	
	public FileToLoad(ResourceFile fileType, String src, String name)
	{
		this.fileType = fileType;
		this.src = src;
		this.name = name;
	}
	
	public String getPath()
	{
		if(this.pathPrefix.length() == 0)
		{
			return this.src;
		} else {
			return this.pathPrefix + File.separator + this.src;
		}
	}
	
	public String toString()
	{
		return "File to load: " + fileType + " located at " + src;
	}
}