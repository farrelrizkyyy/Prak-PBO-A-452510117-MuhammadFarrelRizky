package id.ac.univpancasila.perpustakaan.service;

import id.ac.univpancasila.perpustakaan.Repository;
import id.ac.univpancasila.perpustakaan.exception.AnggotaTidakDitemukanException;
import id.ac.univpancasila.perpustakaan.exception.BatasPinjamTerlampauiException;
import id.ac.univpancasila.perpustakaan.exception.BukuSedangDipinjamException;
import id.ac.univpancasila.perpustakaan.exception.BukuTidakDitemukanException;
import id.ac.univpancasila.perpustakaan.model.Buku;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/** Sesi 11: array/Map paralel diganti Repository<Buku>; laporan memakai operasi fungsional. */
public class LibraryService {

    public static final int BATAS_PINJAM = 3;
    public static final String PENULIS_DEFAULT = "Tidak diketahui";

    // Dulu: Map stok + Map judul (dua Map paralel dengan kunci ISBN yang sama).
    // Sekarang: satu Repository<Buku>, kuncinya nomor induk (ISBN).
    private final Repository<Buku> repoBuku = new Repository<>(Buku::getNomorInduk);
    private final Map<String, Integer> pinjaman = new HashMap<>();

    /** Kompatibel dengan kode lama (tanpa penulis). */
    public void tambahBuku(String isbn, String judulBuku, int eksemplar) {
        tambahBuku(isbn, judulBuku, eksemplar, PENULIS_DEFAULT);
    }

    public void tambahBuku(String isbn, String judulBuku, int eksemplar, String penulis) {
        int stokLama = repoBuku.cariById(isbn)
                .map(Buku::getJumlahEksemplar)
                .orElse(0);
        repoBuku.simpan(new Buku(isbn, judulBuku, penulis, stokLama + eksemplar));
    }

    public void daftarkanAnggota(String idAnggota) {
        pinjaman.putIfAbsent(idAnggota, 0);
    }

    public void pinjam(String idAnggota, String isbn) {
        if (!pinjaman.containsKey(idAnggota)) {
            throw new AnggotaTidakDitemukanException(idAnggota);
        }
        Buku buku = repoBuku.cariById(isbn)
                .orElseThrow(() -> new BukuTidakDitemukanException(isbn));
        if (!buku.tersedia()) {
            throw new BukuSedangDipinjamException(isbn, buku.getJudul());
        }
        if (pinjaman.get(idAnggota) >= BATAS_PINJAM) {
            throw new BatasPinjamTerlampauiException(idAnggota, BATAS_PINJAM);
        }

        // logika utama, rata di kiri
        buku.kurangiStok();
        pinjaman.merge(idAnggota, 1, Integer::sum);
    }

    public Optional<Buku> cariBuku(String isbn) {
        return repoBuku.cariById(isbn);
    }

    public int sisaEksemplar(String isbn) {
        return repoBuku.cariById(isbn)
                .map(Buku::getJumlahEksemplar)
                .orElse(0);
    }

    public String judulDari(String isbn) {
        return repoBuku.cariById(isbn)
                .map(Buku::getJudul)
                .orElse("(tidak ada)");
    }

    /** Seluruh ISBN, diurutkan agar keluaran ekspor stabil. */
    public Set<String> semuaIsbn() {
        return repoBuku.cariSemua().stream()
                .map(Buku::getNomorInduk)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    // ---------- Laporan fungsional (Langkah 4) ----------

    /** Laporan 1: buku yang tersedia (filter). */
    public List<Buku> bukuTersedia() {
        return repoBuku.cariSemua().stream()
                .filter(Buku::tersedia)
                .toList();
    }

    /** Laporan 2: seluruh judul digabung (map + join). */
    public String daftarJudul() {
        return repoBuku.cariSemua().stream()
                .map(Buku::getJudul)
                .collect(Collectors.joining(", "));
    }

    /** Laporan 3: jumlah buku per penulis (groupingBy), urut abjad. */
    public Map<String, Long> jumlahPerPenulis() {
        return repoBuku.cariSemua().stream()
                .collect(Collectors.groupingBy(Buku::getPenulis, TreeMap::new, Collectors.counting()));
    }
}
