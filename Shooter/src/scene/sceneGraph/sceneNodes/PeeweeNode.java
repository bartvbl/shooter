package scene.sceneGraph.sceneNodes;

import geom.Point;
import geom.mesh.Mesh3D;
import geom.mesh.ModelPart;
import orre.resources.loaders.obj.ModelLoader;
import orre.resources.twoStageLoadables.BlueprintModel;
import render.RenderContext;
import shooter.GameWorld;
import shooter.map.Direction;

import static org.lwjgl.opengl.GL11.*;


public class PeeweeNode extends EmptyCoordinateNode {
	private static final BlueprintModel peeweeModel = ModelLoader.loadModel("res/mesh/peewee.mdl", "peewee");
	private static final double moveSpeed = 0.006;
	
	private Mesh3D peeweeMesh;
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
	
	public PeeweeNode(GameWorld world) {
		this.peeweeMesh = peeweeModel.createSceneNode();
		this.meshCullingNode = new FrustrumCullingNode(world, 30, 0, 0);
		meshCullingNode.addChild(peeweeMesh);
		this.addChild(meshCullingNode);
		
		this.body = peeweeMesh.getModelPartByName("body");
		this.leftLeg = peeweeMesh.getModelPartByName("leftLeg");
		this.rightLeg = peeweeMesh.getModelPartByName("rightLeg");
	}
	
	public void render(RenderContext context) {
		this.meshCullingNode.setDrawCenter(this.getLocation());
		if(!transitionCompleted) {
			updateTransition();
		}
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
		this.transitionStart = this.getLocation();
	}

	public boolean hasFinishedTransition() {
		return transitionCompleted;
	}
}
