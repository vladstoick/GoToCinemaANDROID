package com.vladstoick.objects;

public class Cinema {
    public final String distance;
    public final String duration;
    public final String latCinema;
    public final String lngCinema;

    public Cinema(String distance,String durata,String latCinema,String lngCinema,String name)
    {
        this.distance=distance;
        this.duration=durata;
        this.latCinema=latCinema;
        this.lngCinema=lngCinema;
    }
}
