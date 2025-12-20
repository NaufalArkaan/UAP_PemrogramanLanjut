package model;

import java.time.LocalDate;

public class LaporanBarang {

    private String idLaporan;
    private String namaBarang;
    private String lokasi;
    private String status;
    private LocalDate tanggal;

    public LaporanBarang(String idLaporan, String namaBarang,
                         String lokasi, String status, LocalDate tanggal) {
        this.idLaporan = idLaporan;
        this.namaBarang = namaBarang;
        this.lokasi = lokasi;
        this.status = status;
        this.tanggal = tanggal;
    }

    public String getIdLaporan() {
        return idLaporan;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
