package info.silin.gdxinit.graph;

public class GraphEdge {

	private GraphNode fromNode;
	private GraphNode toNode;
	private float cost;

	public GraphEdge(GraphNode fromNode, GraphNode toNode) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.cost = 1;
	}

	public GraphEdge(GraphNode fromNode, GraphNode toNode, float cost) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.cost = cost;
	}

	public GraphNode getToNode() {
		return toNode;
	}

	public GraphNode getFromNode() {
		return fromNode;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}
}
