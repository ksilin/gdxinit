package info.silin.gdxinit.graph;

public class GraphNode {
	
	private int nodeIndex;
	private boolean nodeVisited = false;
	
	public GraphNode() {
		
	}
	
	public GraphNode(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}
	
	public void setIndex(int newNodeIndex) {
		this.nodeIndex = newNodeIndex;
	}
	
	public int getNodeIndex() {
		return nodeIndex;
	}
	
	public boolean nodeVisited() {
		return nodeVisited;
	}
	
	public void setNodeVisited(boolean nodeVisited) {
		this.nodeVisited = nodeVisited;
	}

}
