package GraphOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EIntContainerTest {
	private EIntContainer container;

	@BeforeEach
	void setUp() {
		GraphOperationsFactory factory = GraphOperationsFactory.eINSTANCE;
		this.container = factory.createEIntContainer();
	}

	@Test
	void test_increment() throws Exception {
		this.container.setValue(1);
		this.container.increment();
		assertEquals(2, this.container.getValue());
	}
}
