package info.silin.gdxinit.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class GraphSearch {

	private static HashMap<GraphNode, GraphNode> route = new HashMap<GraphNode, GraphNode>();

	private static GraphNode sourceNode;
	private static GraphNode targetNode;

	static public List<GraphNode> searchDFS(SparseGraph graph, GraphNode source, GraphNode target) {

		sourceNode = source;
		targetNode = target;

		Stack<GraphEdge> edges = new Stack<GraphEdge>();
		GraphEdge dummy = new GraphEdge(sourceNode, sourceNode, 0);
		GraphEdge next;
		
		edges.push(dummy);
		while (!edges.empty()) {
			next = edges.pop();
			route.put(next.getToNode(), next.getFromNode());
			graph.getNode(next.getToNode()).setNodeVisited(true);

			if (next.getToNode() == targetNode) {
				return getPath();
			}

			GraphNode actualNode = next.getToNode();

			List<GraphEdge> list = graph.getAllEdgesWithSameFrom(actualNode);
			for (GraphEdge e : list) {
				if (!graph.getNode(e.getToNode()).nodeVisited()) {
					edges.push(e);
				}
			}
		}

		return null;
	}

	static List<GraphNode> getPath() {
		List<GraphNode> path = new ArrayList<GraphNode>();
		GraphNode node = targetNode;

		path.add(targetNode);

		while (node != sourceNode) {
			node = route.get(node);
			path.add(node);
		}
		return path;

	}

}
