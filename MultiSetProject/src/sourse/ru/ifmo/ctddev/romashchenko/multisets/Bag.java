/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sourse.ru.ifmo.ctddev.romashchenko.multisets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @author I
 */
public class Bag<E> implements Collection<E> {

    private Map<E, List<E>> map;
    int size;

    public Bag() {
        map = new HashMap<>();
    }

    public Bag(Collection<? extends E> collection) {
        this();
        addAll(collection);
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
        return new BagIterator();
    }

    @Override
    public Object[] toArray() {
        List<Object> list = fillListByElements();
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        List<Object> list = fillListByElements();
        return list.toArray(a);
    }

    private List<Object> fillListByElements() {
        List<Object> list = new ArrayList<>(size);
        for (E k : map.keySet()) {
            for (E e : map.get(k)) {
                list.add(e);
            }
        }
        return list;
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
        List<E> onremove = map.get(o);
        if (onremove == null) {
            return false;
        }
        int removedsize = onremove.size();
        map.remove(o);
        size -= removedsize;
        return (removedsize > 0);
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

    private class BagIterator implements Iterator {

        private Iterator<E> listIterator;
        private Iterator<List<E>> mapIterator;
        private List<E> currentList;

        public BagIterator() {
            mapIterator = map.values().iterator();
            listIterator = Collections.emptyListIterator();
        }

        @Override
        public boolean hasNext() {
            boolean isNextIs = false;;
            if (listIterator.hasNext()) {
                return true;
            } else {
                if (mapIterator.hasNext()) {
                    currentList = mapIterator.next();
                    listIterator = currentList.listIterator();
                    return hasNext();
                } else {
                    return false;
                }
            }
        }
        
        @Override
        public E next() {
            hasNext();
            return listIterator.next();
        }

        @Override
        public void remove() {
            listIterator.remove();
            size--;
            //checking on empty list
            if(currentList!=null && currentList.isEmpty()){
                mapIterator.remove();
            }
        }
    }
}
