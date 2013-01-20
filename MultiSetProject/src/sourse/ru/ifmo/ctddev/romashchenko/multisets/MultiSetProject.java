/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sourse.ru.ifmo.ctddev.romashchenko.multisets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import sourse.ru.ifmo.ctddev.romashchenko.multisets.Bag;

/**
 *
 * @author Christina Romashchenko
 */
public class MultiSetProject {

    public static void main(String[] args) {
        Iterator<Integer> it;
        Bag<Integer> bag = new Bag<Integer>();
        Collections.addAll(bag, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 4, 4, 4, null);
        it = bag.iterator();
        while (it.hasNext()) {
            Integer e = it.next();
            System.out.print("\n" + e + "\t");
            if (e != null && e % 2 == 0) {
                it.remove();
                System.out.print(" has beem removed");
            }
        }

        System.out.println("\ndump");
        it = bag.iterator();
        while (it.hasNext()) {
            Integer e = it.next();
            System.out.println(e);
        }
        bag.remove(null);
        System.out.println(bag);

    }
}
