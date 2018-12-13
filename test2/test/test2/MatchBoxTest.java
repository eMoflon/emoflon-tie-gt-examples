package test2;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class MatchBoxTest {

	private static final Test2Factory factory = Test2Factory.eINSTANCE;

	private void setupPositive(Box b, Partition p) {
		b.setName("testbox");
		b.setBoxWidthcm(1.1);
		b.setIsAssignable(true);
		b.setThisBoxId(1);
		b.setType(BoxType.BIG_BOX);
		p.setBox(b);
		b.setBoxSize(1);
	}

	@Test
	void matchPartitionPositive() {
		Test2Factory factory = Test2Factory.eINSTANCE;
		Box b = factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		// Preconditions
		assertNotNull(p.getBox());
		assertTrue(b.getPartitions().size() > 0);
		assertTrue(b.getBoxSize() != 0);
		b.matchAPartition(1);
		// Postconditions
		assertTrue(b.getPartitions().size() > 0);
		assertNotNull(p.getBox());
		assertTrue(b.getBoxSize() != 0);
	}

	@Test
	void matchPartitionNegativeAttribute() throws RuntimeException {

		Box b = factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		b.setBoxSize(0);
		try {
			b.matchAPartition(1);
		} catch (RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	void matchPartitionNegativeStringAttribute() throws RuntimeException {

		Box b = factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		b.setName("this is not the box that you are looking for");
		try {
			b.matchAPartition(1);
		} catch (RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	void matchPartitionNegativeNoPartition() throws RuntimeException {

		Box b = factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		b.getPartitions().add(p);
		p.setBox(null);
		p = null;
		try {
			b.matchAPartition(1);
		} catch (RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	void removePartitionPositive() {
		Box b = factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		// Preconditions
		assertTrue(b.getPartitions().size() > 0);
		assertNotNull(p.getBox());
		b.removeAPartition(1);
		// Postconditions
		assertNull(p.getBox());
		assertEquals(b.getPartitions().size(), 0);
	}

	@Test
	void removePartitionNegative() {
		Box b = factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		p.setBox(null);
		b.getPartitions().clear();
		// Preconditions
		assertEquals(b.getPartitions().size(), 0);
		assertNull(p.getBox());
		try {
			b.removeAPartition(0);
		} catch (RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	void addPartitionNegativeNAC() {
		Box b = factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		// Preconditions
		assertNotNull(p.getBox());
		assertTrue(b.getPartitions().size() > 0);
		try {
			b.addAPartition();
		} catch (RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	void addPartitionPositive() {
		Box b = factory.createBox();
		// Preconditions
		assertEquals(b.getPartitions().size(), 0);
		b.addAPartition();
		// Postconditions
		assertTrue(b.getPartitions().size() > 0);
	}

	@Test
	void matchPartitionNegativeParameter() throws RuntimeException {

		Box b = factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		b.setBoxSize(0);
		try {
			b.matchAPartition(0);
		} catch (RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	void matchPartitionNegativeEnum() throws RuntimeException {

		Box b = factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		b.setType(BoxType.SMALL_BOX);
		try {
			b.matchAPartition(0);
		} catch (RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	void testInsertPartition() throws Exception {
		Box b = factory.createBox();
		b.addAPartition();
		Partition predecessorPartition = b.getPartitions().get(0);
		b.insertPartition(predecessorPartition);
		assertEquals(b.getPartitions().size(), 2);
	}

	@Test
	void testInsertPartitionOtherBox() throws Exception {
		Box b = factory.createBox();
		Box b2 = factory.createBox();

		b.addAPartition();
		Partition predecessorPartition = b.getPartitions().get(0);

		assertThrows(RuntimeException.class, () -> b2.insertPartition(predecessorPartition));

		assertEquals(b.getPartitions().size(), 1);
		assertEquals(b2.getPartitions().size(), 0);
	}
}
