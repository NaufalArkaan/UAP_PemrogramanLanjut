package view.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LaporanFormDialog extends JDialog {

    public LaporanFormDialog(JFrame parent, DefaultTableModel model) {
        setTitle("Tambah Laporan Kerusakan");
        setSize(420, 420);
        setLocationRelativeTo(parent);
        setModal(true);
        setLayout(new GridLayout(0,2,8,8));

        JTextField txtNama = new JTextField();
        JTextField txtJenis = new JTextField();
        JTextField txtLokasi = new JTextField();
        JTextField txtKerusakan = new JTextField();
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Rusak","Perbaikan","Normal"});

        add(new JLabel("Nama Peralatan"));
        add(txtNama);
        add(new JLabel("Jenis"));
        add(txtJenis);
        add(new JLabel("Lokasi"));
        add(txtLokasi);
        add(new JLabel("Jenis Kerusakan"));
        add(txtKerusakan);
        add(new JLabel("Status"));
        add(cbStatus);

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.addActionListener(e -> {
            model.addRow(new Object[]{
                    "#LPR-" + String.format("%03d", model.getRowCount()+1),
                    txtNama.getText(),
                    txtJenis.getText(),
                    txtLokasi.getText(),
                    txtKerusakan.getText(),
                    cbStatus.getSelectedItem(),
                    "Hari Ini",
                    ""
            });
            dispose();
        });

        add(new JLabel());
        add(btnSimpan);

        setVisible(true);
    }
}
