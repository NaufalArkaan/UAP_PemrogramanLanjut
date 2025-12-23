package view.admin;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;

public class TambahDataPeralatanPanel extends JPanel {

    private final AdminFrame parent;
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
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

        card.add(field("ID Peralatan", new JTextField()));
        card.add(field("Nama Peralatan", new JTextField()));
        card.add(field("Jenis Peralatan", combo("Komputer","Monitor","Proyektor","Keyboard","Mouse","Printer")));
        card.add(field("Lokasi", combo("Lab 1","Lab 2","Lab 3","Lab Multimedia")));
        card.add(field("Jenis Kerusakan", combo("Hardware","Software")));
        card.add(field("Status", combo("Rusak","Dalam Perbaikan","Normal")));

        JTextArea deskripsi = new JTextArea(4,20);
        deskripsi.setLineWrap(true);
        deskripsi.setWrapStyleWord(true);
        deskripsi.setBorder(new LineBorder(new Color(200,200,200)));
        card.add(field("Deskripsi Kerusakan", new JScrollPane(deskripsi)));

        card.add(Box.createVerticalStrut(12));
        card.add(imageSection());
        card.add(Box.createVerticalStrut(16));
        card.add(actionButton());

        return card;
    }

    // ================= IMAGE UPLOAD =================
    private JPanel imageSection() {
        JPanel panel = new JPanel(new BorderLayout(8,8));
        panel.setOpaque(false);
        panel.setBorder(new TitledBorder("Foto Kerusakan"));

        imagePreview = new JLabel("Belum ada gambar", SwingConstants.CENTER);
        imagePreview.setPreferredSize(new Dimension(200,150));
        imagePreview.setBorder(new LineBorder(new Color(200,200,200)));
        imagePreview.setOpaque(true);
        imagePreview.setBackground(new Color(245,245,245));

        JButton btnUpload = new JButton("Pilih Gambar");
        btnUpload.setFocusPainted(false);
        btnUpload.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnUpload.addActionListener(e -> chooseImage());

        panel.add(imagePreview, BorderLayout.CENTER);
        panel.add(btnUpload, BorderLayout.SOUTH);

        return panel;
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png"
        ));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedImage = chooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(selectedImage.getAbsolutePath());

            Image scaled = icon.getImage().getScaledInstance(
                    imagePreview.getWidth(),
                    imagePreview.getHeight(),
                    Image.SCALE_SMOOTH
            );

            imagePreview.setText("");
            imagePreview.setIcon(new ImageIcon(scaled));
        }
    }

    // ================= FIELD =================
    private JPanel field(String label, Component input) {
        JPanel panel = new JPanel(new BorderLayout(8,6));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(input, BorderLayout.CENTER);

        return panel;
    }

    private JComboBox<String> combo(String... items) {
        return new JComboBox<>(items);
    }

    // ================= ACTION =================
    private JPanel actionButton() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);

        JButton btnSave = new JButton("Simpan Data");
        btnSave.setBackground(new Color(14,64,160));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setBorder(new EmptyBorder(8,20,8,20));

        btnSave.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Data berhasil disimpan (UI saja)",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        panel.add(btnSave);
        return panel;
    }
}
