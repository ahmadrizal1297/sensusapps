package com.tomeskates.sensusapps;

public class Sensus {
    private String sensusid;
    private String provinsi;
    private String kota;
    private String kecamatan;
    private String kelurahan;
    private Integer rw;
    private Integer rt;
    private Integer jumlahkk;
    private Integer jumlahpenduduk;
    private String user;
    private String datetime;

    public Sensus() {
    }

    public Sensus(String sensusid, String provinsi, String kota, String kecamatan, String kelurahan, Integer rw, Integer rt, Integer jumlahkk, Integer jumlahpenduduk, String user, String datetime) {
        this.sensusid = sensusid;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
        this.rw = rw;
        this.rt = rt;
        this.jumlahkk = jumlahkk;
        this.jumlahpenduduk = jumlahpenduduk;
        this.user = user;
        this.datetime = datetime;
    }

    public String getSensusid() {
        return sensusid;
    }

    public void setSensusid(String sensusid) {
        this.sensusid = sensusid;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public Integer getRw() {
        return rw;
    }

    public void setRw(Integer rw) {
        this.rw = rw;
    }

    public Integer getRt() {
        return rt;
    }

    public void setRt(Integer rt) {
        this.rt = rt;
    }

    public Integer getJumlahkk() {
        return jumlahkk;
    }

    public void setJumlahkk(Integer jumlahkk) {
        this.jumlahkk = jumlahkk;
    }

    public Integer getJumlahpenduduk() {
        return jumlahpenduduk;
    }

    public void setJumlahpenduduk(Integer jumlahpenduduk) {
        this.jumlahpenduduk = jumlahpenduduk;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
