package info.silin.gdxinit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import info.silin.gdxinit.graph.GraphEdge;
import info.silin.gdxinit.graph.GraphNode;
import info.silin.gdxinit.graph.GraphSearch;
import info.silin.gdxinit.graph.NavGraphNode;
import info.silin.gdxinit.graph.SparseGraph;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {

	SparseGraph pathFinding = new SparseGraph(true);

	@Before
	public void before() {
		pathFinding = new SparseGraph(true);
		pathFinding.addNode(new NavGraphNode<Integer>(0));
		pathFinding.addNode(new NavGraphNode<Integer>(1));
		pathFinding.addNode(new NavGraphNode<Integer>(2));
		pathFinding.addNode(new NavGraphNode<Integer>(3));
		pathFinding.addNode(new NavGraphNode<Integer>(4));
		pathFinding.addNode(new NavGraphNode<Integer>(5));
		pathFinding.addNode(new NavGraphNode<Integer>(6));
		pathFinding.addNode(new NavGraphNode<Integer>(7));

		pathFinding.addEdge(pathFinding.getNode(0), pathFinding.getNode(1));
		pathFinding.addEdge(pathFinding.getNode(1), pathFinding.getNode(6));
		pathFinding.addEdge(pathFinding.getNode(6), pathFinding.getNode(7));
		pathFinding.addEdge(pathFinding.getNode(7), pathFinding.getNode(5));
		pathFinding.addEdge(pathFinding.getNode(1), pathFinding.getNode(5));
		pathFinding.addEdge(pathFinding.getNode(1), pathFinding.getNode(2));
		pathFinding.addEdge(pathFinding.getNode(2), pathFinding.getNode(3));
		pathFinding.addEdge(pathFinding.getNode(2), pathFinding.getNode(4));

	}

	@Test
	public void createGraph() {
		int sourceIndex = 3;
		int targetIndex = 7;
		List<GraphNode> path = GraphSearch.searchDFS(pathFinding,
				pathFinding.getNode(sourceIndex),
				pathFinding.getNode(targetIndex));
		assertNotNull(path);
		System.out.println("Path from " + sourceIndex + " to " + targetIndex);
		for (GraphNode i : path) {
			System.out.println("" + i.getIndex());
		}

	}

	@Test
	public void shouldReturnConneectedNodes() {

		List<GraphEdge> connectedNodes = pathFinding
				.getAllOutgoingEdges(pathFinding.getNode(0));
		assertNotNull(connectedNodes);
		assertFalse(connectedNodes.isEmpty());
	}

	@Test
	public void genericNode() {

		// add values to NavGraphNodes
		Integer con = 1;
		for (GraphNode node : pathFinding.getNodes()) {
			((NavGraphNode<Integer>) node).setValue(con);
		}
		for (GraphNode node : pathFinding.getNodes()) {
			assertEquals(con, ((NavGraphNode<Integer>) node).getValue());
		}

	}

}
