package view.admin;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class DetailLaporanPanel extends JPanel {
    private final AdminFrame parentFrame;
    private final String laporanId;

    public DetailLaporanPanel(AdminFrame parentFrame, String laporanId) {
        this.parentFrame = parentFrame;
        this.laporanId = laporanId;

        setLayout(new BorderLayout(16, 16));
        setBackground(new Color(245, 246, 250));
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }

    // ================= HEADER =================
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JButton btnBack = new JButton("← Kembali");
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnBack.addActionListener(e -> {
            parentFrame.showPage("peralatan"); // 🔥 BALIK KE DATA PERALATAN
        });

        JLabel title = new JLabel("Detail Laporan Kerusakan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel lblId = new JLabel("ID: #" + laporanId);
        lblId.setForeground(Color.GRAY);

        header.add(btnBack, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        header.add(lblId, BorderLayout.EAST);

        return header;
    }

    // ================= CONTENT =================
    private JPanel createContent() {
        JPanel content = new JPanel(new GridLayout(1, 2, 20, 0));
        content.setOpaque(false);

        content.add(createLeftSection());
        content.add(createRightSection());

        return content;
    }

    // ================= LEFT =================
    private JPanel createLeftSection() {
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
        JPanel panel = createCard("Informasi Peralatan");

        panel.add(createLabel("Nama Peralatan", "Komputer PC-15"));
        panel.add(createLabel("Jenis Peralatan", "Komputer"));
        panel.add(createLabel("Lokasi", "Lab 1"));

        return panel;
    }

    private JPanel infoKerusakan() {
        JPanel panel = createCard("Informasi Kerusakan");

        panel.add(createLabel("Jenis Kerusakan", "Hardware"));
        panel.add(createMultiline(
                "Deskripsi",
                "Komputer tidak dapat menyala sama sekali. "
                        + "Sudah dicoba tekan tombol power berulang kali "
                        + "namun tidak ada respon."
        ));
        panel.add(createLabel("Tanggal Laporan", "14 Desember 2025"));
        panel.add(createLabel("Pelapor", "Budi Santoso"));

        return panel;
    }

    private JPanel updateStatus() {
        JPanel panel = createCard("Update Status");

        JLabel current = new JLabel("Status Saat Ini:  Rusak");
        current.setForeground(new Color(185,28,28));
        current.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JComboBox<String> cbStatus = new JComboBox<>(new String[]{
                "Rusak", "Dalam Perbaikan", "Normal"
        });

        JTextArea catatan = new JTextArea(4, 20);
        catatan.setLineWrap(true);
        catatan.setWrapStyleWord(true);
        catatan.setBorder(new LineBorder(new Color(200,200,200)));

        JButton btnSave = new JButton("Simpan Perubahan");
        btnSave.setBackground(new Color(14,64,160));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));

        panel.add(current);
        panel.add(Box.createVerticalStrut(8));
        panel.add(new JLabel("Ubah Status"));
        panel.add(cbStatus);
        panel.add(Box.createVerticalStrut(8));
        panel.add(new JLabel("Catatan Perbaikan"));
        panel.add(new JScrollPane(catatan));
        panel.add(Box.createVerticalStrut(12));
        panel.add(btnSave);

        return panel;
    }

    // ================= RIGHT =================
    private JPanel createRightSection() {
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);

        right.add(fotoKerusakan());
        right.add(Box.createVerticalStrut(12));
        right.add(riwayatAktivitas());

        return right;
    }

    private JPanel fotoKerusakan() {
        JPanel panel = createCard("Foto Kerusakan");

        JLabel image = new JLabel("Tidak ada foto", SwingConstants.CENTER);
        image.setPreferredSize(new Dimension(260, 200));
        image.setBorder(new LineBorder(new Color(200,200,200)));
        image.setForeground(Color.GRAY);

        JPanel thumbs = new JPanel(new GridLayout(1,3,8,8));
        thumbs.setOpaque(false);

        for (int i = 0; i < 3; i++) {
            JLabel t = new JLabel("📷", SwingConstants.CENTER);
            t.setBorder(new LineBorder(new Color(220,220,220)));
            thumbs.add(t);
        }

        panel.add(image);
        panel.add(Box.createVerticalStrut(10));
        panel.add(thumbs);

        return panel;
    }

    private JPanel riwayatAktivitas() {
        JPanel panel = createCard("Riwayat Aktivitas");

        JTextArea log = new JTextArea(
                "14 Des 2025 10:30\n"
                        + "Laporan dibuat oleh Budi Santoso\n\n"
                        + "14 Des 2025 11:15\n"
                        + "Status diubah: Dalam Perbaikan\n\n"
                        + "14 Des 2025 14:20\n"
                        + "Catatan: Sedang mengecek power supply"
        );
        log.setEditable(false);
        log.setBackground(panel.getBackground());

        panel.add(new JScrollPane(log));
        return panel;
    }

    // ================= HELPERS =================
    private JPanel createCard(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,220,220)),
                new EmptyBorder(12,12,12,12)
        ));

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    private JPanel createLabel(String label, String value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(new JLabel(label + ":"), BorderLayout.NORTH);
        p.add(new JLabel(value), BorderLayout.CENTER);
        return p;
    }

    private JPanel createMultiline(String label, String value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(new JLabel(label + ":"), BorderLayout.NORTH);

        JTextArea ta = new JTextArea(value);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setEditable(false);
        ta.setBackground(p.getBackground());

        p.add(ta, BorderLayout.CENTER);
        return p;
    }
}
