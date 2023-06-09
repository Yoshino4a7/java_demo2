package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void getTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> Ad1 = new LinkedListDeque<Integer>();

        Ad1.addLast(1);
        Ad1.addLast(2);
        Ad1.addLast(3);
        Ad1.addLast(4);
        Ad1.addLast(5);
        Ad1.addLast(6);
        Ad1.addLast(7);
        Ad1.addLast(8);
        Ad1.addLast(9);
        Ad1.addLast(10);
        Ad1.addLast(11);
        Ad1.addFirst(12);

        int str1=Ad1.get(0);
        int str2=Ad1.get(11);

        System.out.println("Printing out deque: ");
        Ad1.printDeque();
        assertEquals(12,str1);
        assertEquals(11,str2);



    }

    @Test
    public void equalsTest(){
        LinkedListDeque<Integer> Ld1 = new LinkedListDeque<Integer>();

        Ld1.addLast(1);
        Ld1.addLast(2);
        Ld1.addLast(3);
        Ld1.addLast(4);
        Ld1.addLast(5);
        Ld1.addLast(6);
        Ld1.addLast(7);
        Ld1.addLast(8);
        Ld1.addLast(9);
        Ld1.addLast(10);
        Ld1.addLast(11);
        Ld1.addLast(12);

        ArrayDeque<Integer> Ad1 = new ArrayDeque<Integer>();

        Ad1.addLast(1);
        Ad1.addLast(2);
        Ad1.addLast(3);
        Ad1.addLast(4);
        Ad1.addLast(5);
        Ad1.addLast(6);
        Ad1.addLast(7);
        Ad1.addLast(8);

        Ad1.addLast(10);
        Ad1.addLast(11);
        Ad1.addLast(12);


        System.out.println("Printing out deque: ");
        Ad1.printDeque();
        System.out.println("Printing out deque: ");
        Ld1.printDeque();
        boolean b=Ad1.equals(Ld1);
        assertFalse("这两个列表不应该相等",b);



        LinkedListDeque<String> Ld2 = new LinkedListDeque<String>();
        ArrayDeque<String> Ad2 = new  ArrayDeque<String>();

        Ld2.addLast("abc");
        Ld2.addLast("def");
        Ld2.addLast("ghi");
        Ld2.addLast("jkl");
        Ld2.addLast("mno");
        Ld2.addFirst("rst");
        Ld2.addFirst("rs1");
        Ld2.addFirst("rs2");
        Ld2.addFirst("rs3");
        Ld2.removeFirst();
        Ld2.removeFirst();
        Ld2.removeFirst();
        Ld2.removeLast();

        Ad2.addLast("abc");
        Ad2.addLast("def");
        Ad2.addLast("ghi");
        Ad2.addLast("jkl");
        Ad2.addLast("mno");
        Ad2.addFirst("rst");
        Ad2.addFirst("rs1");
        Ad2.addFirst("rs2");
        Ad2.addFirst("rs3");
        Ad2.removeFirst();
        Ad2.removeFirst();
        Ad2.removeFirst();
        Ad2.removeLast();
        boolean r=Ld2.equals(Ad2);
        assertTrue(r);


    }
    @Test
    public void equalsBigTest(){

        LinkedListDeque<Integer> Ld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 10000; i++) {
            Ld1.addFirst(i);
        }
        for (int i = 0; i < 10; i++) {
            Ld1.removeFirst();
        }
        for (int i = 0; i < 10; i++) {
            Ld1.removeLast();
        }


        ArrayDeque<Integer> Ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 10000; i++) {
            Ad1.addFirst(i);
        }
        for (int i = 0; i < 10; i++) {
            Ad1.removeFirst();
        }
        for (int i = 0; i < 10; i++) {
            Ad1.removeLast();
        }

        boolean b=Ad1.equals(Ld1);
        boolean c=Ld1.equals(Ad1);

        assertEquals(true,c);
        assertEquals(true,b);

        for (int i = 0; i < 9900; i++) {
            int d=Ad1.get(i);
            int e=Ld1.get(i);
            boolean h=(d==e);
            assertEquals(true,h);
        }


    }
    @Test
    public void getRecurseTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> Ad1 = new LinkedListDeque<Integer>();

        Ad1.addLast(1);
        Ad1.addLast(2);
        Ad1.addLast(3);
        Ad1.addLast(4);
        Ad1.addLast(5);
        Ad1.addLast(6);
        Ad1.addLast(7);
        Ad1.addLast(8);
        Ad1.addLast(9);
        Ad1.addLast(10);
        Ad1.addLast(11);
        Ad1.addLast(12);

        Ad1.addLast(13);
        Ad1.addLast(14);
        Ad1.addLast(15);
        Ad1.addLast(16);
        Ad1.addLast(17);
        Ad1.addLast(18);
        Ad1.addLast(19);

        int str1=Ad1.getRecursive(17);
        int str2=Ad1.getRecursive(13);


        assertEquals(18,str1);
        assertEquals(14,str2);



    }
    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {


        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
        System.out.print(s+" "+d+" "+b);
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }


    }
}


