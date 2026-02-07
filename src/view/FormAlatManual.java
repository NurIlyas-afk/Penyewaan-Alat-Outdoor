package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import config.Koneksi;

public class FormAlatManual extends JFrame {
    // Tambahkan txtPembeli di sini
    private JTextField txtNama, txtPembeli, txtKategori, txtHarga, txtStok;
    private JButton btnSimpan, btnHapus, btnClear;
    private JTable tabelAlat;
    private DefaultTableModel tableModel;

    public FormAlatManual() {
        setTitle("SIPAO - Sistem Penyewaan Alat Outdoor");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Input dengan 6 baris (tambah 1 untuk pembeli)
        JPanel panelInput = new JPanel(new GridLayout(6, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelInput.add(new JLabel("Nama Alat:"));
        txtNama = new JTextField();
        panelInput.add(txtNama);

        // Input Baru: Nama Pembeli
        panelInput.add(new JLabel("Nama Pembeli/Penyewa:"));
        txtPembeli = new JTextField();
        panelInput.add(txtPembeli);

        panelInput.add(new JLabel("Kategori:"));
        txtKategori = new JTextField();
        panelInput.add(txtKategori);

        panelInput.add(new JLabel("Harga Sewa:"));
        txtHarga = new JTextField();
        panelInput.add(txtHarga);

        panelInput.add(new JLabel("Stok:"));
        txtStok = new JTextField();
        panelInput.add(txtStok);

        btnSimpan = new JButton("Simpan Data");
        panelInput.add(btnSimpan);
        
        btnClear = new JButton("Kosongkan Form");
        panelInput.add(btnClear);

        add(panelInput, BorderLayout.NORTH);

        // Update Tabel: Tambah kolom Nama Pembeli
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama Alat", "Pembeli", "Kategori", "Harga", "Stok"}, 0);
        tabelAlat = new JTable(tableModel);
        add(new JScrollPane(tabelAlat), BorderLayout.CENTER);

        JPanel panelBawah = new JPanel();
        btnHapus = new JButton("Hapus Data Terpilih");
        btnHapus.setBackground(Color.RED);
        btnHapus.setForeground(Color.WHITE);
        panelBawah.add(btnHapus);
        add(panelBawah, BorderLayout.SOUTH);

        btnSimpan.addActionListener(e -> simpanData());
        btnHapus.addActionListener(e -> hapusData());
        btnClear.addActionListener(e -> {
            txtNama.setText(""); txtPembeli.setText(""); txtKategori.setText(""); txtHarga.setText(""); txtStok.setText("");
        });

        tampilkanData();
    }

    private void tampilkanData() {
        tableModel.setRowCount(0);
        try {
            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tabel_alat");
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id_alat"),
                    rs.getString("nama_alat"),
                    rs.getString("nama_pembeli"), // Ambil dari database
                    rs.getString("kategori"),
                    rs.getString("harga_sewa"),
                    rs.getString("stok")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal ambil data: " + e.getMessage());
        }
    }

    private void simpanData() {
        try {
            // Query SQL ditambah nama_pembeli
            String sql = "INSERT INTO tabel_alat (nama_alat, nama_pembeli, kategori, harga_sewa, stok) VALUES (?, ?, ?, ?, ?)";
            Connection conn = Koneksi.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtNama.getText());
            pst.setString(2, txtPembeli.getText()); // Input pembeli
            pst.setString(3, txtKategori.getText());
            pst.setString(4, txtHarga.getText());
            pst.setString(5, txtStok.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil Simpan!");
            tampilkanData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal Simpan: " + e.getMessage());
        }
    }

    private void hapusData() {
        int baris = tabelAlat.getSelectedRow();
        if (baris != -1) {
            String id = tableModel.getValueAt(baris, 0).toString();
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM tabel_alat WHERE id_alat=?");
                pst.setString(1, id);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Terhapus!");
                tampilkanData();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel dulu!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormAlatManual().setVisible(true));
    }
}