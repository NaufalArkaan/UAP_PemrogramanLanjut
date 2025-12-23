package view.admin;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import view.components.ActionCellRenderer;
import view.components.ActionCellEditor;
import view.components.StatusCellRenderer;

public class DataPeralatanPanel extends JPanel {

    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    // ===== PAGINATION =====
    private int currentPage = 1;
    private final int pageSize = 5;
    private JLabel lblPage;

    // ===== DATA =====
    private final List<Object[]> masterData = new ArrayList<>();
    private final List<Object[]> filteredData = new ArrayList<>();

    // ===== SORTING =====
    private int sortColumn = -1;
    private boolean ascending = true;

    private final String[] headers = {
            "ID","Nama","Jenis","Lokasi","Kerusakan","Status","Tanggal","Aksi"
    };

    public DataPeralatanPanel() {
        setLayout(new BorderLayout(0, 12));
        setBackground(new Color(245, 246, 250));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        initData();

        add(createHeader(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
        add(createPagination(), BorderLayout.SOUTH);

        applyFilter();
    }

    // ================= DATA =================
    private void initData() {
        masterData.add(new Object[]{"LPR-001","Komputer PC-15","Komputer","Lab 1","Hardware","Rusak","14/12/2025"});
        masterData.add(new Object[]{"LPR-002","Monitor LCD-22","Monitor","Lab 2","Hardware","Perbaikan","13/12/2025"});
        masterData.add(new Object[]{"LPR-003","Proyektor-08","Proyektor","Lab Multimedia","Hardware","Normal","12/12/2025"});
        masterData.add(new Object[]{"LPR-004","Keyboard K-12","Keyboard","Lab 3","Hardware","Rusak","11/12/2025"});
        masterData.add(new Object[]{"LPR-005","Mouse M-35","Mouse","Lab 1","Hardware","Normal","10/12/2025"});
        masterData.add(new Object[]{"LPR-006","Printer P-02","Printer","Lab 2","Hardware","Rusak","09/12/2025"});
    }

    // ================= HEADER =================
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(0, 10));
        header.setOpaque(false);

        JLabel title = new JLabel("Data Peralatan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel subtitle = new JLabel("Kelola data peralatan laboratorium");
        subtitle.setForeground(Color.GRAY);

        JPanel titleWrap = new JPanel(new GridLayout(2,1));
        titleWrap.setOpaque(false);
        titleWrap.add(title);
        titleWrap.add(subtitle);

        JPanel actionBar = new JPanel();
        actionBar.setLayout(new BoxLayout(actionBar, BoxLayout.X_AXIS));
        actionBar.setOpaque(false);

        txtSearch = new JTextField();
        txtSearch.setMaximumSize(new Dimension(300, 36));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200)),
                BorderFactory.createEmptyBorder(6,10,6,10)
        ));
        txtSearch.setToolTipText("Cari peralatan...");

        JButton btnTambah = new JButton("+ Tambah Data");
        btnTambah.setBackground(new Color(14,64,160));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFocusPainted(false);
        btnTambah.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));

        actionBar.add(txtSearch);
        actionBar.add(Box.createHorizontalStrut(12));
        actionBar.add(btnTambah);
        btnTambah.addActionListener(e -> {
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w instanceof AdminFrame frame) {
                frame.openTambahData();
            }
        });

        header.add(titleWrap, BorderLayout.NORTH);
        header.add(actionBar, BorderLayout.SOUTH);

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { applyFilter(); }
            public void removeUpdate(DocumentEvent e) { applyFilter(); }
            public void changedUpdate(DocumentEvent e) { applyFilter(); }
        });

        return header;
    }

    // ================= TABLE =================
    private JScrollPane createTable() {

        tableModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return c == 7; // hanya kolom Aksi
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(36);
        table.setFillsViewportHeight(true);

        // ===== AKSI =====
        TableColumn aksiCol = table.getColumn("Aksi");
        aksiCol.setPreferredWidth(240);
        aksiCol.setCellRenderer(new ActionCellRenderer());
        aksiCol.setCellEditor(new ActionCellEditor(table));

        // ===== STATUS =====
        TableColumn statusCol = table.getColumn("Status");
        statusCol.setPreferredWidth(120);
        statusCol.setCellRenderer(new StatusCellRenderer());

        // ===== SORTING HEADER =====
        table.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int col = table.columnAtPoint(e.getPoint());
                if (col == 7) return; // kolom Aksi tidak sortable

                if (sortColumn == col) {
                    ascending = !ascending;
                } else {
                    sortColumn = col;
                    ascending = true;
                }

                applySorting();
                refreshHeader();
            }
        });

        return new JScrollPane(table);
    }

    // ================= SORTING =================
    private void applySorting() {
        if (sortColumn < 0) return;

        filteredData.sort((a, b) -> {
            String v1 = a[sortColumn].toString();
            String v2 = b[sortColumn].toString();

            int result = v1.compareToIgnoreCase(v2);
            return ascending ? result : -result;
        });

        currentPage = 1;
        loadPage();
    }

    private void refreshHeader() {
        TableColumnModel cm = table.getColumnModel();
        for (int i = 0; i < headers.length; i++) {
            if (i == sortColumn) {
                cm.getColumn(i).setHeaderValue(
                        headers[i] + (ascending ? " ▲" : " ▼")
                );
            } else {
                cm.getColumn(i).setHeaderValue(headers[i]);
            }
        }
        table.getTableHeader().repaint();
    }

    // ================= PAGINATION =================
    private JPanel createPagination() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);

        JButton btnPrev = new JButton("Previous");
        JButton btnNext = new JButton("Next");
        lblPage = new JLabel();

        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadPage();
            }
        });

        btnNext.addActionListener(e -> {
            if (currentPage < totalPage()) {
                currentPage++;
                loadPage();
            }
        });

        panel.add(btnPrev);
        panel.add(lblPage);
        panel.add(btnNext);

        return panel;
    }

    private void loadPage() {
        tableModel.setRowCount(0);

        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, filteredData.size());

        for (int i = start; i < end; i++) {
            Object[] r = filteredData.get(i);
            tableModel.addRow(new Object[]{
                    r[0], r[1], r[2], r[3], r[4], r[5], r[6], ""
            });
        }

        lblPage.setText("Page " + currentPage + " / " + totalPage());
    }

    private int totalPage() {
        return Math.max(1, (int) Math.ceil((double) filteredData.size() / pageSize));
    }

    // ================= SEARCH =================
    private void applyFilter() {
        String key = txtSearch.getText().toLowerCase();
        filteredData.clear();

        for (Object[] row : masterData) {
            StringBuilder sb = new StringBuilder();
            for (Object col : row) sb.append(col.toString().toLowerCase()).append(" ");
            if (sb.toString().contains(key)) filteredData.add(row);
        }

        currentPage = 1;
        applySorting();
        loadPage();
    }
}
