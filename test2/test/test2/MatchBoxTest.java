package test2;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;


class MatchBoxTest {

	private static final Test2Factory factory=Test2Factory.eINSTANCE;
	@Test
	void matchPartitionPositive() {
		Test2Factory factory=Test2Factory.eINSTANCE;
		Box b= factory.createBox();
		b.setName("testbox");
		b.setBoxWidthcm(1.1);
		b.setIsAssignable(true);
		b.setThisBoxId(1);
		Partition p = factory.createPartition();
		p.setBox(b);
		b.setBoxSize(0);
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
		b.setBoxSize(1);
		b.setName("testbox");
		Partition p = factory.createPartition();
		p.setBox(b);
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
		b.setBoxSize(0);
		b.setThisBoxId(1);
		b.setName("this is not the box that you are looking for");
		Partition p = factory.createPartition();
		p.setBox(b);
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
		b.setThisBoxId(1);
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
		p.setBox(b);
		//Preconditions
		assertNotNull(b.getPartition());
		assertNotNull(p.getBox());
		b.removeAPartition();
		//Postconditions
		assertNull(p.getBox());
		assertNull(b.getPartition());
	}
	
	@Test
	void removePartitionNegative() {
		Box b= factory.createBox();
		Partition p = factory.createPartition();
		//Preconditions
		assertNull(b.getPartition());
		assertNull(p.getBox());
		try {
			b.removeAPartition();
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
		b.setName("testbox");
		b.setBoxWidthcm(1.1f);
		b.setIsAssignable(true);
		b.setThisBoxId(1);
		Partition p = factory.createPartition();
		p.setBox(b);
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

}
