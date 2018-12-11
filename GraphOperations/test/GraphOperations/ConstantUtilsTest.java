package GraphOperations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConstantUtilsTest {

	private ConstantUtils utils;

	@BeforeEach
	void setUp() {
		GraphOperationsFactory factory = GraphOperationsFactory.eINSTANCE;
		this.utils = factory.createConstantUtils();
	}

	@Test
	void test_getLong() throws Exception {
		assertEquals(-42, utils.getLong());
	}

	@Test
	void test_getInt() throws Exception {
		assertEquals(-42, utils.getInt());
	}

	@Test
	void test_getFloat() throws Exception {
		assertEquals(-42.0f, utils.getFloat());
	}

	@Test
	void testj_getDouble() throws Exception {
		assertEquals(-42.0, utils.getDouble());
	}

	@Test
	void test_getString() throws Exception {
		assertEquals("-42", utils.getString());
	}
}
