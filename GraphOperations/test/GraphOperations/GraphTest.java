package GraphOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphTest {

	private Graph graph;

	@BeforeEach
	void setUp() {
		GraphOperationsFactory factory = GraphOperationsFactory.eINSTANCE;
		this.graph = factory.createGraph();
	}

	@Test
	void test_createEdge() throws Exception {
		this.graph.addEdgeWithIncidentNodes("n1", "n2", "e12");
		assertNodeCount(2);
		assertEdgeCount(1);
		assertOutdegree("n1", 1);
		assertIndegree("n2", 1);
		this.graph.addEdgeWithIncidentNodes("n1", "n3", "e13");
		assertNodeCount(3);
		assertEdgeCount(2);
		assertOutdegree("n1", 2);
		assertIndegree("n3", 1);
		this.graph.addEdgeWithIncidentNodes("n2", "n4", "e24");
		assertNodeCount(4);
		assertEdgeCount(3);
		assertOutdegree("n2", 1);
		assertIndegree("n4", 1);
		this.graph.addEdgeWithIncidentNodes("n3", "n4", "e34");
		assertNodeCount(4);
		assertEdgeCount(4);
	}

	@Test
	void test_removeEdge() throws Exception {
		this.graph.addEdgeWithIncidentNodes("n1", "n2", "e12");
		this.graph.removeEdge("e12");
		assertEdgeCount(0);

		this.graph.removeEdge("e12");
	}

	@Test
	void test_clear() throws Exception {
		this.graph.addEdgeWithIncidentNodes("n1", "n2", "e12");
		this.graph.addEdgeWithIncidentNodes("n1", "n3", "e13");
		this.graph.addEdgeWithIncidentNodes("n2", "n4", "e24");
		this.graph.addEdgeWithIncidentNodes("n3", "n4", "e34");

		this.graph.clear();
		assertNodeCount(0);
		assertEdgeCount(0);
	}

	private void assertNodeCount(final int expectedNodeCount) {
		assertEquals(expectedNodeCount, graph.getNodes().size());
	}

	private void assertEdgeCount(final int expectedEdgeCount) {
		assertEquals(expectedEdgeCount, graph.getEdges().size());
	}

	private void assertOutdegree(final String nodeId, final int expectedDegree) {
		assertEquals(expectedDegree, getNodeWithId(nodeId).getOutgoingEdges().size());
	}

	private void assertIndegree(final String nodeId, final int expectedDegree) {
		assertEquals(expectedDegree, getNodeWithId(nodeId).getIncomingEdges().size());
	}

	private Node getNodeWithId(final String nodeId) {
		Optional<Node> optionalNode = graph.getNodes().stream().filter(node -> nodeId.equals(node.getId())).findAny();
		if (!optionalNode.isPresent()) {
			fail("No node with ID " + nodeId);
		}
		Node node = optionalNode.get();
		return node;
	}
}
