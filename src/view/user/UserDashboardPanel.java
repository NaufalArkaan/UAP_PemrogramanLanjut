package view.user;

import controller.UserLaporanController;
import model.SummaryData;
import model.User;
import util.UIStyleUser;
import view.auth.LoginFrame;
import view.user.components.ActionCard;
import view.user.components.SummaryCard;
import view.user.components.AnimatedBackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class UserDashboardPanel extends JPanel {

    private final UserFrame frame;
    private final User user;

    // SUMMARY CARD REFERENCE
    private SummaryCard totalCard;
    private SummaryCard processCard;
    private SummaryCard doneCard;

    public UserDashboardPanel(UserFrame frame, User user) {
        this.frame = frame;
        this.user = user;

        setLayout(new BorderLayout());

        AnimatedBackgroundPanel background = new AnimatedBackgroundPanel();
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));

        add(background, BorderLayout.CENTER);

        buildUI(background);
        refreshSummary();
    }

    /* ================= UI ================= */

    private void buildUI(JPanel container) {
        container.add(header()); // ⬅️ header sudah ada logout
        container.add(Box.createVerticalStrut(24));
        container.add(infoBox());
        container.add(Box.createVerticalStrut(28));
        container.add(summarySection());
        container.add(Box.createVerticalStrut(36));
        container.add(actionCards());
        container.add(Box.createVerticalStrut(16));
        container.add(hintText());
    }

    /* ================= HEADER ================= */

    private JComponent header() {

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIStyleUser.BORDER),
                BorderFactory.createEmptyBorder(28, 28, 28, 28)
        ));
        header.setAlignmentX(LEFT_ALIGNMENT);

        // ===== LEFT (TITLE) =====
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);

        JLabel title = new JLabel("Monitoring Kerusakan Laboratorium");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(30, 40, 70));

        JLabel tagline = new JLabel(
                "Pelaporan dan pemantauan kondisi peralatan laboratorium secara terpusat"
        );
        tagline.setFont(UIStyleUser.SMALL);
        tagline.setForeground(UIStyleUser.TEXT_MUTED);

        left.add(title);
        left.add(Box.createVerticalStrut(10));
        left.add(tagline);

        // ===== RIGHT (LOGOUT) =====
        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(UIStyleUser.BODY_BOLD);
        btnLogout.setBackground(UIStyleUser.DANGER);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Apakah Anda yakin ingin logout?",
                    "Konfirmasi Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                new LoginFrame();
            }
        });

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(btnLogout);

        header.add(left, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        return header;
    }

    /* ================= INFO BOX ================= */

    private JComponent infoBox() {

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UIStyleUser.INFO_BG);
        info.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, UIStyleUser.PRIMARY),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        info.setAlignmentX(LEFT_ALIGNMENT);

        JLabel title = new JLabel("<html><b>Informasi Penggunaan Sistem</b></html>");
        title.setFont(UIStyleUser.BODY_BOLD);

        JLabel desc = new JLabel(
                "Gunakan menu di bawah untuk melaporkan kerusakan atau melihat riwayat laporan Anda."
        );
        desc.setFont(UIStyleUser.BODY);

        info.add(title);
        info.add(Box.createVerticalStrut(6));
        info.add(desc);

        return info;
    }

    /* ================= SUMMARY ================= */

    private JComponent summarySection() {

        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        panel.setAlignmentX(LEFT_ALIGNMENT);

        totalCard = new SummaryCard("Total Laporan", "0");
        processCard = new SummaryCard("Sedang Diproses", "0");
        doneCard = new SummaryCard("Selesai", "0");

        panel.add(totalCard);
        panel.add(processCard);
        panel.add(doneCard);

        return panel;
    }

    /* ================= ACTION CARDS ================= */

    private JComponent actionCards() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 32, 0));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setOpaque(false);

        ActionCard laporCard = new ActionCard(
                "Laporkan Kerusakan",
                "Buat laporan kerusakan baru",
                UIStyleUser.DANGER
        );

        ActionCard riwayatCard = new ActionCard(
                "Riwayat Laporan",
                "Lihat laporan yang sudah dibuat",
                UIStyleUser.PRIMARY
        );

        bindAction(laporCard, riwayatCard);

        panel.add(laporCard);
        panel.add(riwayatCard);

        return panel;
    }

    /* ================= HINT ================= */

    private JComponent hintText() {

        JLabel hint = new JLabel(
                "Klik salah satu menu di atas untuk memulai pelaporan atau melihat riwayat laporan."
        );
        hint.setFont(UIStyleUser.SMALL);
        hint.setForeground(UIStyleUser.TEXT_MUTED);
        hint.setAlignmentX(LEFT_ALIGNMENT);

        return hint;
    }

    /* ================= ACTION ================= */

    private void bindAction(ActionCard lapor, ActionCard riwayat) {

        lapor.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.showFormLaporan();
            }
        });

        riwayat.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.showRiwayatLaporan();
            }
        });
    }

    /* ================= REFRESH SUMMARY ================= */

    public void refreshSummary() {

        SummaryData data = UserLaporanController.getSummary();

        totalCard.setValue(String.valueOf(data.getTotal()));
        processCard.setValue(String.valueOf(data.getProcessing()));
        doneCard.setValue(String.valueOf(data.getDone()));
    }
}
