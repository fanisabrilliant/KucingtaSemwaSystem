package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.Database;
import model.Produk;

public class ProdukRepository {

    public void add(Produk produk) throws SQLException {
        String sql = "INSERT INTO produk (nama, kategori, harga, stok, foto) VALUES (?, ?, ?, ?, ?)";
        try (Connection koneksi = Database.bukaKoneksi();
             PreparedStatement perintah = koneksi.prepareStatement(sql)) {
            perintah.setString(1, produk.getNama());
            perintah.setString(2, produk.getKategori());
            perintah.setLong(3, produk.getHarga());
            perintah.setInt(4, produk.getStok());
            perintah.setString(5, produk.getFoto());
            perintah.executeUpdate();
        }
    }

    // kategori null = ambil semua produk
    public ArrayList<Produk> getAll(String kategori) throws SQLException {
        ArrayList<Produk> daftar = new ArrayList<Produk>();
        String sql = "SELECT id, nama, kategori, harga, stok, foto FROM produk";
        if (kategori != null) {
            sql += " WHERE kategori = ?";
        }
        sql += " ORDER BY id";

        try (Connection koneksi = Database.bukaKoneksi();
             PreparedStatement perintah = koneksi.prepareStatement(sql)) {
            if (kategori != null) {
                perintah.setString(1, kategori);
            }
            try (ResultSet hasil = perintah.executeQuery()) {
                while (hasil.next()) {
                    daftar.add(new Produk(
                            hasil.getLong("id"),
                            hasil.getString("nama"),
                            hasil.getString("kategori"),
                            hasil.getLong("harga"),
                            hasil.getInt("stok"),
                            hasil.getString("foto")));
                }
            }
        }
        return daftar;
    }

    // Jual 1 buah: stok berkurang 1. Return false kalau stok sudah habis.
    public boolean jual(long id) throws SQLException {
        String sql = "UPDATE produk SET stok = stok - 1 WHERE id = ? AND stok > 0";
        try (Connection koneksi = Database.bukaKoneksi();
             PreparedStatement perintah = koneksi.prepareStatement(sql)) {
            perintah.setLong(1, id);
            return perintah.executeUpdate() > 0;
        }
    }

    // Ubah data produk berdasarkan id. Return false kalau id tidak ditemukan.
    public boolean update(Produk produk) throws SQLException {
        String sql = "UPDATE produk SET nama = ?, kategori = ?, harga = ?, stok = ?, foto = ? WHERE id = ?";
        try (Connection koneksi = Database.bukaKoneksi();
             PreparedStatement perintah = koneksi.prepareStatement(sql)) {
            perintah.setString(1, produk.getNama());
            perintah.setString(2, produk.getKategori());
            perintah.setLong(3, produk.getHarga());
            perintah.setInt(4, produk.getStok());
            perintah.setString(5, produk.getFoto());
            perintah.setLong(6, produk.getId());
            return perintah.executeUpdate() > 0;
        }
    }

    // Hapus produk berdasarkan id. Return false kalau id tidak ditemukan.
    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM produk WHERE id = ?";
        try (Connection koneksi = Database.bukaKoneksi();
             PreparedStatement perintah = koneksi.prepareStatement(sql)) {
            perintah.setLong(1, id);
            return perintah.executeUpdate() > 0;
        }
    }
}
