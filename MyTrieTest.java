import static org.junit.Assert.*;

import org.junit.Test;


public class MyTrieTest {

	@Test
	public void testSize() {
		MyTrie test = new MyTrie();
		assertTrue(test.size()==0);
	}

	@Test
	public void testMyTrie() {
		MyTrie test = new MyTrie();
	}

	@Test
	public void testAddString() {
		MyTrie test = new MyTrie();
		test.add("");
		test.add("hella");
		test.add("bolivia");
		test.add("hello");
		test.add("hell");
		test.add("apples");
		assertFalse(test.contains("hl"));
		test.contains("hell");
		test.containsPrefix("helld");
		assertTrue(test.containsEmptyString());
		assertFalse(test.isEmpty());
	}

	@Test
	public void testToString() {
		MyTrie test = new MyTrie();
		test.add("hella");
		test.add("bolivia");
		test.add("hello");
		test.add("hell");
		test.add("hell");
		assertTrue(test.size()==4);
		test.add("apples");
		test.add("jog");
		test.add("cat");
		assertTrue(test.containsPrefix("h"));
		System.out.print(test.toString());
	}

//	@Test
	public void testIterator() {
		fail("Not yet implemented");
	}

}
