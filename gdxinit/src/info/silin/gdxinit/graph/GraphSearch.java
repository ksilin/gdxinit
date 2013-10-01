package info.silin.gdxinit.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class GraphSearch {

	private static Vector<Boolean> nodesVisited;
	private static Vector<Integer> route;

	private static int sourceNode;
	private static int targetNode;

	static public List<Integer> searchDFS(SparseGraph graph, int source, int target) {

		sourceNode = source;
		targetNode = target;

		Stack<GraphEdge> stack = new Stack<GraphEdge>();
		GraphEdge dummy = new GraphEdge(source, source, 0);
		GraphEdge next;
		route = new Vector<Integer>();
		nodesVisited = new Vector<Boolean>();

		for (int i = 0; i < graph.getAmountsOfNodes(); i++) {
			route.add(-1);
		}

		for (int i = 0; i < graph.getAmountsOfNodes(); i++) {
			nodesVisited.add(false);
		}

		stack.push(dummy);
		while (!stack.empty()) {
			next = stack.pop();
			route.set(next.getIndexToNode(), next.getIndexFromNode());
			nodesVisited.set(next.getIndexToNode(), true);

			if (next.getIndexToNode() == targetNode) {
				return getPath();
			}

			int indexOfNextInActualNode = next.getIndexToNode();

			List<GraphEdge> list = graph.getAllEdgesWithSameFrom(indexOfNextInActualNode);
			for (GraphEdge e : list) {
				if (!nodesVisited.get(e.getIndexToNode())) {
					stack.push(e);
				}
			}
		}

		return null;
	}

	static List<Integer> getPath() {

		List<Integer> path = new ArrayList<Integer>();
		int node = targetNode;

		path.add(targetNode);

		while (node != sourceNode) {
			node = route.get(node);
			path.add(node);
		}
		return path;

	}

}
