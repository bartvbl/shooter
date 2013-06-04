package shooter.dialogue;

import gl.texture.Texture;
import gl.texture.TextureLoader;

public enum DialogueSequence {
	INTRO(new String[]{"res/textures/dialogue1.png",
					   "res/textures/dialogue2.png",
					   "res/textures/dialogue3.png",
					   "res/textures/dialogue4.png"}),
	BOSS_ENTER(new String[]{"res/textures/dialogue5.png"});
	
	public final Texture[] dialogueTextures;
	
	private DialogueSequence(String[] textureSources) {
		dialogueTextures = new Texture[textureSources.length];
		for(int i = 0; i < textureSources.length; i++) {
			dialogueTextures[i] = TextureLoader.loadTextureFromFile(textureSources[i]);
		}
	}
}
