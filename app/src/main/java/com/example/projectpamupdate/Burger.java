package com.example.projectpamupdate;

public class Burger {
    public String nama;

    public String roti;
    public String daging;
    public String pelengkap;
    public String saus;
    public boolean terpilih = false;

    public Burger(String nama, String roti, String daging, String pelengkap, String saus) {
        this.nama = nama;
        this.roti = roti;
        this.daging = daging;
        this.pelengkap = pelengkap;
        this.saus = saus;
    }
}
