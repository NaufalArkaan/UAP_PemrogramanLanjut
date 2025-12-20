package view.auth;

import controller.AuthController;
import model.User;
import util.UIStyle;
import view.admin.AdminFrame;
import view.components.ShadowPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setTitle("Login - Sistem Monitoring Lab");
        // ukuran frame dibuat agak lebih tinggi supaya isi kartu tidak terpotong
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 246, 250));
        setResizable(false);

        // ===== CARD =====
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        // Lebih lebar & lebih tinggi supaya subtitle tidak membungkus per kata dan hint terlihat
        card.setPreferredSize(new Dimension(400, 540));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(28, 32, 28, 32)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.NONE;

        // ===== LOGO (centered, cukup ruang) =====
        JLabel logo = new JLabel("■");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 40));
        logo.setForeground(UIStyle.PRIMARY);
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(logo, gbc);

        // ===== TITLE (center) =====
        JLabel title = new JLabel("Sistem Monitoring");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(title, gbc);

        // ===== SUBTITLE (center, tetap 2 baris max) =====
        // gunakan HTML dengan satu break, plus beri preferredSize agar label punya cukup lebar
        JLabel subtitle = new JLabel("<html><center>Kerusakan Peralatan<br>Laboratorium Informatika</center></html>");
        subtitle.setFont(UIStyle.NORMAL);
        subtitle.setForeground(Color.GRAY);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        // pastikan label cukup lebar agar teks tidak terpecah jadi banyak baris
        subtitle.setPreferredSize(new Dimension(320, 44));
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(subtitle, gbc);

        // spacer
        gbc.gridy = 3;
        card.add(Box.createVerticalStrut(12), gbc);

        // ukuran field
        Dimension fieldSize = new Dimension(300, 36);
        Dimension btnSize = new Dimension(300, 42);

        // ===== LABEL Username (LEFT) =====
        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(UIStyle.SMALL);
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST; // left align
        card.add(lblUser, gbc);

        // ===== FIELD Username =====
        txtUsername = new JTextField();
        txtUsername.setPreferredSize(fieldSize);
        txtUsername.setMinimumSize(fieldSize);
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        card.add(txtUsername, gbc);
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        // small gap
        gbc.gridy = 6;
        card.add(Box.createVerticalStrut(6), gbc);

        // ===== LABEL Password (LEFT) =====
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(UIStyle.SMALL);
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        card.add(lblPass, gbc);

        // ===== FIELD Password =====
        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(fieldSize);
        txtPassword.setMinimumSize(fieldSize);
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        card.add(txtPassword, gbc);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        // gap before button
        gbc.gridy = 9;
        card.add(Box.createVerticalStrut(18), gbc);

        // ===== BUTTON (CENTER) =====
        JButton btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(btnSize);
        btnLogin.setBackground(UIStyle.PRIMARY);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        Color normal = UIStyle.PRIMARY;
        Color hover = UIStyle.PRIMARY.darker();

        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(normal);
            }
        });

        btnLogin.addActionListener(e -> login());
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(btnLogin, gbc);

        // hint text (centered)
        JLabel hint = new JLabel("Hint: Username \"admin\" untuk Admin");
        hint.setFont(UIStyle.SMALL);
        hint.setForeground(Color.GRAY);
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(Box.createVerticalStrut(10), gbc); // sedikit jarak
        card.add(hint, gbc);

        // ===== SHADOW WRAPPER =====
        ShadowPanel shadowWrapper = new ShadowPanel();
        shadowWrapper.setPreferredSize(new Dimension(420, 560));

        shadowWrapper.add(card);

        // tambahkan ke frame (tetap center)
        add(shadowWrapper);
        setVisible(true);
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi!");
            return;
        }

        User user = AuthController.login(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Login gagal!");
        } else {
            dispose();
            if ("ADMIN".equals(user.getRole())) {
                if ("ADMIN".equals(user.getRole())) {
                    new AdminFrame();
                } else {
                    JOptionPane.showMessageDialog(null, "Halaman USER belum dibuat");
                }
            }
        }
    }
}