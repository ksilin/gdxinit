package info.silin.gdxinit.graph;

public class GraphEdge {
	
	//find better expression for naming
	private int indexFromNode;
	private int indexToNode;
	private int costOfEdge;
	
	public GraphEdge(int indexFromNode, int indexToNode) {
		this.indexFromNode = indexFromNode;
		this.indexToNode = indexToNode;
		this.costOfEdge = 1;
	}
	
	public GraphEdge(int indexFromNode, int indexToNode, int costOfEdge) {
		this.indexFromNode = indexFromNode;
		this.indexToNode = indexToNode;
		this.costOfEdge = costOfEdge;
	}
	
	public int getIndexToNode() {
		return indexToNode;
	}
	
	public int getIndexFromNode() {
		return indexFromNode;
	}
	
	
}
