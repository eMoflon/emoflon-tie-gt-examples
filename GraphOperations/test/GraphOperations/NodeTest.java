package GraphOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NodeTest {

	private Node node;

	@BeforeEach
	void setUp() {
		final GraphOperationsFactory factory = GraphOperationsFactory.eINSTANCE;
		node = factory.createNode();
	}

	@Test
	void testAssignNewId() throws Exception {
		node.assignIdCAC();
		assertEquals("newId", node.getId());
	}
}
