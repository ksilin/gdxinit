package info.silin.gdxinit.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SparseGraph {

	private List<GraphNode> nodes = new ArrayList<GraphNode>();
	private HashMap<GraphNode, List<GraphEdge>> edgeMap = new HashMap<GraphNode, List<GraphEdge>>();
	private boolean isDiGraph;

	public SparseGraph(boolean isDiGraph) {
		this.isDiGraph = isDiGraph;
	}

	public void addNode(GraphNode nodeToAdd) {
		nodes.add(nodeToAdd);
	}

	public void removeNode(GraphNode nodeToRemove) {
		nodes.remove(nodeToRemove);
	}

	public void addEdge(GraphNode first, GraphNode second) {
		addEdge(new GraphEdge(first, second));
		addEdge(new GraphEdge(second, first));
	}

	public void addEdge(GraphEdge edge) {

		if (edgeMap.get(edge.getFromNode()) == null) {
			List<GraphEdge> list = new ArrayList<GraphEdge>();
			list.add(edge);
			edgeMap.put(edge.getFromNode(), list);
		} else {
			edgeMap.get(edge.getFromNode()).add(edge);
		}

	}

	public void removeEdge(GraphEdge edge) {
		edgeMap.remove(edge);
	}

	public GraphNode getNode(int nodeIndex) {

		for (GraphNode node : nodes) {
			if (node.getNodeIndex() == nodeIndex) {
				return node;
			}

		}
		return null;
	}

	public GraphNode getNode(GraphNode node) {
		for (GraphNode actNode : nodes) {
			if (actNode == node)
				return actNode;
		}

		return null;
	}

	public GraphEdge getEdge(GraphNode fromNode, GraphNode toNode) {
		for (GraphEdge edge : edgeMap.get(fromNode)) {
			return (edge.getFromNode() == toNode) ? edge : null;
		}

		return null;
	}

	public List<GraphEdge> getAllEdgesWithSameFrom(GraphNode fromNode) {
		List<GraphEdge> list = new ArrayList<GraphEdge>();
		list = edgeMap.get(fromNode);
		return list;
	}

	boolean isDiGraph() {
		return this.isDiGraph;
	}

	public int getAmountsOfNodes() {
		return nodes.size();
	}

	public List<GraphNode> getNodes() {
		return nodes;
	}

}
