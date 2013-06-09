package scene.sceneGraph.sceneNodes;

import geom.Point;
import render.RenderContext;
import reused.geom.mesh.Mesh3D;
import reused.geom.mesh.ModelPart;
import reused.resources.loaders.obj.ModelLoader;
import reused.resources.twoStageLoadables.BlueprintModel;
import scene.sceneGraph.FrustrumCullingNode;
import shooter.GameWorld;
import shooter.map.generator.Direction;

import static org.lwjgl.opengl.GL11.*;


public class EnemyNode extends EmptyCoordinateNode {
	private static final double moveSpeed = 0.006;
	
	private Mesh3D enemyMesh;
	private final FrustrumCullingNode meshCullingNode;
	
	private final ModelPart body;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	
	private int transitionDistanceX = 0;
	private int transitionDistanceY = 0;
	private double distanceCoveredX = 0;
	private double distanceCoveredY = 0;
	private Direction transitionDirection = Direction.NORTH;
	private boolean transitionCompleted = true;
	private Point transitionStart;
	
	private double playHead = 0;
	private boolean isReversed = false;
	
	private static final double UPPER_BOUND = 45;
	private static final double LOWER_BOUND = -45;
	
	public EnemyNode(BlueprintModel enemyModel, GameWorld world) {
		this.enemyMesh = enemyModel.createSceneNode();
		this.meshCullingNode = new FrustrumCullingNode(world, 30, 0, 0);
		meshCullingNode.addChild(enemyMesh);
		this.addChild(meshCullingNode);
		
		this.body = enemyMesh.getModelPartByName("body");
		this.leftLeg = enemyMesh.getModelPartByName("leftLeg");
		this.rightLeg = enemyMesh.getModelPartByName("rightLeg");
	}
	
	public void render(RenderContext context) {
		this.meshCullingNode.setDrawCenter(this.getLocation());
		if(!transitionCompleted) {
			updateTransition();
			updateAnimation();
		}
	}
	
	private void updateAnimation() {
		if(isReversed) {
			playHead -= 1;
			if(playHead < LOWER_BOUND) {
				isReversed = false;
			}
		} else {
			playHead += 1;
			if(playHead > UPPER_BOUND) {
				isReversed = true;
			}
		}
		leftLeg.setRotationX(-playHead);
		rightLeg.setRotationX(playHead);
	}

	public void pointBodyAt(double x, double y) {
		double dx = this.x - x;
		double dy = this.y - y;
		if(dx != 0) {			
			double angle = Math.atan(dy/dx);
			angle = Math.toDegrees(angle);
			angle -= 90;
			if(dx > 0) {
				angle += 180;
			}
			this.body.setRotationZ(angle);
		}
		
		
	}

	private void updateTransition() {
		double dx = (double) transitionDirection.dx * moveSpeed;
		double dy = (double) transitionDirection.dy * moveSpeed;
		this.translate(dx, dy, 0);
		this.distanceCoveredX += dx;
		this.distanceCoveredY += dy;
		
		boolean transitionCompletedX = (Math.abs(distanceCoveredX) >= Math.abs(transitionDistanceX));
		boolean transitionCompletedY = (Math.abs(distanceCoveredY) >= Math.abs(transitionDistanceY));
		if(transitionCompletedX && transitionCompletedY) {
			this.setLocation((float) (transitionStart.x + transitionDistanceX), (float) (transitionStart.y + transitionDistanceY), 0);
			this.transitionCompleted = true;
		}
		
	}
	
	public double getBodyRotation() {
		return body.getRotationZ();
	}

	public void transitionTo(int dx, int dy, Direction direction) {
		this.transitionDistanceX = dx;
		this.transitionDistanceY = dy;
		this.transitionDirection = direction;
		this.transitionCompleted = false;
		this.distanceCoveredX = 0;
		this.distanceCoveredY = 0;
		this.leftLeg.setRotationZ(direction.rotation);
		this.rightLeg.setRotationZ(direction.rotation);
		this.transitionStart = this.getLocation();
	}

	public boolean hasFinishedTransition() {
		return transitionCompleted;
	}
}
