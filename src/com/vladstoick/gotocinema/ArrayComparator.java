package com.vladstoick.gotocinema;

import java.util.Comparator;

public class ArrayComparator implements Comparator<AparitiiCinema> {
    @Override
    public int compare(AparitiiCinema o1, AparitiiCinema o2) {
        return (o1.ora).compareTo(o2.ora);
    }
}