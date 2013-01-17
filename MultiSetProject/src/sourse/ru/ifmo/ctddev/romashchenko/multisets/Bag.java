/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sourse.ru.ifmo.ctddev.romashchenko.multisets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Андрей
 */
public class Bag<E> implements Collection<E> {

    private HashMap<E, List<E>> map;
    transient int size;

    public Bag() {
        map = new HashMap<>();
    }

    public Bag(Set<? extends E> set) {
        this();
        addAll(set);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        List<E> list = map.get(o);
        return list == null ? false : list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new BagIterator<>();
    }

    @Override
    public Object[] toArray() {
        List<Object> list = new ArrayList<>();
        for (E k : map.keySet()) {
            for (E e : map.get(k)) {
                list.add(e);
            }
        }
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        List<Object> list = new ArrayList<>();
        for (E k : map.keySet()) {
            for (E e : map.get(k)) {
                list.add(e);
            }
        }
        return list.toArray(a);
    }

    @Override
    public boolean add(E e) {
        size++;
        if (!map.containsKey(e)) {
            List<E> newlist = new ArrayList<E>();
            newlist.add(e);
            return (map.put(e, newlist) != null);
        } else {
            return (map.get(e).add(e));
        }
    }

    @Override
    public boolean remove(Object o) {
        int removedsize = map.get(o).size();
        return (map.remove(o) != null && (size -= removedsize) >= 0);
    }

    //there is unaware what it should do exactly
    @Override
    public boolean containsAll(Collection<?> c) {
        return map.keySet().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int oldsize = size;
        for (E e : c) {
            add(e);
        }
        return size() != oldsize;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int oldsize = size;
        for (Object e : c) {
            remove(e);
        }
        return oldsize != size;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int oldsize = size;
        for (E e : map.keySet()) {
            if (!c.contains(e)) {
                remove(e);
            }
        }
        return size != oldsize;
    }

    @Override
    public void clear() {
        map.clear();
        size = 0;
    }

    @Override
    public String toString() {
        return "Bag{" + map + '}' + " size = " + size;
    }

    private class BagIterator<E> implements Iterator<E> {

        private List<E> listOfEntries;
        private Iterator<E> bagIterator;

        public BagIterator() {
            E arr[] = (E[])new Object[]{};
            arr = Bag.this.toArray(arr);
            listOfEntries = new ArrayList<>(Arrays.asList(arr));
            bagIterator = listOfEntries.iterator();
           
        }

        @Override
        public boolean hasNext() {
            return bagIterator.hasNext();
        }

        @Override
        public E next() {
            return bagIterator.next();
        }

        @Override
        public void remove() {
            bagIterator.remove();
        }
    }
}
