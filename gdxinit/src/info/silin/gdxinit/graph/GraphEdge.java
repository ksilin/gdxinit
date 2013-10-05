package info.silin.gdxinit.graph;

public class GraphEdge {
	
	private GraphNode fromNode;
	private GraphNode toNode;
	private int costOfEdge;
	
	public GraphEdge(GraphNode fromNode, GraphNode toNode) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.costOfEdge = 1;
	}
	
	public GraphEdge(GraphNode fromNode, GraphNode toNode, int costOfEdge) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.costOfEdge = costOfEdge;
	}
	
	public GraphNode getToNode() {
		return toNode;
	}
	
	public GraphNode getFromNode() {
		return fromNode;
	}
	
	public void setCostOfEdge(int costOfEdge) {
		this.costOfEdge = costOfEdge;
	}
	
	public int setCostOfEdge() {
		return costOfEdge;
	}
	
	
}
