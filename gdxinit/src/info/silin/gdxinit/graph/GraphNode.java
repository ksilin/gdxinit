package info.silin.gdxinit.graph;

public class GraphNode {

	private int nodeIndex;

	public GraphNode() {
	}

	public GraphNode(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}

	public void setIndex(int newNodeIndex) {
		this.nodeIndex = newNodeIndex;
	}

	public int getIndex() {
		return nodeIndex;
	}
}
