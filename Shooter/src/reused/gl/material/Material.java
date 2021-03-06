package reused.gl.material;


import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import render.RenderContext;
import reused.gl.texture.Texture;
import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyContainerNode;


import static org.lwjgl.opengl.GL11.*;

public class Material extends EmptyContainerNode implements SceneNode, AbstractMaterial {
	public final String name;
	private float[] ambientColour;
	private float[] diffuseColour;
	private float[] specularColour;
	private float[] emissionColour;
	private float shininess;
	private float alpha;
	private Texture ambientTexture = null;
	private Texture diffuseTexture = null;
	private Texture specularTexture = null;
	private FloatBuffer colourBuffer = BufferUtils.createFloatBuffer(4);
	
	public Material(String name)
	{
		this.name = name;
		this.ambientColour = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
		this.diffuseColour = new float[]{0.7f, 0.7f, 0.7f, 1.0f};
		this.specularColour = new float[]{0.4f, 0.4f, 0.4f, 1.0f};
		this.emissionColour = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
		this.alpha = 1f;
		this.shininess = 12f;
	}
	
	public void setAmbientTexture(Texture texture)
	{
		this.ambientTexture = texture;
	}
	public void setDiffuseTexture(Texture texture)
	{
		this.diffuseTexture = texture;
	}
	public void setSpecularTexture(Texture texture)
	{
		this.specularTexture = texture;
	}
	
	public void setAmbientColour(float[] colour)
	{
		colour = formatColour(colour);
		this.ambientColour = colour;
	}
	public void setDiffuseColour(float[] colour)
	{
		colour = formatColour(colour);
		this.diffuseColour = colour;
	}
	public void setSpecularColour(float[] colour)
	{
		colour = formatColour(colour);
		this.specularColour = colour;
	}
	public void setEmissionColour(float[] colour)
	{
		colour = formatColour(colour);
		this.emissionColour = colour;
	}
	
	public void setShininess(float shininess) {
		this.shininess = shininess;
	}
	
	private float[] formatColour(float[] colour)
	{
		if(colour.length == 3)
		{
			return new float[]{colour[0], colour[1], colour[2], 1f};
		}
		return colour;
	}
	
	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}
	
	public void render(RenderContext context) 
	{
		this.bindTexture();
		glMaterialf(GL_FRONT, GL_SHININESS, shininess);
		glMaterial(GL_FRONT, GL_AMBIENT, this.fillColourBuffer(this.ambientColour, this.alpha));
		glMaterial(GL_FRONT, GL_DIFFUSE, this.fillColourBuffer(this.diffuseColour, this.alpha));
		glMaterial(GL_FRONT, GL_SPECULAR, this.fillColourBuffer(this.specularColour, this.alpha));
		glMaterial(GL_FRONT, GL_EMISSION, this.fillColourBuffer(this.emissionColour, this.alpha));
	}
	
	private void bindTexture() {
		if(this.diffuseTexture != null) {
			glEnable(GL_TEXTURE_2D);
			this.diffuseTexture.bind();
		} else {
			glDisable(GL_TEXTURE_2D);
		}
	}

	private FloatBuffer fillColourBuffer(float[] colour, float alpha) {
		this.colourBuffer.put(colour[0]);
		this.colourBuffer.put(colour[1]);
		this.colourBuffer.put(colour[2]);
		this.colourBuffer.put(alpha);
		this.colourBuffer.rewind();
		return this.colourBuffer;
	}
	
	public void destroy(){}
	
	public Material clone() {
		Material material = new Material(this.name);
		material.setAlpha(this.alpha);
		material.setAmbientColour(this.ambientColour);
		material.setDiffuseColour(this.diffuseColour);
		material.setSpecularColour(this.specularColour);
		material.setEmissionColour(this.emissionColour);
		material.setShininess(this.shininess);
		if(this.ambientTexture != null) {material.setAmbientTexture(this.ambientTexture.clone());}
		if(this.diffuseTexture != null) {material.setDiffuseTexture(this.diffuseTexture.clone());}
		if(this.specularTexture != null) {material.setSpecularTexture(this.specularTexture.clone());}
		return material;
	}

}
