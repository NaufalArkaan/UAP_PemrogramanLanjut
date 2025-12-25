package model;

public class LaporanBarang {

    private String idLaporan;
    private String namaBarang;
    private String jenis;
    private String lokasi;
    private String kerusakan;
    private String deskripsi;
    private String status;
    private String tanggal;      // STRING dari CSV
    private String fotoPaths;    // ⬅️ BARU

    public LaporanBarang(
            String idLaporan,
            String namaBarang,
            String jenis,
            String lokasi,
            String kerusakan,
            String deskripsi,
            String status,
            String tanggal,
            String fotoPaths
    ) {
        this.idLaporan = idLaporan;
        this.namaBarang = namaBarang;
        this.jenis = jenis;
        this.lokasi = lokasi;
        this.kerusakan = kerusakan;
        this.deskripsi = deskripsi;
        this.status = status;
        this.tanggal = tanggal;
        this.fotoPaths = fotoPaths;
    }

    public String getIdLaporan() { return idLaporan; }
    public String getNamaBarang() { return namaBarang; }
    public String getJenis() { return jenis; }
    public String getLokasi() { return lokasi; }
    public String getKerusakan() { return kerusakan; }
    public String getDeskripsi() { return deskripsi; }
    public String getStatus() { return status; }
    public String getTanggal() { return tanggal; }
    public String getFotoPaths() { return fotoPaths; }

    public void setStatus(String status) {
        this.status = status;
    }
}
