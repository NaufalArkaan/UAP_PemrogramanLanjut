package view.admin;

import controller.UserLaporanController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LaporanFormDialog extends JDialog {

    public LaporanFormDialog(JFrame parent) {
        setTitle("Tambah Laporan Kerusakan");
        setSize(420, 420);
        setLocationRelativeTo(parent);
        setModal(true);
        setLayout(new GridLayout(0, 2, 8, 8));

        JTextField txtNama = new JTextField();
        JTextField txtJenis = new JTextField();
        JTextField txtLokasi = new JTextField();
        JTextField txtKerusakan = new JTextField();
        JTextArea txtDeskripsi = new JTextArea(3, 20);

        JComboBox<String> cbStatus = new JComboBox<>(
                new String[]{"Rusak", "Perbaikan", "Selesai"}
        );

        add(new JLabel("Nama Peralatan"));
        add(txtNama);

        add(new JLabel("Jenis"));
        add(txtJenis);

        add(new JLabel("Lokasi"));
        add(txtLokasi);

        add(new JLabel("Jenis Kerusakan"));
        add(txtKerusakan);

        add(new JLabel("Deskripsi"));
        add(new JScrollPane(txtDeskripsi));

        add(new JLabel("Status"));
        add(cbStatus);

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.addActionListener(e -> {

            if (txtNama.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nama peralatan wajib diisi",
                        "Validasi",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 🔥 TANGGAL OTOMATIS (ADMIN)
            String tanggal = DateTimeFormatter
                    .ofPattern("dd MMMM yyyy")
                    .format(LocalDate.now());

            // ✅ SESUAI CONTROLLER (8 PARAMETER)
            UserLaporanController.tambahLaporan(
                    txtNama.getText().trim(),            // nama
                    txtJenis.getText().trim(),           // jenis
                    txtLokasi.getText().trim(),          // lokasi
                    txtKerusakan.getText().trim(),       // kerusakan
                    txtDeskripsi.getText().trim(),       // deskripsi
                    cbStatus.getSelectedItem().toString(), // status
                    tanggal,                             // ⬅️ WAJIB
                    ""                                   // fotoPaths
            );

            dispose();
        });

        add(new JLabel());
        add(btnSimpan);

        setVisible(true);
    }
}
