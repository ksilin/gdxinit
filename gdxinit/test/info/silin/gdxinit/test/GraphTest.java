package info.silin.gdxinit.test;

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
		pathFinding.addNode(new GraphNode(0));
		pathFinding.addNode(new GraphNode(1));
		pathFinding.addNode(new GraphNode(2));
		pathFinding.addNode(new GraphNode(3));
		pathFinding.addNode(new GraphNode(4));
		pathFinding.addNode(new GraphNode(5));
		pathFinding.addNode(new GraphNode(6));
		pathFinding.addNode(new GraphNode(7));

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
		List<GraphNode> path = GraphSearch.searchDFS(pathFinding, pathFinding.getNode(3), pathFinding.getNode(7));
		assertNotNull(path);
		System.out.println("Path from source to destination");
		for (GraphNode i : path) {
			System.out.println("" + i.getNodeIndex());
		}

	}

	@Test
	public void shouldReturnConneectedNodes() {

		List<GraphEdge> connectedNodes = pathFinding.getAllEdgesWithSameFrom(pathFinding.getNode(0));
		assertNotNull(connectedNodes);
		assertFalse(connectedNodes.isEmpty());
	}
	

	@Test
	public void genericNode() {
		
		//add values to NavGraphNodes
		
		for (GraphNode iterable_element : pathFinding.getNodes()) {
			Integer[] con = {1,2,3};
			((NavGraphNode<Integer>)iterable_element).setCoordinates(con);
		}
		
		List<GraphNode> path = GraphSearch.searchDFS(pathFinding, pathFinding.getNode(3), pathFinding.getNode(7));
		assertNotNull(path);
		System.out.println("Path from source to destination");
		for (GraphNode i : path) {
			System.out.println("" + i.getNodeIndex());
		}

	}
	

}
