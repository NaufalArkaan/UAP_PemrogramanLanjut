package util;

import model.LaporanBarang;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvLaporanHelper {

    // ✅ FIX FINAL: PATH ABSOLUT (TIDAK UBAH LOGIKA)
    private static final String FILE =
            System.getProperty("user.dir") + File.separator + "data" + File.separator + "laporan.csv";

    private static final String HEADER =
            "id,nama,jenis,lokasi,kerusakan,deskripsi,status,tanggal,fotopaths";

    /* ================= APPEND ================= */
    public static void append(
            String id,
            String nama,
            String jenis,
            String lokasi,
            String kerusakan,
            String deskripsi,
            String status,
            String tanggal,
            String fotoPaths
    ) {
        try {
            File f = new File(FILE);
            boolean writeHeader = !f.exists() || f.length() == 0;

            try (PrintWriter pw = new PrintWriter(new FileWriter(f, true))) {

                if (writeHeader) {
                    pw.println(HEADER);
                }

                pw.println(String.join(",",
                        id,
                        nama,
                        jenis,
                        lokasi,
                        kerusakan,
                        deskripsi.replace(",", " "),
                        status,
                        tanggal,
                        fotoPaths == null ? "" : fotoPaths
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ================= READ ================= */
    public static List<LaporanBarang> readAll() {
        List<LaporanBarang> list = new ArrayList<>();
        File f = new File(FILE);
        if (!f.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {

                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] d = line.split(",", -1);
                if (d.length < 9) continue;

                list.add(new LaporanBarang(
                        d[0],
                        d[1],
                        d[2],
                        d[3],
                        d[4],
                        d[5],
                        d[6],
                        d[7],
                        d[8]
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ================= DELETE ================= */
    public static void deleteById(String id) {
        List<LaporanBarang> data = readAll();
        data.removeIf(l -> l.getIdLaporan().equals(id));
        writeAll(data);
    }

    /* ================= WRITE ALL ================= */
    public static void writeAll(List<LaporanBarang> data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {

            pw.println(HEADER);

            for (LaporanBarang l : data) {
                pw.println(String.join(",",
                        l.getIdLaporan(),
                        l.getNamaBarang(),
                        l.getJenis(),
                        l.getLokasi(),
                        l.getKerusakan(),
                        l.getDeskripsi().replace(",", " "),
                        l.getStatus(),
                        l.getTanggal(),
                        l.getFotoPaths() == null ? "" : l.getFotoPaths()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
