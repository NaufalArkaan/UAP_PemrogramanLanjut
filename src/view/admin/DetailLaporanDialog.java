package view.admin;

import controller.UserLaporanController;
import model.LaporanBarang;

import javax.swing.*;
import java.awt.*;

public class DetailLaporanDialog extends JDialog {

    private final LaporanBarang laporan;

    public DetailLaporanDialog(Window parent, String reportId) {
        super(parent, "Detail Laporan", ModalityType.APPLICATION_MODAL);

        laporan = UserLaporanController.getById(reportId);
        if (laporan == null) {
            JOptionPane.showMessageDialog(parent, "Data tidak ditemukan");
            dispose();
            return;
        }

        setSize(800, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(info(), BorderLayout.CENTER);
    }

    private JPanel info() {
        JPanel p = new JPanel(new GridLayout(0,2,8,8));
        p.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        p.add(new JLabel("ID"));        p.add(new JLabel(laporan.getIdLaporan()));
        p.add(new JLabel("Nama"));      p.add(new JLabel(laporan.getNamaBarang()));
        p.add(new JLabel("Jenis"));     p.add(new JLabel(laporan.getJenis()));
        p.add(new JLabel("Lokasi"));    p.add(new JLabel(laporan.getLokasi()));
        p.add(new JLabel("Kerusakan")); p.add(new JLabel(laporan.getKerusakan()));
        p.add(new JLabel("Deskripsi")); p.add(new JLabel(laporan.getDeskripsi()));
        p.add(new JLabel("Status"));    p.add(new JLabel(laporan.getStatus()));
        p.add(new JLabel("Tanggal"));   p.add(new JLabel(laporan.getTanggal()));

        return p;
    }
}
