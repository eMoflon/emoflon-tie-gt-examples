package test2;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;


class MatchBoxTest {

	void setupPositive(Box b, Partition p) {
		b.setName("testbox");
		b.setBoxWidthcm(1.1);
		b.setIsAssignable(true);
		b.setThisBoxId(1);
		b.setType(BoxType.BIG_BOX);
		p.setBox(b);
		b.setBoxSize(0);
	}

	private static final Test2Factory factory=Test2Factory.eINSTANCE;
	@Test
	void matchPartitionPositive() {
		Test2Factory factory=Test2Factory.eINSTANCE;
		Box b= factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		//Preconditions
		assertNotNull(p.getBox());
		assertNotNull(b.getPartition());
		assertTrue(b.getBoxSize()==0);
		b.matchAPartition(1);
		//Postconditions
		assertNotNull(b.getPartition());
		assertNotNull(p.getBox());
		assertTrue(b.getBoxSize()==0);
	}

	@Test
	void matchPartitionNegativeAttribute() throws RuntimeException {

		Box b= factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		b.setBoxSize(1);
		try {
			b.matchAPartition(1);
		}
		catch(RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}
	@Test
	void matchPartitionNegativeStringAttribute() throws RuntimeException {

		Box b= factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		b.setName("this is not the box that you are looking for");
		try {
			b.matchAPartition(1);
		}
		catch(RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	void matchPartitionNegativeNoPartition() throws RuntimeException {

		Box b= factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		b.setPartition(null);
		p.setBox(null);
		p=null;
		try {
			b.matchAPartition(1);
		}
		catch(RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	void removePartitionPositive() {
		Box b= factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		//Preconditions
		assertNotNull(b.getPartition());
		assertNotNull(p.getBox());
		b.removeAPartition(1);
		//Postconditions
		assertNull(p.getBox());
		assertNull(b.getPartition());
	}

	@Test
	void removePartitionNegative() {
		Box b= factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		p.setBox(null);
		b.setPartition(null);
		//Preconditions
		assertNull(b.getPartition());
		assertNull(p.getBox());
		try {
			b.removeAPartition(0);
		}
		catch(RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	void addPartitionPositive() {
		Box b= factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		p.setBox(null);
		b.setPartition(null);
		//Preconditions
		assertNull(p.getBox());
		assertNull(b.getPartition());
		b.addAPartition();
		//Postconditions
		assertNotNull(b.getPartition());
		assertNotNull(p.getBox());
	}

	@Test
	void addPartitionNegative() {
		Box b= factory.createBox();
		//Preconditions
		assertNull(b.getPartition());
		try {
			b.addAPartition();
		}
		catch(RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	void matchPartitionNegativeParameter() throws RuntimeException {

		Box b= factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		b.setBoxSize(0);
		try {
			b.matchAPartition(0);
		}
		catch(RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

	@Test
	void matchPartitionNegativeEnum() throws RuntimeException {

		Box b= factory.createBox();
		Partition p = factory.createPartition();
		setupPositive(b, p);
		b.setType(BoxType.SMALL_BOX);
		try {
			b.matchAPartition(0);
		}
		catch(RuntimeException e) {
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}

}
