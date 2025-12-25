package controller;

import model.LaporanBarang;
import model.SummaryData;
import util.CsvLaporanHelper;

import java.util.List;

public class UserLaporanController {

    /* ================= GENERATE ID (LPR-01, LPR-02, ...) ================= */
    private static String generateId() {

        List<LaporanBarang> data = CsvLaporanHelper.readAll();
        int max = 0;

        for (LaporanBarang l : data) {
            String id = l.getIdLaporan(); // contoh: LPR-03
            if (id != null && id.startsWith("LPR-")) {
                try {
                    int num = Integer.parseInt(id.replace("LPR-", ""));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }

        return String.format("LPR-%02d", max + 1);
    }

    /* ================= TAMBAH LAPORAN ================= */
    public static void tambahLaporan(
            String nama,
            String jenis,
            String lokasi,
            String kerusakan,
            String deskripsi,
            String status,
            String tanggal,
            String fotoPaths
    ) {
        String id = generateId(); // 🔥 ID RAPI

        CsvLaporanHelper.append(
                id,
                nama,
                jenis,
                lokasi,
                kerusakan,
                deskripsi,
                status,
                tanggal,
                fotoPaths
        );
    }

    /* ================= GET ALL ================= */
    public static List<LaporanBarang> getAllLaporan() {
        return CsvLaporanHelper.readAll();
    }

    /* ================= RIWAYAT LAPORAN ================= */
    public static List<LaporanBarang> getRiwayatLaporan() {
        return CsvLaporanHelper.readAll();
    }

    /* ================= GET BY ID ================= */
    public static LaporanBarang getById(String id) {
        for (LaporanBarang l : CsvLaporanHelper.readAll()) {
            if (l.getIdLaporan().equals(id)) {
                return l;
            }
        }
        return null;
    }

    /* ================= UPDATE STATUS ================= */
    public static boolean updateStatus(String id, String statusBaru) {
        try {
            List<LaporanBarang> data = CsvLaporanHelper.readAll();

            for (LaporanBarang l : data) {
                if (l.getIdLaporan().equals(id)) {
                    l.setStatus(statusBaru);
                    break;
                }
            }

            CsvLaporanHelper.writeAll(data);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ================= DELETE ================= */
    public static void hapusLaporan(String id) {
        CsvLaporanHelper.deleteById(id);
    }

    /* ================= SUMMARY ================= */
    public static SummaryData getSummary() {

        List<LaporanBarang> data = CsvLaporanHelper.readAll();

        int total = data.size();
        int processing = 0;
        int done = 0;

        for (LaporanBarang l : data) {
            String status = l.getStatus();
            if ("Selesai".equalsIgnoreCase(status)
                    || "Normal".equalsIgnoreCase(status)) {
                done++;
            } else {
                processing++;
            }
        }

        return new SummaryData(total, processing, done);
    }
}
