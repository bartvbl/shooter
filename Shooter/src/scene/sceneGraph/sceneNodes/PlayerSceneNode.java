package scene.sceneGraph.sceneNodes;

import geom.mesh.Mesh3D;
import geom.mesh.ModelPart;

import java.util.ArrayList;

import orre.resources.loaders.obj.ModelLoader;
import orre.resources.twoStageLoadables.BlueprintModel;
import render.RenderContext;
import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;

import static org.lwjgl.opengl.GL11.*;

public class PlayerSceneNode extends StackingContainerNode implements SceneNode {
	private boolean isAnimationPlaying = false;
	private final Mesh3D playerMesh;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private double playHead = 0;
	private boolean isReversed = false;
	
	private static final double UPPER_BOUND = 45;
	private static final double LOWER_BOUND = -45;
	
	public PlayerSceneNode() {
		BlueprintModel model = ModelLoader.loadModel("res/mesh/ak.mdl", "AK");
		model.finalizeResource();
		this.playerMesh = model.createSceneNode();
		this.addChild(playerMesh);
		
		this.leftLeg = playerMesh.getModelPartByName("leftLeg");
		this.rightLeg = playerMesh.getModelPartByName("rightLeg");
	}

	public void destroy() {
		
	}

	public void render(RenderContext context) {
		context.rotate(90, 1, 0, 0);
		context.scale(0.03f, 0.03f, 0.03f);
	}

	public void updateLegRotation(double degrees) {
		isAnimationPlaying = true;
		leftLeg.setRotationY(-degrees);
		rightLeg.setRotationY(-degrees);
	}

	//simulating animation here.
	public void updateAnimation() {
		if(isAnimationPlaying) {
			if(isReversed) {
				playHead -= 4;
				if(playHead < LOWER_BOUND) {
					isReversed = false;
				}
			} else {
				playHead += 4;
				if(playHead > UPPER_BOUND) {
					isReversed = true;
				}
			}
			leftLeg.setRotationX(-playHead);
			rightLeg.setRotationX(playHead);
		} else {
			playHead = 0;
			isReversed = false;
			leftLeg.setRotationY(0);
			rightLeg.setRotationY(0);
		}
		isAnimationPlaying = false;
	}

}
