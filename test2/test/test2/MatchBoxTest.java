package test2;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;


class MatchBoxTest {

	private static final Test2Factory factory=Test2Factory.eINSTANCE;
	@Test
	void matchPartitionPositive() {
		Test2Factory factory=Test2Factory.eINSTANCE;
		Box b= factory.createBox();
		Partition p = factory.createPartition();
		p.setBox(b);
		//Preconditions
		assertNull(p.getBox());
		assertNull(b.getPartition());
		b.matchABox();
		//Postconditions
		assertNotNull(b.getPartition());
		assertNotNull(p.getBox());
	}
	
	@Test
	void matchPartitionNegative() throws RuntimeException {
		
		Box b= factory.createBox();
		try {
			b.matchABox();
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
			assert(true);
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
