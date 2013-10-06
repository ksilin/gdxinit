package info.silin.gdxinit.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class GraphSearch {

	private static Map<GraphNode, GraphNode> route = new HashMap<GraphNode, GraphNode>();

	private static Map<GraphNode, Boolean> visited = new HashMap<GraphNode, Boolean>();

	static public List<GraphNode> searchDFS(SparseGraph graph,
			GraphNode source, GraphNode target) {

		resetVisited(graph);

		Stack<GraphEdge> edges = new Stack<GraphEdge>();
		GraphEdge dummy = new GraphEdge(source, source, 0);
		GraphEdge next;

		edges.push(dummy);
		while (!edges.empty()) {
			next = edges.pop();
			route.put(next.getToNode(), next.getFromNode());
			visited.put(next.getToNode(), Boolean.TRUE);

			if (next.getToNode() == target) {
				return getPath(source, target);
			}

			GraphNode actualNode = next.getToNode();

			List<GraphEdge> list = graph.getAllOutgoingEdges(actualNode);
			for (GraphEdge e : list) {
				Boolean visitedNode = visited.get(e.getToNode());
				if (visitedNode != null && !visitedNode) {
					edges.push(e);
				}
			}
		}

		return null;
	}

	private static void resetVisited(SparseGraph graph) {
		visited = new HashMap<GraphNode, Boolean>();
		for (GraphNode node : graph.getNodes()) {
			visited.put(node, Boolean.FALSE);
		}
	}

	static List<GraphNode> getPath(GraphNode source, GraphNode target) {
		List<GraphNode> path = new ArrayList<GraphNode>();
		GraphNode node = target;

		path.add(target);

		while (node != source) {
			node = route.get(node);
			path.add(node);
		}
		return path;
	}

}
