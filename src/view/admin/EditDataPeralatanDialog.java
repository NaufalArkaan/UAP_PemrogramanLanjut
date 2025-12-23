package view.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EditDataPeralatanDialog extends JDialog {

    private final JTable table;
    private final int row;

    private JTextField txtNama;
    private JComboBox<String> cbJenis, cbLokasi, cbStatus;

    public EditDataPeralatanDialog(Window parent, JTable table, int row) {
        super(parent, "Edit Data Peralatan", ModalityType.APPLICATION_MODAL);
        this.table = table;
        this.row = row;

        setSize(420, 360);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(12,12));

        add(header(), BorderLayout.NORTH);
        add(form(), BorderLayout.CENTER);
        add(action(), BorderLayout.SOUTH);

        loadData();
    }

    // ================= HEADER =================
    private JPanel header() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10,12,10,12));

        JLabel title = new JLabel("Edit Data Peralatan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));

        panel.add(title, BorderLayout.WEST);
        return panel;
    }

    // ================= FORM =================
    private JPanel form() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10,12,10,12));

        txtNama = new JTextField();

        cbJenis = new JComboBox<>(new String[]{
                "Komputer","Monitor","Proyektor","Keyboard","Mouse","Printer"
        });

        cbLokasi = new JComboBox<>(new String[]{
                "Lab 1","Lab 2","Lab 3","Lab Multimedia"
        });

        cbStatus = new JComboBox<>(new String[]{
                "Rusak","Perbaikan","Normal"
        });

        panel.add(field("Nama Peralatan", txtNama));
        panel.add(field("Jenis", cbJenis));
        panel.add(field("Lokasi", cbLokasi));
        panel.add(field("Status", cbStatus));

        return panel;
    }

    private JPanel field(String label, JComponent input) {
        JPanel panel = new JPanel(new BorderLayout(6,6));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(input, BorderLayout.CENTER);

        return panel;
    }

    // ================= ACTION =================
    private JPanel action() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBorder(new EmptyBorder(8,12,8,12));

        JButton btnSave = new JButton("Simpan");
        JButton btnCancel = new JButton("Batal");

        btnSave.setBackground(new Color(14,64,160));
        btnSave.setForeground(Color.WHITE);

        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());

        panel.add(btnCancel);
        panel.add(btnSave);

        return panel;
    }

    // ================= LOAD & SAVE =================
    private void loadData() {
        txtNama.setText(table.getValueAt(row, 1).toString());
        cbJenis.setSelectedItem(table.getValueAt(row, 2));
        cbLokasi.setSelectedItem(table.getValueAt(row, 3));
        cbStatus.setSelectedItem(table.getValueAt(row, 5));
    }

    private void save() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setValueAt(txtNama.getText(), row, 1);
        model.setValueAt(cbJenis.getSelectedItem(), row, 2);
        model.setValueAt(cbLokasi.getSelectedItem(), row, 3);
        model.setValueAt(cbStatus.getSelectedItem(), row, 5);

        JOptionPane.showMessageDialog(
                this,
                "Data berhasil diperbarui",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }
}
