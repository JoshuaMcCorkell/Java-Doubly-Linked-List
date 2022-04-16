import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.LinkedList;

import org.junit.Test;

import dllist.DLinkedList;

public class Tests {
    @Test
    public void equals() {
        DLinkedList<Integer> a = new DLinkedList<>();
        DLinkedList<Integer> b = new DLinkedList<>();
        assertEquals(a, b);
        a.add(1); b.add(1);
        a.add(100); b.add(100);
        assertEquals(a, b);
        a.add(5);
        assertNotEquals(a, b);
    }

    @Test
    public void addGet() {
        DLinkedList<Integer> a = new DLinkedList<>();
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
        DLinkedList<Integer> a = new DLinkedList<>();
        a.addAll(new Integer[] {1, 5, 7});
        LinkedList<Integer> d = new LinkedList<>();
        d.add(1); d.add(100); d.add(500);
        a.addAll(new LinkedList<>(d));
        assertEquals(Integer.valueOf(500), a.get(5));
        assertEquals(Integer.valueOf(5), a.get(1));
    }

    @Test
    public void length() {
        DLinkedList<Integer> a = new DLinkedList<>();
        a.addAll(new Integer[] {1, 2, 3, 5});
        a.add(3, 4);
        a.add(6);
        assertEquals(6, a.size());
    }

    @Test
    public void insert() {
        DLinkedList<Integer> a = new DLinkedList<>();
        a.addAll(new Integer[] {1, 2, 3, 5});
        a.add(3, 4);
        for (int i = 0; i < 5; i++) {
            assertEquals(Integer.valueOf(i + 1), a.get(i));
        }
    }
}
