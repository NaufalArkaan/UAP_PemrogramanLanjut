package view.admin;

import controller.UserLaporanController;
import model.LaporanBarang;
import view.components.ActionCellEditor;
import view.components.ActionCellRenderer;
import view.components.StatusCellRenderer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DataPeralatanPanel extends JPanel {

    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    /* ================= PAGINATION ================= */
    private int currentPage = 1;
    private final int pageSize = 15;
    private JLabel lblPage;

    /* ================= DATA ================= */
    private final List<Object[]> masterData = new ArrayList<>();
    private final List<Object[]> filteredData = new ArrayList<>();

    /* ================= SORTING ================= */
    private int sortColumn = -1;
    private boolean ascending = true;

    private final String[] headers = {
            "ID", "Nama", "Jenis", "Lokasi",
            "Kerusakan", "Status", "Tanggal", "Aksi"
    };

    /* ================= CONSTRUCTOR ================= */

    public DataPeralatanPanel() {
        setLayout(new BorderLayout(0, 12));
        setBackground(new Color(245, 246, 250));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // ⚠️ UI dulu
        add(createHeader(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
        add(createPagination(), BorderLayout.SOUTH);

        // 🔥 load CSV
        reloadData();
    }

    /* ================= LOAD / RELOAD ================= */

    public final void reloadData() {
        masterData.clear();

        for (LaporanBarang l : UserLaporanController.getAllLaporan()) {
            masterData.add(new Object[]{
                    l.getIdLaporan(),
                    l.getNamaBarang(),
                    l.getJenis(),
                    l.getLokasi(),
                    l.getKerusakan(),
                    l.getStatus(),
                    l.getTanggal()
            });
        }

        applyFilter();
    }

    /* ================= HEADER ================= */

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(0, 10));
        header.setOpaque(false);

        JLabel title = new JLabel("Data Peralatan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel subtitle = new JLabel("Kelola data peralatan laboratorium");
        subtitle.setForeground(Color.GRAY);

        JPanel titleWrap = new JPanel(new GridLayout(2, 1));
        titleWrap.setOpaque(false);
        titleWrap.add(title);
        titleWrap.add(subtitle);

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(260, 36));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        txtSearch.setToolTipText("Cari peralatan...");

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { applyFilter(); }
            public void removeUpdate(DocumentEvent e) { applyFilter(); }
            public void changedUpdate(DocumentEvent e) { applyFilter(); }
        });

        // ===== TOMBOL TAMBAH =====
        JButton btnTambah = new JButton("+ Tambah Data");
        btnTambah.setPreferredSize(new Dimension(150, 36));
        btnTambah.setBackground(new Color(14, 64, 160));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFocusPainted(false);
        btnTambah.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnTambah.addActionListener(e -> {
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w instanceof AdminFrame frame) {
                frame.openTambahData(); // dialog admin tambah
                reloadData();           // refresh CSV
            }
        });

        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionBar.setOpaque(false);
        actionBar.add(txtSearch);
        actionBar.add(btnTambah);

        header.add(titleWrap, BorderLayout.NORTH);
        header.add(actionBar, BorderLayout.SOUTH);

        return header;
    }

    /* ================= TABLE ================= */

    private JScrollPane createTable() {

        tableModel = new DefaultTableModel(headers, 0) {
            public boolean isCellEditable(int r, int c) {
                return c == 7;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(36);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);

        TableColumn statusCol = table.getColumn("Status");
        statusCol.setPreferredWidth(120);
        statusCol.setCellRenderer(new StatusCellRenderer());

        TableColumn aksiCol = table.getColumn("Aksi");
        aksiCol.setPreferredWidth(220);
        aksiCol.setCellRenderer(new ActionCellRenderer());
        aksiCol.setCellEditor(new ActionCellEditor(table));

        table.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int col = table.columnAtPoint(e.getPoint());
                if (col == 7) return;

                if (sortColumn == col) ascending = !ascending;
                else {
                    sortColumn = col;
                    ascending = true;
                }

                applySorting();
                refreshHeader();
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        return sp;
    }

    /* ================= SORTING ================= */

    private void applySorting() {
        if (sortColumn < 0) return;

        filteredData.sort((a, b) -> {
            int res = a[sortColumn].toString()
                    .compareToIgnoreCase(b[sortColumn].toString());
            return ascending ? res : -res;
        });

        currentPage = 1;
        loadPage();
    }

    private void refreshHeader() {
        TableColumnModel cm = table.getColumnModel();
        for (int i = 0; i < headers.length; i++) {
            cm.getColumn(i).setHeaderValue(
                    i == sortColumn
                            ? headers[i] + (ascending ? " ▲" : " ▼")
                            : headers[i]
            );
        }
        table.getTableHeader().repaint();
    }

    /* ================= PAGINATION ================= */

    private JPanel createPagination() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);

        JButton prev = new JButton("Previous");
        JButton next = new JButton("Next");
        lblPage = new JLabel();

        prev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadPage();
            }
        });

        next.addActionListener(e -> {
            if (currentPage < totalPage()) {
                currentPage++;
                loadPage();
            }
        });

        panel.add(prev);
        panel.add(lblPage);
        panel.add(next);
        return panel;
    }

    private void loadPage() {
        tableModel.setRowCount(0);

        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, filteredData.size());

        for (int i = start; i < end; i++) {
            Object[] r = filteredData.get(i);
            tableModel.addRow(new Object[]{
                    r[0], r[1], r[2], r[3],
                    r[4], r[5], r[6], ""
            });
        }

        lblPage.setText("Page " + currentPage + " / " + totalPage());
    }

    private int totalPage() {
        return Math.max(1,
                (int) Math.ceil((double) filteredData.size() / pageSize));
    }

    /* ================= SEARCH ================= */

    private void applyFilter() {
        String key = txtSearch == null ? "" : txtSearch.getText().toLowerCase();
        filteredData.clear();

        for (Object[] row : masterData) {
            StringBuilder sb = new StringBuilder();
            for (Object c : row) sb.append(c.toString().toLowerCase()).append(" ");
            if (sb.toString().contains(key)) {
                filteredData.add(row);
            }
        }

        currentPage = 1;
        applySorting();
        loadPage();
    }
}
