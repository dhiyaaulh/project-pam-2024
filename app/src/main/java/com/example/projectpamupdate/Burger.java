package com.example.projectpamupdate;

public class Burger {
    public String id;
    public String nama, roti, daging, pelengkap, saus;

    public Burger(String nama, String roti, String daging, String pelengkap, String saus) {
        this.nama = nama;
        this.roti = roti;
        this.daging = daging;
        this.pelengkap = pelengkap;
        this.saus = saus;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNama(){
        return nama;
    }

    public String getRoti(){return roti;}

    public String getDaging(){return daging;}

    public String getPelengkap(){return pelengkap;}

    public String getSaus(){return saus;}

    public String getDeskripsi(){
        return roti + ", " + daging + ", " + pelengkap + ", " + saus;
    }
}
