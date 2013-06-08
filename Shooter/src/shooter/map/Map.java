package shooter.map;


import geom.Point;
import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyContainerNode;
import scene.sceneGraph.sceneNodes.MapSceneNode;
import scene.sceneGraph.sceneNodes.ShadowMappedLightNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.map.generator.MapGenerator;

public class Map extends GameObject {

	private final MapSceneNode mapNode;
	private final TileType[][] tileMap;
	private final EmptyContainerNode contentRootNode;

	public static Map createInstance(GameWorld gameWorld) {
		MapGenerator generator = new MapGenerator();
		TileType[][] tileMap = generator.generateMap(250, 250, System.currentTimeMillis());
		ShadowMappedLightNode shadowMapNode = new ShadowMappedLightNode(gameWorld.controlledNode);
		return new Map(gameWorld, new MapSceneNode(shadowMapNode), tileMap);
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
		this.mapNode.setRotationZ(rotation);
	}

	public void translate(double x, double y) {
		this.mapNode.translate(x, y, 0);
	}

	public SceneNode getMapContentRootNode() {
		return contentRootNode;
	}

	public Point getLocation() {
		return this.mapNode.getLocation();
	}

	public TileType getTileAt(int x, int y) {
		if((x < 0) || (x >= tileMap.length)) {
			return TileType.WALL;
		}
		if((y < 0) || (y >= tileMap[0].length)) {
			return TileType.WALL;
		}
		return tileMap[x][y];
	}
}
