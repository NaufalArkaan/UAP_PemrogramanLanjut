package view.admin;

import controller.UserLaporanController;
import model.LaporanBarang;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class DetailLaporanPanel extends JPanel {

    private final AdminFrame parentFrame;
    private LaporanBarang laporan;

    /* ===== STYLE ===== */
    private static final Color BG = new Color(245,246,250);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color BORDER = new Color(220,220,220);

    private static final Font H1 = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font H2 = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font LABEL = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font VALUE = new Font("Segoe UI", Font.PLAIN, 13);

    public DetailLaporanPanel(AdminFrame parentFrame, String laporanId) {
        this.parentFrame = parentFrame;
        this.laporan = UserLaporanController.getById(laporanId);

        if (laporan == null) {
            JOptionPane.showMessageDialog(
                    parentFrame,
                    "Data laporan tidak ditemukan",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            parentFrame.showPage("peralatan");
            return;
        }

        setLayout(new BorderLayout());
        setBackground(BG);
        setBorder(new EmptyBorder(16,16,16,16));

        add(header(), BorderLayout.NORTH);
        add(content(), BorderLayout.CENTER);
    }

    /* ================= HEADER ================= */

    private JComponent header() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(0,0,16,0));

        JButton back = new JButton("← Kembali");
        back.addActionListener(e -> parentFrame.showPage("peralatan"));

        JLabel title = new JLabel("Detail Laporan Kerusakan", SwingConstants.CENTER);
        title.setFont(H1);

        JLabel id = new JLabel("ID: " + laporan.getIdLaporan());
        id.setForeground(Color.GRAY);

        p.add(back, BorderLayout.WEST);
        p.add(title, BorderLayout.CENTER);
        p.add(id, BorderLayout.EAST);

        return p;
    }

    /* ================= CONTENT ================= */

    private JComponent content() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.weighty = 1;

        g.gridx = 0;
        g.weightx = 0.7;
        wrapper.add(leftColumn(), g);

        g.gridx = 1;
        g.weightx = 0.05;
        wrapper.add(Box.createHorizontalStrut(16), g);

        g.gridx = 2;
        g.weightx = 0.25;
        wrapper.add(rightColumn(), g);

        return wrapper;
    }

    /* ================= LEFT COLUMN ================= */

    private JComponent leftColumn() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        p.add(infoPeralatan());
        p.add(Box.createVerticalStrut(12));
        p.add(infoKerusakan());
        p.add(Box.createVerticalStrut(12));
        p.add(updateStatus());

        return p;
    }

    private JComponent infoPeralatan() {
        JPanel c = new JPanel(new GridBagLayout());
        c.setOpaque(false);

        GridBagConstraints g = baseGrid();
        addRow(c, g, 0, "Nama", laporan.getNamaBarang());
        addRow(c, g, 1, "Jenis", laporan.getJenis());
        addRow(c, g, 2, "Lokasi", laporan.getLokasi());

        return card("Informasi Peralatan", c);
    }

    private JComponent infoKerusakan() {
        JPanel c = new JPanel(new GridBagLayout());
        c.setOpaque(false);

        GridBagConstraints g = baseGrid();
        addRow(c, g, 0, "Kerusakan", laporan.getKerusakan());
        addRow(c, g, 1, "Deskripsi", laporan.getDeskripsi());
        addRow(c, g, 2, "Tanggal", laporan.getTanggal());

        return card("Informasi Kerusakan", c);
    }

    /* ================= UPDATE STATUS (FINAL FIX) ================= */

    private JComponent updateStatus() {
        JPanel c = new JPanel(new GridBagLayout());
        c.setOpaque(false);

        GridBagConstraints g = baseGrid();

        g.gridx = 0;
        g.gridy = 0;
        c.add(label("Status"), g);

        g.gridx = 1;
        JComboBox<String> cb = new JComboBox<>(
                new String[]{"Rusak","Dalam Perbaikan","Selesai"}
        );
        cb.setSelectedItem(laporan.getStatus());
        cb.setPreferredSize(new Dimension(200,32));
        c.add(cb, g);

        g.gridy++;
        g.gridx = 1;
        g.anchor = GridBagConstraints.EAST;

        JButton save = new JButton("Simpan Perubahan");
        save.addActionListener(e -> {

            String statusBaru = cb.getSelectedItem().toString();

            boolean ok = UserLaporanController.updateStatus(
                    laporan.getIdLaporan(),
                    statusBaru
            );

            if (ok) {
                // 🔥 FIX INTI: ambil ulang data dari CSV
                laporan = UserLaporanController.getById(laporan.getIdLaporan());

                JOptionPane.showMessageDialog(
                        this,
                        "Status berhasil diperbarui",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE
                );

                parentFrame.showPage("peralatan");
                parentFrame.refreshDashboard();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Gagal menyimpan perubahan",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        c.add(save, g);
        return card("Update Status", c);
    }

    /* ================= RIGHT COLUMN ================= */

    private JComponent rightColumn() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        p.add(foto());
        return p;
    }

    private JComponent foto() {
        JPanel c = new JPanel(new BorderLayout());
        c.setOpaque(false);

        JLabel img = new JLabel("Tidak ada foto", SwingConstants.CENTER);
        img.setPreferredSize(new Dimension(220,260));
        img.setBorder(new LineBorder(BORDER));

        String path = laporan.getFotoPaths();
        if (path != null && !path.isBlank()) {
            try {
                File f = new File(path);
                if (f.exists()) {
                    BufferedImage bi = ImageIO.read(f);
                    Image scaled = bi.getScaledInstance(
                            220,260,Image.SCALE_SMOOTH
                    );
                    img.setIcon(new ImageIcon(scaled));
                    img.setText("");
                }
            } catch (Exception ignored) {}
        }

        c.add(img, BorderLayout.CENTER);
        return card("Foto Kerusakan", c);
    }

    /* ================= HELPERS ================= */

    private JPanel card(String title, JComponent content) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(new CompoundBorder(
                new LineBorder(BORDER),
                new EmptyBorder(12,12,12,12)
        ));

        JLabel t = new JLabel(title);
        t.setFont(H2);
        t.setBorder(new EmptyBorder(0,0,8,0));

        card.add(t, BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private GridBagConstraints baseGrid() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6,6,6,6);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
        return g;
    }

    private void addRow(JPanel p, GridBagConstraints g, int y, String l, String v) {
        g.gridy = y;

        g.gridx = 0;
        g.weightx = 0;
        p.add(label(l), g);

        g.gridx = 1;
        g.weightx = 1;
        p.add(value(v), g);
    }

    private JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setFont(LABEL);
        return l;
    }

    private JLabel value(String t) {
        JLabel v = new JLabel(t);
        v.setFont(VALUE);
        return v;
    }
}
