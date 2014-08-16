package com.taucarre.smartdeals.smartdealsapp.modele;

import com.appspot.smart_deals.smartdeals.model.Deal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by tarekelchami on 25/04/14.
 */
public class ListeDeal {

    List<Deal> listeDeal;

    public ListeDeal() {
        listeDeal = new ArrayList<Deal>();
    }

    public void add(int i, Deal deal) {
        listeDeal.add(i, deal);
    }

    public Deal set(int i, Deal deal) {
        return listeDeal.set(i, deal);
    }

    public int lastIndexOf(Object o) {
        return listeDeal.lastIndexOf(o);
    }

    public Object[] toArray() {
        return listeDeal.toArray();
    }

    public void clear() {
        listeDeal.clear();
    }

    public Iterator<Deal> iterator() {
        return listeDeal.iterator();
    }

    public boolean addAll(Collection<? extends Deal> deals) {
        return listeDeal.addAll(deals);
    }

    public boolean containsAll(Collection<?> objects) {
        return listeDeal.containsAll(objects);
    }

    public int size() {
        return listeDeal.size();
    }

    public boolean addAll(int i, Collection<? extends Deal> deals) {
        return listeDeal.addAll(i, deals);
    }

    public ListIterator<Deal> listIterator() {
        return listeDeal.listIterator();
    }

    public ListIterator<Deal> listIterator(int i) {
        return listeDeal.listIterator(i);
    }

    public Deal get(int i) {
        return listeDeal.get(i);
    }

    public boolean remove(Object o) {
        return listeDeal.remove(o);
    }

    public boolean contains(Object o) {
        return listeDeal.contains(o);
    }

    public boolean removeAll(Collection<?> objects) {
        return listeDeal.removeAll(objects);
    }

    public int indexOf(Object o) {
        return listeDeal.indexOf(o);
    }

    public boolean add(Deal deal) {
        return listeDeal.add(deal);
    }

    public List<Deal> subList(int i, int i2) {
        return listeDeal.subList(i, i2);
    }

    public boolean isEmpty() {
        return listeDeal.isEmpty();
    }

    public <T> T[] toArray(T[] ts) {
        return listeDeal.toArray(ts);
    }

    public Deal remove(int i) {
        return listeDeal.remove(i);
    }

    public boolean retainAll(Collection<?> objects) {
        return listeDeal.retainAll(objects);
    }
}
