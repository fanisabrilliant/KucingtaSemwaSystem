# Kucingta Semwa - Katalog Produk Petshop

Aplikasi desktop sederhana untuk mengelola katalog produk petshop: makanan hewan, kandang, dan alat bermain. Dibuat dengan **JavaFX** dan **SQLite**.

## Fitur

- **Tambah produk** dengan nama, kategori, harga, stok, dan foto
- **Lihat katalog** dalam bentuk kartu produk
- **Filter** produk berdasarkan kategori
- **Beli 1**: mengurangi stok produk sebanyak 1 (tombol nonaktif jika stok habis)
- **Edit produk**: ubah data produk yang sudah ada
- **Hapus produk**: hapus produk dari katalog dengan dialog konfirmasi

## Teknologi

| Komponen | Versi |
| --- | --- |
| Java (JDK) | 17 atau lebih baru |
| JavaFX | 21 |
| SQLite JDBC | 3.49.1.0 |
| Build tool | Maven |

## Cara Menjalankan

1. Pastikan **JDK 17+** dan **Maven** sudah terpasang.
2. Clone repository ini:
   ```bash
   git clone https://github.com/<username>/KucingtaSemwa.git
   cd KucingtaSemwa
   ```
3. Jalankan aplikasi:
   ```bash
   mvn javafx:run
   ```

Bisa juga dibuka lewat IDE (NetBeans, IntelliJ IDEA, dll.) sebagai proyek Maven. Jalankan **Clean and Build** lalu **Run** pada kelas `main.MainApp`.

> Saat pertama kali dijalankan, aplikasi membuat file database `data/petshop.db` secara otomatis dan mengisinya dengan 6 contoh produk.

## Cara Penggunaan

### Menambah produk
1. Isi **Nama produk**, pilih **Kategori**, isi **Harga (Rp)** dan **Stok**, lalu pilih **Foto contoh**.
2. Klik **Simpan Produk**. Produk baru langsung muncul di katalog.

### Memfilter katalog
Pilih kategori pada dropdown **Tampilkan** di atas katalog. Pilih **Semua Kategori** untuk menampilkan semuanya.

### Menjual produk
Klik **Beli 1** pada kartu produk. Stok berkurang 1, dan tombol nonaktif otomatis saat stok habis.

### Mengedit produk
1. Klik **Edit** pada kartu produk yang ingin diubah. Datanya terisi otomatis ke form.
2. Ubah data yang diperlukan.
3. Klik **Simpan Perubahan**, atau **Batal** untuk membatalkan.

### Menghapus produk
1. Klik **Hapus** pada kartu produk.
2. Konfirmasi pada dialog yang muncul. Produk akan dihapus permanen.

## Struktur Proyek

```
KucingtaSemwa/
├── pom.xml
└── src/main/
    ├── java/
    │   ├── main/         MainApp.java            (titik masuk aplikasi)
    │   ├── controller/   KatalogController.java (logika tampilan)
    │   ├── model/        Produk.java             (model + validasi data)
    │   ├── data/         ProdukRepository.java   (akses data: tambah, ambil, jual, ubah, hapus)
    │   └── db/           Database.java           (koneksi dan pembuatan tabel SQLite)
    └── resources/
        ├── image/        foto contoh produk dan logo
        └── view/         katalog.fxml dan styles/app.css
```

## Catatan

- Data produk disimpan di `data/petshop.db` pada folder tempat aplikasi dijalankan. Hapus file ini untuk mengembalikan katalog ke data contoh awal.
- Pilihan foto saat ini terbatas pada gambar contoh yang ada di `src/main/resources/image/`.
