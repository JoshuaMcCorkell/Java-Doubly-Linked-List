import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import dllist.DLinkedList;

public class Tests {

    DLinkedList<Integer> a;

    @Test
    public void equals() {
        a = new DLinkedList<>();
        DLinkedList<Integer> aa = new DLinkedList<>();
        assertEquals(a, aa);
        a.add(1); aa.add(1);
        a.add(100); aa.add(100);
        assertEquals(a, aa);
        a.add(5);
        assertNotEquals(a, aa);
    }

    @Test
    public void addGet() {
        a = new DLinkedList<>();
        var el = new int[] {1, 5, 7, 3, 9, 14, 1000};
        for (int element: el) {
            a.add(element);
        }
        for (int i = 0; i < el.length; i++) {
            assertEquals(Integer.valueOf(el[i]), a.get(i));
        }
    }

    @Test
    public void addAll() {
        a = new DLinkedList<>();
        a.addAll(new Integer[] {1, 5, 7});
        LinkedList<Integer> aa = new LinkedList<>();
        aa.add(1); aa.add(100); aa.add(500);
        a.addAll(new LinkedList<>(aa));
        assertEquals(Integer.valueOf(500), a.get(5));
        assertEquals(Integer.valueOf(5), a.get(1));
    }

    @Test
    public void length() {
        a = new DLinkedList<>();
        a.addAll(new Integer[] {1, 2, 3, 5});
        a.add(3, 4);
        a.add(6);
        assertEquals(6, a.size());
    }

    @Test
    public void insert() {
        a = new DLinkedList<>();
        a.addAll(new Integer[] {1, 3, 4, 6});
        a.add(3, 5);
        a.add(1, 2);
        for (int i = 0; i < 5; i++) {
            assertEquals(Integer.valueOf(i + 1), a.get(i));
        }
    }

    @Test 
    public void emptyListOperations() {
        a = new DLinkedList<>();
        a.add(1);
        assertEquals(Integer.valueOf(1), a.get(0));
        a = new DLinkedList<>();
        a.add(0, 1);
        assertEquals(Integer.valueOf(1), a.get(0));
        try {
            a.add(5, 2);
        } catch (IndexOutOfBoundsException ignore) {}
        a.add(1, 2);
        assertEquals(Integer.valueOf(2), a.get(1));
        assertEquals(Integer.valueOf(1), a.get(0));
    }
}
