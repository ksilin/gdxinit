package info.silin.gdxinit.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SparseGraph {

	private List<GraphNode> nodes = new ArrayList<GraphNode>();
	private HashMap<Integer, List<GraphEdge>> edgeMap = new HashMap<Integer, List<GraphEdge>>();
	private boolean isDiGraph;
	private int nextFreeNOdeIndex = 0;

	public SparseGraph(boolean isDiGraph) {
		this.isDiGraph = isDiGraph;
	}

	public int addNode(GraphNode nodeToAdd) {
		nodes.add(nodeToAdd);
		nodeToAdd.setIndex(nodes.size() - 1);
		return nodes.size() - 1;
	}

	public void removeNode() {

	}

	public void addEdge(int first, int second) {
		addEdge(new GraphEdge(first, second));
		addEdge(new GraphEdge(second, first));
	}

	public void addEdge(GraphEdge edge) {

		if (edgeMap.get(edge.getIndexFromNode()) == null) {
			List<GraphEdge> list = new ArrayList<GraphEdge>();
			list.add(edge);
			edgeMap.put(edge.getIndexFromNode(), list);
		} else {
			edgeMap.get(edge.getIndexFromNode()).add(edge);
		}
	}

	public void removeEdge(int fromNodeIndex, int toNodeIndex) {
		for (GraphEdge edgeList : edgeMap.get(fromNodeIndex)) {
			// (edge.getIndexToNode() == toNodeIndex) ? edge: null;
		}
	}

	public GraphNode getNode(int nodeIndex) {
		return nodes.get(nodeIndex);
	}

	public GraphEdge getEdge(int fromNodeIndex, int toNodeIndex) {
		for (GraphEdge edge : edgeMap.get(fromNodeIndex)) {
			return (edge.getIndexToNode() == toNodeIndex) ? edge : null;
		}

		return null;
	}

	public List<GraphEdge> getAllEdgesWithSameFrom(int fromNode) {
		List<GraphEdge> edgeList = new ArrayList();
		List<GraphEdge> list = edgeMap.get(fromNode);
		if (isDiGraph) {
			for (GraphEdge edge : list) {

				// if (edge.getIndexToNode() == fromNode) {
				edgeList.add(edge);
				// }

			}

		} else {

			for (GraphEdge edge : list) {

				if (edge.getIndexToNode() == fromNode) {
					edgeList.add(edge);
				}

			}
		}

		return edgeList;
	}

	public int getNextFreeNodeIndex() {
		return nextFreeNOdeIndex++;
	}

	boolean isDiGraph() {
		return this.isDiGraph;
	}

	public int getAmountsOfNodes() {
		return nodes.size();
	}

}
