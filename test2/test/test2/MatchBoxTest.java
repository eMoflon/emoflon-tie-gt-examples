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
		Partition p = factory.createPartition();
		p.setBox(b);
		b.setBoxSize(0);
		//Preconditions
		assertNotNull(p.getBox());
		assertNotNull(b.getPartition());
		assertTrue(b.getBoxSize()==0);
		b.matchAPartition();
		//Postconditions
		assertNotNull(b.getPartition());
		assertNotNull(p.getBox());
		assertTrue(b.getBoxSize()==0);
	}
	
	@Test
	void matchPartitionNegativeAttribute() throws RuntimeException {
		
		Box b= factory.createBox();
		b.setBoxSize(1);
		Partition p = factory.createPartition();
		p.setBox(b);
		try {
			b.matchAPartition();
		}
		catch(RuntimeException e) {
			assert(true);
			return;
		}
		assert(false);
	}
	
	@Test
	void matchPartitionNegativeNoPartition() throws RuntimeException {
		
		Box b= factory.createBox();
		try {
			b.matchAPartition();
		}
		catch(RuntimeException e) {
			assert(true);
			return;
		}
		assert(false);
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
		assert(false);
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
			assert(true);
			return;
		}
		assert(false);
	}

}
