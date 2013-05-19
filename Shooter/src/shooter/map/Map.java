package shooter.map;


import geom.Point;
import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyContainerNode;
import scene.sceneGraph.sceneNodes.MapSceneNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;

public class Map extends GameObject {

	private final MapSceneNode mapNode;
	private final TileType[][] tileMap;
	private final EmptyContainerNode contentRootNode;

	public static Map createInstance(GameWorld gameWorld) {
		TileType[][] tileMap = MapGenerator.generateMap(900, 900, System.currentTimeMillis());
		return new Map(gameWorld, new MapSceneNode(), tileMap);
	}

	private Map(GameWorld world, MapSceneNode mapSceneNode, TileType[][] tileMap) {
		super(GameObjectType.MAP, mapSceneNode, world);
		this.mapNode = mapSceneNode;
		this.tileMap = tileMap;
		this.contentRootNode = new EmptyContainerNode();
		mapNode.addChild(contentRootNode);
		MapBuilder.buildMap(mapNode, tileMap, world);
	}

	public void update() {
		
	}

	public void setRotation(double rotation) {
		this.mapNode.setRotation(rotation);
	}

	public void translate(double x, double y) {
		this.mapNode.translate(x, y);
	}

	public SceneNode getMapContentRootNode() {
		return contentRootNode;
	}

	public Point getLocation() {
		return this.mapNode.getLocation();
	}
}
