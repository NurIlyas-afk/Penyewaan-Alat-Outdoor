package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection mysqlconfig;
    
    public static Connection configDB() throws SQLException {
        try {
            // Jalur ke database MySQL kamu
            String url = "jdbc:mysql://localhost:3306/db_sipao"; 
            String user = "root"; 
            String pass = "ilyas123"; // Kosongkan jika kamu tidak set password di MySQL tadi
            
            // Registrasi driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            mysqlconfig = DriverManager.getConnection(url, user, pass);
            System.out.println("Koneksi Berhasil!");
            
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Koneksi Gagal: " + e.getMessage());
        }
        return mysqlconfig;
    }    
    
    // Main method untuk tes koneksi langsung
    public static void main(String[] args) {
        try {
            configDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}