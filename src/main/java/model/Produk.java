package model;

public class Produk {

    private long id;
    private String nama;
    private String kategori;
    private long harga;
    private int stok;
    private String foto;

    public Produk(long id, String nama, String kategori, long harga, int stok, String foto) {
        if (nama == null || nama.isBlank()) {
            throw new IllegalArgumentException("Nama produk wajib diisi.");
        }
        if (kategori == null || kategori.isBlank()) {
            throw new IllegalArgumentException("Pilih kategori produk.");
        }
        if (harga <= 0) {
            throw new IllegalArgumentException("Harga harus lebih dari nol.");
        }
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif.");
        }
        if (foto == null || foto.isBlank()) {
            throw new IllegalArgumentException("Pilih foto produk.");
        }
        this.id = id;
        this.nama = nama.trim();
        this.kategori = kategori;
        this.harga = harga;
        this.stok = stok;
        this.foto = foto;
    }

   
    public Produk(String nama, String kategori, long harga, int stok, String foto) {
        this(0, nama, kategori, harga, stok, foto);
    }

    public long getId() { return id; }
    public String getNama() { return nama; }
    public String getKategori() { return kategori; }
    public long getHarga() { return harga; }
    public int getStok() { return stok; }
    public String getFoto() { return foto; }
}
