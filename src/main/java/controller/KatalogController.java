package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import data.ProdukRepository;
import model.Produk;

public class KatalogController {

    private static final String SEMUA = "Semua Kategori";

    @FXML private ImageView logoView;
    @FXML private TextField namaField;
    @FXML private TextField hargaField;
    @FXML private TextField stokField;
    @FXML private ComboBox<String> kategoriBox;
    @FXML private ComboBox<String> fotoBox;
    @FXML private ComboBox<String> filterBox;
    @FXML private Button simpanButton;
    @FXML private Button batalButton;
    @FXML private Label pesanLabel;
    @FXML private Label jumlahLabel;
    @FXML private TilePane katalogPane;

    private final ProdukRepository repository = new ProdukRepository();

    // null = form dalam mode tambah; terisi = sedang mengedit produk ini.
    private Produk produkDiedit = null;

    @FXML
    private void initialize() {
        URL lokasiLogo = getClass().getResource("/image/logo.png");
        if (lokasiLogo != null) {
            logoView.setImage(new Image(lokasiLogo.toExternalForm()));
        }

        String[] kategori = {"Makanan Kucing", "Makanan Anjing", "Makanan Ikan",
            "Makanan Hamster", "Kandang", "Alat Bermain"};
        for (String k : kategori) {
            kategoriBox.getItems().add(k);
            filterBox.getItems().add(k);
        }
        filterBox.getItems().add(0, SEMUA);
        kategoriBox.setValue("Makanan Kucing");
        filterBox.setValue(SEMUA);

        String[] foto = {"pakan_kucing.png", "pakan_anjing.png", "pakan_ikan.png",
            "pakan_hamster.png", "kandang.png", "mainan.png"};
        for (String f : foto) {
            fotoBox.getItems().add(f);
        }
        fotoBox.setValue("pakan_kucing.png");

        try {
            showData();
        } catch (SQLException e) {
            pesanLabel.setText("Gagal membaca database. Lihat Output.");
            e.printStackTrace();
        }
    }

    private void showData() throws SQLException {
        String pilihan = filterBox.getValue();
        String filter = (pilihan == null || pilihan.equals(SEMUA)) ? null : pilihan;

        ArrayList<Produk> daftar = repository.getAll(filter);
        katalogPane.getChildren().clear();
        for (Produk produk : daftar) {
            katalogPane.getChildren().add(createProductCard(produk));
        }
        jumlahLabel.setText("Jumlah produk: " + daftar.size());
    }

    private VBox createProductCard(Produk produk) {
        ImageView fotoView = new ImageView();
        fotoView.setFitWidth(170);
        fotoView.setFitHeight(110);
        fotoView.setPreserveRatio(true);

        StackPane bingkai = new StackPane();
        bingkai.setPrefSize(180, 120);
        bingkai.setMinSize(180, 120);
        bingkai.setMaxSize(180, 120);

        URL lokasi = getClass().getResource("/image/" + produk.getFoto());
        if (lokasi != null) {
            Image gambar = new Image(lokasi.toExternalForm(), 170, 110, true, true);
            if (!gambar.isError()) {
                fotoView.setImage(gambar);
                bingkai.getChildren().add(fotoView);
            } else {
                bingkai.getChildren().add(new Label("Gambar tidak terbaca"));
            }
        } else {
            bingkai.getChildren().add(new Label("Gambar tidak tersedia"));
        }

        Label kategoriLabel = new Label(produk.getKategori());
        kategoriLabel.getStyleClass().add("badge-kategori");

        Label namaLabel = new Label(produk.getNama());
        namaLabel.setWrapText(true);
        namaLabel.getStyleClass().add("nama-produk");

        Label hargaLabel = new Label("Rp" + produk.getHarga());
        Label stokLabel = new Label(produk.getStok() > 0
                ? "Stok: " + produk.getStok() : "Stok habis");
        stokLabel.getStyleClass().add("muted");

        Button beliButton = new Button("Beli 1");
        beliButton.getStyleClass().add("button-beli");
        beliButton.setDisable(produk.getStok() <= 0);
        beliButton.setOnAction(e -> buyProduct(produk));

        Button editButton = new Button("Edit");
        editButton.getStyleClass().add("button-edit");
        editButton.setOnAction(e -> startEdit(produk));

        Button hapusButton = new Button("Hapus");
        hapusButton.getStyleClass().add("button-hapus");
        hapusButton.setOnAction(e -> deleteProduct(produk));

        HBox barisTombol = new HBox(6, beliButton, editButton, hapusButton);

        VBox kartu = new VBox();
        kartu.setSpacing(8);
        kartu.setMinWidth(220);
        kartu.setPrefWidth(220);
        kartu.setMaxWidth(220);
        kartu.getStyleClass().add("kartu-produk");
        kartu.getChildren().add(bingkai);
        kartu.getChildren().add(kategoriLabel);
        kartu.getChildren().add(namaLabel);
        kartu.getChildren().add(hargaLabel);
        kartu.getChildren().add(stokLabel);
        kartu.getChildren().add(barisTombol);
        return kartu;
    }

