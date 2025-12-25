package view.user.riwayat;

import controller.UserLaporanController;
import model.LaporanBarang;
import util.UIStyleUser;
import view.user.UserFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * RIWAYAT LAPORAN PANEL
 *
 * ===============================
 * MODE SEKARANG:
 * ✔ Dummy Memory (UserLaporanController)
 *
 * MODE NANTI (MySQL):
 * ❗ Controller diganti SELECT
 * ❗ File ini TIDAK DIUBAH
 */
public class RiwayatLaporanPanel extends JPanel {

    private final UserFrame frame;
    private TableRowSorter<DefaultTableModel> sorter;
    private DefaultTableModel model;

    // 🔹 Formatter tanggal agar lebih rapi (bukan 2025-12-20)
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RiwayatLaporanPanel(UserFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
        setBackground(UIStyleUser.BG);

        add(card());
        loadData(); // load pertama kali
    }

    /* ================= CARD ================= */

    private JComponent card() {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(1100, 600));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UIStyleUser.BORDER, 1, true),
                new EmptyBorder(24, 28, 28, 28)
        ));

        card.add(header(), BorderLayout.NORTH);
        card.add(tableSection(), BorderLayout.CENTER);

        return card;
    }

    /* ================= HEADER ================= */

    private JComponent header() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(0, 0, 16, 0));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel title = new JLabel("Riwayat Laporan Saya");
        title.setFont(UIStyleUser.H1);
        title.setForeground(UIStyleUser.PRIMARY);

        JButton back = new JButton("← Kembali");
        back.addActionListener(e -> frame.showDashboard());

        top.add(title, BorderLayout.WEST);
        top.add(back, BorderLayout.EAST);

        JPanel filter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        filter.setOpaque(false);

        JComboBox<String> statusFilter = new JComboBox<>(new String[]{
                "Semua Status", "Selesai", "Perbaikan", "Rusak"
        });

        JTextField searchField = new JTextField(18);
        searchField.setToolTipText("Cari ID atau Nama Peralatan");

        filter.add(new JLabel("Status"));
        filter.add(statusFilter);
        filter.add(new JLabel("Cari"));
        filter.add(searchField);

        wrapper.add(top, BorderLayout.NORTH);
        wrapper.add(filter, BorderLayout.CENTER);
        wrapper.add(new JSeparator(), BorderLayout.SOUTH);

        setupFilter(statusFilter, searchField);

        return wrapper;
    }

    /* ================= TABLE ================= */

    private JComponent tableSection() {

        String[] columns = {
                "ID Laporan",
                "Nama Peralatan",
                "Lokasi",
                "Status",
                "Tanggal"
        };

        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(46);
        table.setFont(UIStyleUser.BODY);
        table.setGridColor(UIStyleUser.BORDER);
        table.setShowVerticalLines(false);

        // 🔹 Warna seleksi baris agar tidak bentrok dengan status badge
        table.setSelectionBackground(new Color(220, 235, 255));

        JTableHeader header = table.getTableHeader();
        header.setFont(UIStyleUser.BODY_BOLD);
        header.setBackground(new Color(235, 238, 242));
        header.setBorder(new LineBorder(UIStyleUser.BORDER));

        /* ===== PENGATURAN LEBAR KOLOM =====
         * Supaya tabel rapi dan proporsional
         */
        TableColumnModel cm = table.getColumnModel();
        cm.getColumn(0).setPreferredWidth(120); // ID
        cm.getColumn(1).setPreferredWidth(280); // Nama Peralatan
        cm.getColumn(2).setPreferredWidth(100); // Lokasi
        cm.getColumn(3).setPreferredWidth(120); // Status
        cm.getColumn(4).setPreferredWidth(120); // Tanggal

        /* ===== RATA TENGAH UNTUK DATA TERTENTU ===== */
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        cm.getColumn(0).setCellRenderer(center); // ID
        cm.getColumn(2).setCellRenderer(center); // Lokasi
        cm.getColumn(4).setCellRenderer(center); // Tanggal

        /* ===== STATUS BADGE ===== */
        cm.getColumn(3).setCellRenderer(new StatusRenderer());

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(UIStyleUser.BORDER));

        return scroll;
    }

    /* ================= DATA ================= */

    private void loadData() {
        model.setRowCount(0);

        for (LaporanBarang l : UserLaporanController.getRiwayatLaporan()) {
            model.addRow(new Object[]{
                    l.getIdLaporan(),
                    l.getNamaBarang(),
                    l.getLokasi(),
                    l.getStatus(),
                    l.getTanggal() // STRING, AMAN
            });
        }
    }

    /**
     * 🔥 DIPANGGIL DARI UserFrame
     * Setiap kali halaman Riwayat ditampilkan
     */
    public void reload() {
        loadData();
    }

    /* ================= FILTER ================= */

    private void setupFilter(JComboBox<String> status, JTextField search) {

        status.addActionListener(e -> applyFilter(status, search));

        search.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { applyFilter(status, search); }
            public void removeUpdate(DocumentEvent e) { applyFilter(status, search); }
            public void changedUpdate(DocumentEvent e) { applyFilter(status, search); }
        });
    }

    private void applyFilter(JComboBox<String> status, JTextField search) {

        RowFilter<DefaultTableModel, Object> rf = new RowFilter<>() {
            public boolean include(Entry<? extends DefaultTableModel, ?> e) {

                String selectedStatus = status.getSelectedItem().toString();
                String query = search.getText().toLowerCase();

                boolean statusMatch = selectedStatus.equals("Semua Status")
                        || e.getStringValue(3).equalsIgnoreCase(selectedStatus);

                boolean searchMatch =
                        e.getStringValue(0).toLowerCase().contains(query)
                                || e.getStringValue(1).toLowerCase().contains(query);

                return statusMatch && searchMatch;
            }
        };

        sorter.setRowFilter(rf);
    }

    /* ================= STATUS BADGE ================= */

    private static class StatusRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            JLabel label = new JLabel(value.toString(), SwingConstants.CENTER);
            label.setOpaque(true);
            label.setFont(UIStyleUser.SMALL_BOLD);
            label.setBorder(new EmptyBorder(6, 10, 6, 10));

            // 🔹 Jika baris dipilih, ikuti warna selection
            if (isSelected) {
                label.setBackground(new Color(220, 235, 255));
                label.setForeground(UIStyleUser.PRIMARY);
                return label;
            }

            switch (value.toString()) {
                case "Normal":
                    label.setBackground(new Color(220, 252, 231));
                    label.setForeground(new Color(22, 163, 74));
                    break;
                case "Perbaikan":
                    label.setBackground(new Color(254, 249, 195));
                    label.setForeground(new Color(202, 138, 4));
                    break;
                default:
                    label.setBackground(new Color(254, 226, 226));
                    label.setForeground(new Color(220, 38, 38));
                    break;
            }

            return label;
        }
    }
}
