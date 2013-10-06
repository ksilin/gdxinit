package info.silin.gdxinit.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SparseGraph {

	private List<GraphNode> nodes = new ArrayList<GraphNode>();
	private Map<GraphNode, List<GraphEdge>> edgeMap = new HashMap<GraphNode, List<GraphEdge>>();
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
			if (node.getIndex() == nodeIndex) {
				return node;
			}
		}
		return null;
	}

	public GraphEdge getEdge(GraphNode from, GraphNode to) {
		for (GraphEdge edge : edgeMap.get(from)) {
			return (edge.getFromNode() == to) ? edge : null;
		}
		return null;
	}

	public List<GraphEdge> getAllOutgoingEdges(GraphNode fromNode) {
		List<GraphEdge> list = new ArrayList<GraphEdge>();
		list = edgeMap.get(fromNode);
		return list;
	}

	boolean isDiGraph() {
		return isDiGraph;
	}

	public List<GraphNode> getNodes() {
		return nodes;
	}
}
