package view.user.form;

import controller.UserLaporanController;
import util.UIStyleUser;
import view.user.UserFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormLaporanPanel extends JPanel {

    private final UserFrame frame;

    private JTextField nama, tanggal;
    private JComboBox<String> jenis, lokasi, kerusakan;
    private JTextArea deskripsi;
    private JButton uploadBtn;
    private File selectedPhoto;

    private static final String PH_NAMA = "Contoh: PC Laboratorium 1";
    private static final String PH_DESC = "Jelaskan kerusakan secara detail...";

    public FormLaporanPanel(UserFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
        setBackground(UIStyleUser.BG);
        add(buildCard());
    }

    /* ================= CARD ================= */

    private JComponent buildCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(1100, 720));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UIStyleUser.BORDER, 1, true),
                new EmptyBorder(24, 36, 32, 36)
        ));

        card.add(header(), BorderLayout.NORTH);
        card.add(form(), BorderLayout.CENTER);
        card.add(actions(), BorderLayout.SOUTH);
        return card;
    }

    /* ================= HEADER ================= */

    private JComponent header() {
        JLabel title = new JLabel("Form Pelaporan Kerusakan Peralatan");
        title.setFont(UIStyleUser.H1);
        title.setForeground(UIStyleUser.PRIMARY);

        JLabel subtitle = new JLabel("Silakan isi data berikut dengan lengkap dan jelas");
        subtitle.setFont(UIStyleUser.BODY);
        subtitle.setForeground(UIStyleUser.TEXT_MUTED);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(0, 0, 14, 0));

        p.add(title);
        p.add(Box.createVerticalStrut(4));
        p.add(subtitle);
        return p;
    }

    /* ================= FORM ================= */

    private JComponent form() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        int y = 0;

        nama = new JTextField();
        makeBig(nama);
        setPlaceholder(nama, PH_NAMA);

        jenis = combo("-- Pilih Jenis --",
                "Komputer / PC Desktop","Monitor","Keyboard","Mouse",
                "Proyektor","Router","Switch","Access Point","Kabel","Meja/Kursi");
        makeBig(jenis);

        lokasi = combo("-- Pilih Lokasi --",
                "LAB-A","LAB-B","LAB-C","LAB-D","LAB-E","LAB-F");
        makeBig(lokasi);

        kerusakan = combo("-- Pilih Jenis --",
                "Hardware","Software","Kelistrikan","Jaringan","Fisik","Kehilangan");
        makeBig(kerusakan);

        deskripsi = new JTextArea();
        deskripsi.setFont(UIStyleUser.BODY);
        deskripsi.setLineWrap(true);
        deskripsi.setWrapStyleWord(true);
        deskripsi.setBorder(BorderFactory.createLineBorder(UIStyleUser.BORDER));
        setPlaceholder(deskripsi, PH_DESC);

        JScrollPane descScroll = new JScrollPane(deskripsi);
        descScroll.setPreferredSize(new Dimension(0, 160));

        tanggal = new JTextField(
                DateTimeFormatter.ofPattern("dd MMMM yyyy")
                        .format(LocalDate.now()));
        tanggal.setEditable(false);
        makeBig(tanggal);

        uploadBtn = new JButton("📤 Pilih File");
        makeBig(uploadBtn);
        uploadBtn.addActionListener(e -> openFileChooser());

        addLabel(p, g, 0, y, "Nama Peralatan");
        addLabel(p, g, 1, y, "Jenis Peralatan");
        y++;
        addField(p, g, 0, y, nama);
        addField(p, g, 1, y, jenis);
        y++;

        addLabel(p, g, 0, y, "Lokasi Laboratorium");
        addLabel(p, g, 1, y, "Jenis Kerusakan");
        y++;
        addField(p, g, 0, y, lokasi);
        addField(p, g, 1, y, kerusakan);
        y++;

        addLabelFull(p, g, y++, "Deskripsi Kerusakan");
        g.fill = GridBagConstraints.BOTH;
        g.weighty = 1;
        addFieldFull(p, g, y++, descScroll);
        g.weighty = 0;
        g.fill = GridBagConstraints.HORIZONTAL;

        addLabel(p, g, 0, y, "Tanggal Laporan");
        addLabel(p, g, 1, y, "Upload Foto");
        y++;
        addField(p, g, 0, y, tanggal);
        addField(p, g, 1, y, uploadBtn);

        return p;
    }

    /* ================= ACTIONS ================= */

    private JComponent actions() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(24, 0, 0, 0));

        JButton batal = new JButton("Batal");
        batal.setPreferredSize(new Dimension(120, 42));
        batal.addActionListener(e -> frame.showDashboard());

        JButton submit = new JButton("Submit Laporan");
        submit.setPreferredSize(new Dimension(190, 44));
        submit.setFont(UIStyleUser.BODY_BOLD);
        submit.setBackground(UIStyleUser.PRIMARY);
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.addActionListener(e -> submitLaporan());

        p.add(batal);
        p.add(submit);
        return p;
    }

    /* ================= SUBMIT (FINAL FIX) ================= */

    private void submitLaporan() {

        String namaVal = nama.getText().trim();
        String descVal = deskripsi.getText().trim();

        if (namaVal.isEmpty() || namaVal.equals(PH_NAMA)
                || jenis.getSelectedIndex() == 0
                || lokasi.getSelectedIndex() == 0
                || kerusakan.getSelectedIndex() == 0
                || descVal.isEmpty()
                || descVal.equals(PH_DESC)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Lengkapi semua data dengan benar!",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String fotoPath = "";
        if (selectedPhoto != null) {
            fotoPath = simpanFoto(selectedPhoto);
        }

        UserLaporanController.tambahLaporan(
                namaVal,
                jenis.getSelectedItem().toString(),
                lokasi.getSelectedItem().toString(),
                kerusakan.getSelectedItem().toString(),
                descVal,
                "Perbaikan",
                tanggal.getText(),
                fotoPath
        );

        /* 🔥 SYNC ADMIN DATA (FINAL FIX) */
        for (Frame f : Frame.getFrames()) {
            if (f instanceof view.admin.AdminFrame admin) {
                admin.showPage("peralatan");
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Laporan berhasil dikirim!",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE
        );

        resetForm();
        frame.showDashboard();
    }

    /* ================= FOTO ================= */

    private String simpanFoto(File file) {
        try {
            File dir = new File("data/foto");
            if (!dir.exists()) dir.mkdirs();

            String ext = file.getName()
                    .substring(file.getName().lastIndexOf("."));

            String fileName = "foto_" + System.currentTimeMillis() + ext;
            File dest = new File(dir, fileName);

            Files.copy(file.toPath(), dest.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            return "data/foto/" + fileName;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menyimpan foto",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

    /* ================= RESET ================= */

    private void resetForm() {
        nama.setText("");
        setPlaceholder(nama, PH_NAMA);

        jenis.setSelectedIndex(0);
        lokasi.setSelectedIndex(0);
        kerusakan.setSelectedIndex(0);

        deskripsi.setText("");
        setPlaceholder(deskripsi, PH_DESC);

        selectedPhoto = null;
        uploadBtn.setText("📤 Pilih File");
    }

    /* ================= HELPERS ================= */

    private void makeBig(JComponent c) {
        c.setPreferredSize(new Dimension(0, 42));
        c.setFont(UIStyleUser.BODY);
    }

    private void addLabel(JPanel p, GridBagConstraints g, int x, int y, String text) {
        g.gridx = x;
        g.gridy = y;
        JLabel l = new JLabel(text);
        l.setFont(UIStyleUser.BODY_BOLD);
        p.add(l, g);
    }

    private void addField(JPanel p, GridBagConstraints g, int x, int y, JComponent c) {
        g.gridx = x;
        g.gridy = y;
        p.add(c, g);
    }

    private void addLabelFull(JPanel p, GridBagConstraints g, int y, String text) {
        g.gridx = 0;
        g.gridy = y;
        g.gridwidth = 2;
        JLabel l = new JLabel(text);
        l.setFont(UIStyleUser.BODY_BOLD);
        p.add(l, g);
        g.gridwidth = 1;
    }

    private void addFieldFull(JPanel p, GridBagConstraints g, int y, JComponent c) {
        g.gridx = 0;
        g.gridy = y;
        g.gridwidth = 2;
        p.add(c, g);
        g.gridwidth = 1;
    }

    private JComboBox<String> combo(String... items) {
        return new JComboBox<>(items);
    }

    private void setPlaceholder(JTextComponent f, String text) {
        f.setText(text);
        f.setForeground(Color.GRAY);
        f.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (f.getText().equals(text)) {
                    f.setText("");
                    f.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (f.getText().isEmpty()) {
                    f.setText(text);
                    f.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void openFileChooser() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedPhoto = fc.getSelectedFile();
            uploadBtn.setText("✔ " + selectedPhoto.getName());
        }
    }
}
