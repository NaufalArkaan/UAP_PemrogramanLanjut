package view.admin;

import javax.swing.*;
import java.awt.*;

public class DetailLaporanDialog extends JDialog {

    public DetailLaporanDialog(Window parent, String reportId) {
        super(parent, "Detail Laporan - Sistem Monitoring Lab", ModalityType.APPLICATION_MODAL);
        setSize(900, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(createHeader(reportId), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }

    // ================= HEADER =================
    private JPanel createHeader(String id) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        header.setBackground(Color.WHITE);

        JButton btnBack = new JButton("← Kembali");
        btnBack.addActionListener(e -> dispose());

        JLabel title = new JLabel("Detail Laporan Kerusakan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel lblId = new JLabel("ID: " + id);
        lblId.setForeground(Color.GRAY);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        left.setOpaque(false);
        left.add(btnBack);
        left.add(title);

        header.add(left, BorderLayout.WEST);
        header.add(lblId, BorderLayout.EAST);

        return header;
    }

    // ================= CONTENT =================
    private JPanel createContent() {
        JPanel content = new JPanel(new GridLayout(1, 2, 16, 0));
        content.setBorder(BorderFactory.createEmptyBorder(10, 16, 16, 16));
        content.setBackground(new Color(245,246,250));

        content.add(leftColumn());
        content.add(rightColumn());

        return content;
    }

    // ================= LEFT =================
    private JPanel leftColumn() {
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);

        left.add(infoPeralatan());
        left.add(Box.createVerticalStrut(12));
        left.add(infoKerusakan());
        left.add(Box.createVerticalStrut(12));
        left.add(updateStatus());

        return left;
    }

    private JPanel infoPeralatan() {
        return card("Informasi Peralatan",
                "Nama Peralatan", "Komputer PC-15",
                "Jenis", "Komputer",
                "Lokasi", "Lab 1"
        );
    }

    private JPanel infoKerusakan() {
        JPanel panel = baseCard("Informasi Kerusakan");

        panel.add(labelPair("Jenis Kerusakan", "Hardware"));
        panel.add(labelPair("Deskripsi",
                "<html>Komputer tidak dapat menyala sama sekali.<br>" +
                        "Saat ditekan tombol power tidak ada respon.</html>"));
        panel.add(labelPair("Tanggal", "14 Desember 2025"));
        panel.add(labelPair("Pelapor", "Budi Santoso"));

        return panel;
    }

    private JPanel updateStatus() {
        JPanel panel = baseCard("Update Status");

        JLabel current = new JLabel("Status Saat Ini:");
        JLabel badge = badge("Rusak", new Color(220,38,38));

        JComboBox<String> cbStatus = new JComboBox<>(new String[]{
                "Rusak", "Dalam Perbaikan", "Normal"
        });

        JTextArea catatan = new JTextArea(3, 20);
        catatan.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));

        JButton btnSave = new JButton("Simpan Perubahan");
        btnSave.setBackground(new Color(30,64,175));
        btnSave.setForeground(Color.WHITE);

        panel.add(current);
        panel.add(badge);
        panel.add(new JLabel("Ubah Status:"));
        panel.add(cbStatus);
        panel.add(new JLabel("Catatan Perbaikan:"));
        panel.add(new JScrollPane(catatan));
        panel.add(Box.createVerticalStrut(8));
        panel.add(btnSave);

        return panel;
    }

    // ================= RIGHT =================
    private JPanel rightColumn() {
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);

        right.add(fotoKerusakan());
        right.add(Box.createVerticalStrut(12));
        right.add(riwayatAktivitas());

        return right;
    }

    private JPanel fotoKerusakan() {
        JPanel panel = baseCard("Foto Kerusakan");

        JLabel preview = new JLabel("Foto belum tersedia", SwingConstants.CENTER);
        preview.setPreferredSize(new Dimension(250, 180));
        preview.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        preview.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnUpload = new JButton("Upload Foto");
        btnUpload.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(preview);
        panel.add(Box.createVerticalStrut(8));
        panel.add(btnUpload);

        return panel;
    }

    private JPanel riwayatAktivitas() {
        JPanel panel = baseCard("Riwayat Aktivitas");

        JTextArea log = new JTextArea(
                "14 Des 2025 10:30\n" +
                        "Laporan dibuat oleh Budi Santoso\n\n" +
                        "14 Des 2025 11:15\n" +
                        "Status diubah: Dalam Perbaikan\n\n" +
                        "14 Des 2025 14:20\n" +
                        "Catatan: Sedang cek power supply"
        );
        log.setEditable(false);
        log.setBackground(new Color(248,248,248));

        panel.add(new JScrollPane(log));
        return panel;
    }

    // ================= COMPONENT HELPER =================
    private JPanel card(String title, String... pairs) {
        JPanel panel = baseCard(title);
        for (int i = 0; i < pairs.length; i += 2) {
            panel.add(labelPair(pairs[i], pairs[i + 1]));
        }
        return panel;
    }

    private JPanel baseCard(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(8));

        return panel;
    }

    private JPanel labelPair(String label, String value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(new JLabel(label + ":"), BorderLayout.NORTH);
        p.add(new JLabel(value), BorderLayout.CENTER);
        p.setBorder(BorderFactory.createEmptyBorder(4,0,4,0));
        return p;
    }

    private JLabel badge(String text, Color bg) {
        JLabel lbl = new JLabel(text);
        lbl.setOpaque(true);
        lbl.setBackground(bg);
        lbl.setForeground(Color.WHITE);
        lbl.setBorder(BorderFactory.createEmptyBorder(4,8,4,8));
        return lbl;
    }
}
