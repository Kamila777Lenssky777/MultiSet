/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.ru.ifmo.ctddev.romashchenko.multisets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sourse.ru.ifmo.ctddev.romashchenko.multisets.Bag;
import static org.junit.Assert.*;

/**
 *
 * @author I
 */
public class BagTest {
    
    public static class Entry {
        
        Integer number = 0;
        String name = "zero line";
        
        public Entry() {
        }
        
        public Entry(Integer number, String name) {
            this.number = number;
            this.name = name;
        }
        
        @Override
        public String toString() {
            return "Entry{" + "number=" + number + ", name=" + name + '}';
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(this.number);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Entry) {
                final Entry other = (Entry) obj;
                return other.number == this.number && other.name.equals(this.name);
            }
            return false;
        }
    }
    private static Bag<Entry> bag;
    private static Bag<Entry> exampleBag = new Bag<>();
    
    @BeforeClass
    public static void init() {
        System.out.println("before class");
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < i; k++) {
                exampleBag.add(new Entry(k, "line " + i));
            }
        }
    }
    
    @Before
    public void tearUp() {
        bag = new Bag<>();
        System.out.println("----------------------------------");
    }

    /**
     * Test of size method, of class Bag after adding one element
     */
    @Test
    public void testSizeAfterAddingOneElement() {
        System.out.println("testSizeAfterAddingOneElement");
        int expResult = bag.size();
        bag.add(new Entry(1, "one"));
        expResult++;
        int result = bag.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of size method, of class Bag after adding several elements
     */
    @Test
    public void testSizeAfterAddingSeveralElement() {
        System.out.println("testSizeAfterAddingSeveralElement");
        int expResult = bag.size();
        bag.add(new Entry(1, "one"));
        bag.add(new Entry(1, "one"));
        bag.add(new Entry(1, "two"));
        bag.add(new Entry(3, "three"));
        expResult += 4;
        int result = bag.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class Bag. isEmpty <=> size==0
     */
    @Test
    public void testIsEmpty() {
        System.out.println("testIsEmpty");
        boolean result = ((bag.size() == 0) == bag.isEmpty());
        bag.add(new Entry());
        result &= ((bag.size() == 0) == bag.isEmpty());
        assertTrue(result);
    }

    /**
     * Test of contains method after adding {e1, e1 :
     * hashCode(e1)==hashCode(e2)}
     */
    @Test
    public void testContains() {
        System.out.println("testContains");
        Entry e1 = new Entry(1, " one");
        Entry e2 = new Entry(1, "two");
        bag.add(e1);
        bag.add(e2);
        assertTrue(bag.contains(e1));
        assertTrue(bag.contains(e2));
    }
    
    @Test
    public void testIfNotContains() {
        System.out.println("testIfNotContains");
        bag = exampleBag;
        assertFalse(bag.contains(new Entry(33, "33")));
    }

    /**
     * Test of iterator method, of class Bag.
     */
    @Test
    public void testIterator() {
        System.out.println("testIterator");
        bag = exampleBag;
        Iterator result = bag.iterator();
        assertTrue(result != null);
    }
    
    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorOnModificationException() {
        System.out.println("testIteratorOnModificationException");
        bag = exampleBag;
        Iterator<Entry> iterator = bag.iterator();
        bag.add(new Entry());
        iterator.next();
    }

    /**
     * Test of toArray method, of class Bag.
     */
    @Test
    public void testToArray_0args() {
        System.out.println("toArray_0args");
        Bag instance = new Bag();
        Entry[] expResult = new Entry[]{new Entry(), new Entry(1, "one"), new Entry(1, "one"), new Entry(1, "two"), new Entry(2, "two")};
        List<Entry> expList = Arrays.asList(expResult);
        bag.addAll(expList);
        Object[] result = bag.toArray();
        List<Object> resList = Arrays.asList(result);
        assertTrue(expList.containsAll(resList) && resList.containsAll(expList) && resList.size() == expList.size());
    }

    /**
     * Test of toArray method, of class Bag.
     */
    @Test
    public void testToArray_GenericType() {
        System.out.println("toArray_GenericType");
        Bag instance = new Bag();
        Entry[] expResult = new Entry[]{new Entry(), new Entry(1, "one"), new Entry(1, "one"), new Entry(1, "two"), new Entry(2, "two")};
        List<Entry> expList = Arrays.asList(expResult);
        bag.addAll(expList);
        Entry[] result = new Entry[]{};
        result = bag.toArray(result);
        List<Entry> resList = Arrays.asList(result);
        assertTrue(expList.containsAll(resList));
        assertTrue(resList.containsAll(expList));
        assertTrue(resList.size() == expList.size());
    }

    /**
     * Test of remove method, of class Bag.
     */
    @Test
    public void testRemove() {
        System.out.println("testRemove");
        Entry e1 = new Entry(1, "one");
        Entry e2 = new Entry(1, "two");
        bag.add(e1);
        bag.add(e2);
        bag.remove(e1);
        assertTrue(!bag.contains(e1));
        assertTrue(bag.contains(e2));
    }

    /**
     * Test of remove method, of class Bag.
     */
    @Test
    public void testRemoveIfNull() {
        System.out.println("testRemoveIfNull");
        bag = exampleBag;
        bag.add(null);
        bag.remove(null);
        assertTrue(bag.containsAll(exampleBag));
        assertTrue(bag.size() == exampleBag.size());
    }

    /**
     * Test of containsAll method, of class Bag.
     */
    @Test
    public void testContainsAll() {
        System.out.println("testContainsAll");
        bag.addAll(exampleBag);
        bag.add(new Entry());
        bag.add(new Entry(555, "line 555"));
        boolean result = bag.containsAll(exampleBag);
        assertTrue(result);
    }

    /**
     * Test of containsAll method, of class Bag.
     */
    @Test
    public void testIfNotContainsAny() {
        System.out.println("testIfNotContainsAny");
        bag = exampleBag;
        List<Entry> list = new ArrayList<Entry>();
        Collections.addAll(list, new Entry(33, "33"), new Entry(44, "44"));
        boolean result = bag.containsAll(list);
        assertFalse(result);
    }

    /**
     * Test of addAll method, of class Bag after adding a list of entries
     */
    @Test
    public void testAddAll() {
        System.out.println("testAddAll");
        int expResult = bag.size();
        List<Entry> list = new ArrayList<>();
        list.add(new Entry(1, "one"));
        list.add(new Entry(1, "one"));
        list.add(new Entry(1, "two"));
        list.add(new Entry(3, "three"));
        bag.addAll(list);
        expResult += 4;
        int result = bag.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of removeAll method, of class Bag.
     */
    @Test
    public void testRemoveAll() {
        System.out.println("testRemoveAll");
        Collection<Entry> c = Arrays.asList(new Entry(1, "one"), new Entry(1, "two"), new Entry(2, "two"));
        Entry e7 = new Entry(777, "777");
        bag.addAll(c);
        bag.add(e7);
        bag.removeAll(c);
        assertTrue(bag.contains(e7));
        assertTrue(bag.size() == 1);
    }

    /**
     * Test of retainAll method, of class Bag. bag = {Entry{1,"one}, Entry{2,
     * "two}, Entry{3, "three"}} col = {Entry{1,"one}, Entry{2, "two}, Entry{1,
     * "two" }} bag.retrain(c) -> bag = {Entry{1,"one}, Entry{2, "two}}
     */
    @Test
    public void testRetainAll() {
        System.out.println("testRetainAll");
        Entry e11 = new Entry(1, "one");
        Entry e22 = new Entry(2, "two");
        Entry e33 = new Entry(3, "three");
        Entry e12 = new Entry(1, "two");
        Collection<Entry> c = Arrays.asList(e11, e22, e33, e33);
        bag.addAll(c);
        List<Entry> col = Arrays.asList(e11, e12, e22);
        bag.retainAll(col);
        assertTrue(bag.contains(e11));
        assertTrue(bag.contains(e22));
        assertTrue(bag.size() == 2);
    }

    /**
     * Test of clear method, of class Bag.
     */
    @Test
    public void testClear() {
        System.out.println("testClear");
        Entry e = new Entry(1, " PRESENT");
        for (int i = 0; i < 5; i++) {
            bag.add(e);
        }
        bag.clear();
        assertTrue(bag.size() == 0);
    }

    /**
     * Test of iterator method, of class Bag.
     *
     */
    @Test
    public void testIteratorOnValues() {
        System.out.println("testIteratorOnValues");
        bag = exampleBag;
        bag.add(null);
        bag.add(null);
        List<Entry> list = new ArrayList<>(bag.size());
        System.out.println(bag);
        Iterator<Entry> it = bag.iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        assertTrue(list.size() == bag.size());
        assertTrue(list.containsAll(exampleBag));
        assertTrue(list.contains(null));
    }

    /**
     * Test of iterator method, of class Bag.
     */
    @Test
    public void testIteratorOnGroupingValues() {
        System.out.println("testIteratorOnGroupingValues");
        Bag<Integer> bagOfInteger = new Bag<>();
        bagOfInteger.add(null);         //null - 2 times
        bagOfInteger.add(null);         //1    - 3 times
        bagOfInteger.add(1);            //2    - 2 times
        bagOfInteger.add(2);            //3    - 1 
        bagOfInteger.add(1);
        bagOfInteger.add(2);
        bagOfInteger.add(3);
        bagOfInteger.add(1);
        List<Integer> list = new ArrayList<>(bagOfInteger.size());
        Iterator<Integer> it = bagOfInteger.iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        int indexOfNull = list.indexOf(null);
        int indexOfOne = list.indexOf(1);
        int indexOfTwo = list.indexOf(2);
        int indexOfThree = list.indexOf(3);
        assertTrue(list.size() == bagOfInteger.size());
        assertTrue(indexOfNull != -1);                          //bag contains null
        assertTrue(list.get(indexOfNull + 1) == null);          //second null
        assertTrue(indexOfOne != -1);                           //bag contains 1
        assertTrue(list.get(indexOfOne + 1).equals(1));         //second 1
        assertTrue(list.get(indexOfOne + 2).equals(1));         //third 1
        assertTrue(indexOfTwo != -1);                           //bag contains 2
        assertTrue(list.get(indexOfTwo + 1).equals(2));         //second 2
        assertTrue(indexOfThree != -1);                         //bag contains 3
    }
    
    @Test
    public void testNullRemove() {
        assertFalse(bag.remove(new Entry(777, "zzz")));
    }
}
