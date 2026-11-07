package id.ac.univpancasila.perpustakaan.service;

import id.ac.univpancasila.perpustakaan.exception.AnggotaTidakDitemukanException;
import id.ac.univpancasila.perpustakaan.exception.BatasPinjamTerlampauiException;
import id.ac.univpancasila.perpustakaan.exception.BukuSedangDipinjamException;
import id.ac.univpancasila.perpustakaan.exception.BukuTidakDitemukanException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/** Hasil refactoring LibraryServiceKotor: guard clause + exception yang membawa data. */
public class LibraryService {

    public static final int BATAS_PINJAM = 3;

    private final Map<String, Integer> stok = new HashMap<>();
    private final Map<String, String> judul = new HashMap<>();
    private final Map<String, Integer> pinjaman = new HashMap<>();

    public void tambahBuku(String isbn, String judulBuku, int eksemplar) {
        stok.merge(isbn, eksemplar, Integer::sum);
        judul.put(isbn, judulBuku);
    }

    public void daftarkanAnggota(String idAnggota) {
        pinjaman.putIfAbsent(idAnggota, 0);
    }

    public void pinjam(String idAnggota, String isbn) {
        if (!pinjaman.containsKey(idAnggota)) {
            throw new AnggotaTidakDitemukanException(idAnggota);
        }
        if (!stok.containsKey(isbn)) {
            throw new BukuTidakDitemukanException(isbn);
        }
        if (stok.get(isbn) <= 0) {
            throw new BukuSedangDipinjamException(isbn, judul.get(isbn));
        }
        if (pinjaman.get(idAnggota) >= BATAS_PINJAM) {
            throw new BatasPinjamTerlampauiException(idAnggota, BATAS_PINJAM);
        }

        // logika utama, rata di kiri
        stok.merge(isbn, -1, Integer::sum);
        pinjaman.merge(idAnggota, 1, Integer::sum);
    }

    public int sisaEksemplar(String isbn) {
        return stok.getOrDefault(isbn, 0);
    }

    public String judulDari(String isbn) {
        return judul.get(isbn);
    }

    /** Seluruh ISBN, diurutkan agar keluaran ekspor stabil. */
    public Set<String> semuaIsbn() {
        return new TreeSet<>(stok.keySet());
    }
}
