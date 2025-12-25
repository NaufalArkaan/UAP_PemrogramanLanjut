package view.admin;

import controller.UserLaporanController;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TambahDataPeralatanPanel extends JPanel {

    private final AdminFrame parent;

    private JTextField txtNama;
    private JComboBox<String> cbJenis, cbLokasi, cbKerusakan, cbStatus;
    private JTextArea txtDeskripsi;

    private JLabel imagePreview;
    private File selectedImage;

    public TambahDataPeralatanPanel(AdminFrame parent) {
        this.parent = parent;

        setLayout(new BorderLayout(16,16));
        setBackground(new Color(245,246,250));
        setBorder(new EmptyBorder(16,16,16,16));

        add(header(), BorderLayout.NORTH);
        add(form(), BorderLayout.CENTER);
    }

    // ================= HEADER =================
    private JPanel header() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JButton btnBack = new JButton("← Kembali");
        btnBack.addActionListener(e -> parent.showPage("peralatan"));

        JLabel title = new JLabel("Tambah Data Peralatan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        panel.add(btnBack, BorderLayout.WEST);
        panel.add(title, BorderLayout.CENTER);

        return panel;
    }

    // ================= FORM =================
    private JPanel form() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,220,220)),
                new EmptyBorder(16,16,16,16)
        ));

        txtNama = new JTextField();
        cbJenis = combo("Komputer","Monitor","Proyektor","Keyboard","Mouse","Printer");
        cbLokasi = combo("Lab A","Lab B","Lab C","Lab D","lab E","Lab F");
        cbKerusakan = combo("Hardware","Software","Jaringan","Kelistrikan");
        cbStatus = combo("Rusak","Dalam Perbaikan","Normal");

        txtDeskripsi = new JTextArea(4,20);
        txtDeskripsi.setLineWrap(true);
        txtDeskripsi.setWrapStyleWord(true);
        txtDeskripsi.setBorder(new LineBorder(new Color(200,200,200)));

        card.add(field("Nama Peralatan", txtNama));
        card.add(field("Jenis Peralatan", cbJenis));
        card.add(field("Lokasi", cbLokasi));
        card.add(field("Jenis Kerusakan", cbKerusakan));
        card.add(field("Status", cbStatus));
        card.add(field("Deskripsi Kerusakan", new JScrollPane(txtDeskripsi)));

        card.add(Box.createVerticalStrut(12));
        card.add(imageSection());
        card.add(Box.createVerticalStrut(16));
        card.add(actionButton());

        return card;
    }

    // ================= IMAGE =================
    private JPanel imageSection() {
        JPanel panel = new JPanel(new BorderLayout(8,8));
        panel.setBorder(new TitledBorder("Foto Kerusakan"));

        imagePreview = new JLabel("Belum ada gambar", SwingConstants.CENTER);
        imagePreview.setPreferredSize(new Dimension(220,150));
        imagePreview.setBorder(new LineBorder(new Color(200,200,200)));

        JButton btnUpload = new JButton("Pilih Gambar");
        btnUpload.addActionListener(e -> chooseImage());

        panel.add(imagePreview, BorderLayout.CENTER);
        panel.add(btnUpload, BorderLayout.SOUTH);

        return panel;
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                        "Image Files", "jpg","jpeg","png"
                )
        );

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedImage = chooser.getSelectedFile();

            ImageIcon icon = new ImageIcon(selectedImage.getAbsolutePath());
            Image scaled = icon.getImage().getScaledInstance(
                    220,150,Image.SCALE_SMOOTH
            );

            imagePreview.setText("");
            imagePreview.setIcon(new ImageIcon(scaled));
        }
    }

    // ================= ACTION =================
    private JPanel actionButton() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnSave = new JButton("Simpan Data");
        btnSave.setBackground(new Color(14,64,160));
        btnSave.setForeground(Color.WHITE);

        btnSave.addActionListener(e -> saveData());

        panel.add(btnSave);
        return panel;
    }

    private void saveData() {

        if (txtNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Nama peralatan wajib diisi"
            );
            return;
        }

        String fotoPath = selectedImage == null
                ? ""
                : selectedImage.getAbsolutePath();

        String tanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                .format(LocalDate.now());

        // 🔥 ID OTOMATIS DI CONTROLLER
        UserLaporanController.tambahLaporan(
                txtNama.getText().trim(),
                cbJenis.getSelectedItem().toString(),
                cbLokasi.getSelectedItem().toString(),
                cbKerusakan.getSelectedItem().toString(),
                txtDeskripsi.getText().trim(),
                cbStatus.getSelectedItem().toString(),
                tanggal,
                fotoPath
        );

        JOptionPane.showMessageDialog(
                this,
                "Data berhasil disimpan",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE
        );

        parent.showPage("peralatan");
    }

    // ================= HELPERS =================
    private JPanel field(String label, Component input) {
        JPanel panel = new JPanel(new BorderLayout(8,6));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(input, BorderLayout.CENTER);
        return panel;
    }

    private JComboBox<String> combo(String... items) {
        return new JComboBox<>(items);
    }
}
