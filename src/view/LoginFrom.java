package view;

import javax.swing.*;
import java.awt.*;

public class LoginFrom extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;

    public LoginFrom() {
        // 1. Setting Jendela
        setTitle("Login Penjual - SIPAO");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        // 2. Tambah Komponen
        add(new JLabel("  Username:"));
        txtUser = new JTextField();
        add(txtUser);

        add(new JLabel("  Password:"));
        txtPass = new JPasswordField();
        add(txtPass);

        btnLogin = new JButton("Login");
        add(new JLabel("")); // Spasi kosong
        add(btnLogin);

        // 3. Logika Tombol Login
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            // Jika user & pass benar, buka FormAlatManual
            if (user.equals("Nur Ilyas") && pass.equals("ilyas123")) {
                JOptionPane.showMessageDialog(null, "Login Berhasil!");
                new FormAlatManual().setVisible(true); 
                this.dispose(); // Tutup form login
            } else {
                JOptionPane.showMessageDialog(null, "Username/Password Salah!");
            }
        });
    }

    public static void main(String[] args) {
        new LoginFrom().setVisible(true);
    }
}