    @FXML
    private void filterChanged() {
        try {
            showData();
        } catch (SQLException e) {
            pesanLabel.setText("Gagal membaca database. Lihat Output.");
            e.printStackTrace();
        }
    }

    private void buyProduct(Produk produk) {
        try {
            if (repository.jual(produk.getId())) {
                pesanLabel.setText("Terjual 1: " + produk.getNama() + " (Rp" + produk.getHarga() + ")");
            } else {
                pesanLabel.setText("Maaf, stok " + produk.getNama() + " sudah habis.");
            }
            showData();
        } catch (SQLException e) {
            pesanLabel.setText("Gagal memproses pembelian. Lihat Output.");
            e.printStackTrace();
        }
    }

    @FXML
    private void saveProduct() {
        Produk produk;
        try {
            String nama = namaField.getText();
            String kategori = kategoriBox.getValue();
            long harga = Long.parseLong(hargaField.getText().trim());
            int stok = Integer.parseInt(stokField.getText().trim());
            String foto = fotoBox.getValue();
            long id = (produkDiedit == null) ? 0 : produkDiedit.getId();
            produk = new Produk(id, nama, kategori, harga, stok, foto);
        } catch (NumberFormatException e) {
            pesanLabel.setText("Harga dan stok harus angka bulat, contoh 12000 dan 10.");
            return;
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
            return;
        }

        boolean modeEdit = (produkDiedit != null);
        String pesanSukses = "Produk berhasil disimpan.";
        try {
            if (modeEdit) {
                if (repository.update(produk)) {
                    pesanSukses = "Produk berhasil diperbarui: " + produk.getNama();
                } else {
                    pesanSukses = "Produk tidak ditemukan (mungkin sudah dihapus).";
                }
            } else {
                repository.add(produk);
            }
        } catch (SQLException e) {
            pesanLabel.setText("Gagal menyimpan. Lihat Output.");
            e.printStackTrace();
            return;
        }

        resetForm();
        try {
            showData();
            pesanLabel.setText(pesanSukses);
        } catch (SQLException e) {
            pesanLabel.setText("Data tersimpan, tetapi katalog gagal dimuat ulang.");
            e.printStackTrace();
        }
    }

    // Isi form dengan data produk, lalu form berpindah ke mode edit.
    private void startEdit(Produk produk) {
        produkDiedit = produk;
        namaField.setText(produk.getNama());
        kategoriBox.setValue(produk.getKategori());
        hargaField.setText(String.valueOf(produk.getHarga()));
        stokField.setText(String.valueOf(produk.getStok()));
        fotoBox.setValue(produk.getFoto());

        simpanButton.setText("Simpan Perubahan");
        batalButton.setVisible(true);
        batalButton.setManaged(true);
        pesanLabel.setText("Mengedit: " + produk.getNama() + ". Ubah data di form, lalu klik Simpan Perubahan.");
        namaField.requestFocus();
    }

    @FXML
    private void cancelEdit() {
        resetForm();
        pesanLabel.setText("Edit dibatalkan.");
    }

    // Kosongkan form dan kembalikan ke mode tambah produk.
    private void resetForm() {
        produkDiedit = null;
        namaField.clear();
        hargaField.clear();
        stokField.clear();
        kategoriBox.setValue("Makanan Kucing");
        fotoBox.setValue("pakan_kucing.png");
        simpanButton.setText("Simpan Produk");
        batalButton.setVisible(false);
        batalButton.setManaged(false);
    }

    private void deleteProduct(Produk produk) {
        Alert konfirmasi = new Alert(Alert.AlertType.CONFIRMATION);
        konfirmasi.setTitle("Hapus Produk");
        konfirmasi.setHeaderText("Hapus \"" + produk.getNama() + "\"?");
        konfirmasi.setContentText("Produk akan dihapus permanen dari katalog.");
        Optional<ButtonType> jawaban = konfirmasi.showAndWait();
        if (jawaban.isEmpty() || jawaban.get() != ButtonType.OK) {
            return;
        }

        try {
            boolean terhapus = repository.delete(produk.getId());
            // Kalau produk yang dihapus sedang diedit, form dikosongkan.
            if (produkDiedit != null && produkDiedit.getId() == produk.getId()) {
                resetForm();
            }
            showData();
            pesanLabel.setText(terhapus
                    ? "Produk dihapus: " + produk.getNama()
                    : "Produk tidak ditemukan (mungkin sudah dihapus).");
        } catch (SQLException e) {
            pesanLabel.setText("Gagal menghapus produk. Lihat Output.");
            e.printStackTrace();
        }
    }
}
