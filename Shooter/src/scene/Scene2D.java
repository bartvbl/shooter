package scene;

import core.FrameUtils;
import shooter.GameWorld;
import gui.HUD;

public class Scene2D {
	private final HUD hud;
	private final GameWorld world;

	public Scene2D(GameWorld world) {
		this.world = world;
		this.hud = new HUD(world);
	}

	public void render() {
		FrameUtils.set2DMode();
		hud.render();
		if(world.dialogueHandler.isActive()) {
			world.dialogueHandler.render();
		}
	}
}
