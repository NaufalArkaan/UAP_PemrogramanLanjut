package view.admin;

import javax.swing.*;
import java.awt.*;

public class ReportFormDialog extends JDialog {

    public ReportFormDialog(JTable table, int row) {
        setTitle("Edit Laporan");
        setSize(420, 360);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("Form Edit Laporan (sementara)");
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbl, BorderLayout.CENTER);

        JButton btnClose = new JButton("Tutup");
        btnClose.addActionListener(e -> dispose());
        add(btnClose, BorderLayout.SOUTH);

        setVisible(true);
    }
}
