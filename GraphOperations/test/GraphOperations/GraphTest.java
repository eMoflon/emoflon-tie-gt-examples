package GraphOperations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GraphOperations.Graph;
import GraphOperations.GraphOperationsFactory;

public class GraphTest {

	private Graph graph;

	@BeforeEach
	void setUp() {
		GraphOperationsFactory factory = GraphOperationsFactory.eINSTANCE;
		this.graph = factory.createGraph();
	}

	@Test
	void test_CreateEdge() throws Exception {
		this.graph.addEdgeWithIncidentNodes("n1", "n2", "e12");
	}
}
