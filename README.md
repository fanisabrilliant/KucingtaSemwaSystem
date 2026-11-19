# KucingtaSemwa
Aplikasi katalog produk perlengkapan dan pakan hewan peliharaan (KucingtaSemwa) berbasis Java dan Maven dengan antarmuka FXML/CSS serta penyimpanan data/database.
## Fitur Utama
- **Katalog Produk Hewan Peliharaan**: Menampilkan berbagai produk seperti pakan kucing, pakan anjing, pakan ikan, pakan hamster, mainan, dan kandang.
- **Arsitektur Terstruktur**: Memisahkan logika tampilan (`view`), pengendali (`controller`), model data (`model`), akses data (`data`), dan koneksi basis data (`db`).
- **Antarmuka Kustom**: Menggunakan file `katalog.fxml`, `app.css`, serta aset gambar produk dan logo.

## Struktur Proyek
```text
KucingtaSemwa/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   ├── controller/
        │   │   └── KatalogController.java
        │   ├── data/
        │   │   └── ProdukRepository.java
        │   ├── db/
        │   │   └── Database.java
        │   ├── main/
        │   │   └── MainApp.java
        │   └── model/
        │       └── Produk.java
        └── resources/
            ├── image/
            │   ├── logo.png
            │   ├── kandang.png
            │   ├── mainan.png
            │   ├── pakan_anjing.png
            │   ├── pakan_hamster.png
            │   ├── pakan_ikan.png
            │   └── pakan_kucing.png
            └── view/
                ├── katalog.fxml
                └── styles/
                    └── app.css
                    ## Cara Memakai Aplikasi
1. **Buka Aplikasi**: Jalankan `MainApp.java` hingga jendela utama katalog produk **KucingtaSemwa** (`katalog.fxml`) muncul.
2. **Jelajahi Katalog Produk**: Lihat daftar produk perlengkapan dan pakan hewan peliharaan yang tersedia (pakan kucing, pakan anjing, pakan ikan, pakan hamster, mainan, dan kandang).
3. **Gunakan Fitur Katalog**: Cari, filter, atau kelola data produk yang tersedia pada antarmuka sesuai fitur yang disediakan di halaman katalog.
4. **Tutup Aplikasi**: Tutup jendela aplikasi setelah selesai digunakan; perubahan data akan tersimpan melalui modul `Database`.
