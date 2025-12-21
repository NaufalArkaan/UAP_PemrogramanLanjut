package view.admin;

import javax.swing.*;
import java.awt.*;

public class ReportViewDialog extends JDialog {

    public ReportViewDialog(JTable table, int row) {
        setTitle("Detail Laporan");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0,2,8,8));

        String[] labels = {
                "ID","Peralatan","Jenis","Lokasi",
                "Kerusakan","Status","Tanggal"
        };

        for (int i = 0; i < labels.length; i++) {
            add(new JLabel(labels[i]));
            add(new JLabel(table.getValueAt(row, i).toString()));
        }

        setModal(true);
        setVisible(true);
    }
}
