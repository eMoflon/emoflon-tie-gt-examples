package test2;

import org.junit.jupiter.api.Test;


class MatchBoxTest {

	@Test
	void test() {
		Test2Factory factory=Test2Factory.eINSTANCE;
		Box b= factory.createBox();
		Partition p = factory.createPartition();
		p.setBox(b);
		b.matchABox();
	}
	
	@Test
	void testNegative() throws RuntimeException {
		Test2Factory factory=Test2Factory.eINSTANCE;
		Box b= factory.createBox();
		Partition p = factory.createPartition();
		try {
			b.matchABox();
		}
		catch(RuntimeException e) {
			System.out.println("Caught RuntimeException");
			assert(true);
			return;
		}
		assert(false);
	}

}
