package com.codesid.bimbelapps.Model;

public class Siswa {
    String key,nama,ttl,tingkat,paket,urlFile;

    public Siswa(){

    }
    public Siswa(String key,String nama, String ttl, String tingkat, String paket,String urlFile) {
        this.key= key;
        this.nama = nama;
        this.ttl = ttl;
        this.tingkat = tingkat;
        this.paket = paket;
        this.urlFile = urlFile;
    }

    public String getKey() {
        return key;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public String getNama() {
        return nama;
    }

    public String getTtl() {
        return ttl;
    }

    public String getTingkat() {
        return tingkat;
    }

    public String getPaket() {
        return paket;
    }

}
