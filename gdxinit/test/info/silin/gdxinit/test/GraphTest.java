package info.silin.gdxinit.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import info.silin.gdxinit.graph.GraphEdge;
import info.silin.gdxinit.graph.GraphNode;
import info.silin.gdxinit.graph.GraphSearch;
import info.silin.gdxinit.graph.SparseGraph;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {

	SparseGraph pathFinding = new SparseGraph(true);

	@Before
	public void before() {
		pathFinding = new SparseGraph(true);
		pathFinding.addNode(new GraphNode(0));
		pathFinding.addNode(new GraphNode(1));
		pathFinding.addNode(new GraphNode(2));
		pathFinding.addNode(new GraphNode(3));
		pathFinding.addNode(new GraphNode(4));
		pathFinding.addNode(new GraphNode(5));
		pathFinding.addNode(new GraphNode(6));
		pathFinding.addNode(new GraphNode(7));

		pathFinding.addEdge(0, 1);
		pathFinding.addEdge(1, 6);
		pathFinding.addEdge(6, 7);
		pathFinding.addEdge(7, 5);
		pathFinding.addEdge(1, 5);
		pathFinding.addEdge(1, 2);
		pathFinding.addEdge(2, 3);
		pathFinding.addEdge(2, 4);
	}

	@Test
	// @Ignore
	public void createGraph() {

		List<Integer> path = GraphSearch.searchDFS(pathFinding, 3, 7);
		assertNotNull(path);
		for (Integer i : path) {
			System.out.println("" + i);
		}

	}

	@Test
	public void shouldReturnConneectedNodes() {

		List<GraphEdge> connectedNodes = pathFinding.getAllEdgesWithSameFrom(0);
		assertNotNull(connectedNodes);
		assertFalse(connectedNodes.isEmpty());
	}

	// getAllEdgesWithSameFrom

}
