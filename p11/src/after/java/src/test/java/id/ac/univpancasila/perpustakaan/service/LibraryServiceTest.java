package id.ac.univpancasila.perpustakaan.service;

import id.ac.univpancasila.perpustakaan.exception.AnggotaTidakDitemukanException;
import id.ac.univpancasila.perpustakaan.exception.BatasPinjamTerlampauiException;
import id.ac.univpancasila.perpustakaan.exception.BukuSedangDipinjamException;
import id.ac.univpancasila.perpustakaan.exception.BukuTidakDitemukanException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/** Skenario 1-5 dari skenario-uji.md (sesi 10) + laporan fungsional sesi 11. */
class LibraryServiceTest {

    private LibraryService service;

    @BeforeEach
    void siapkan() {
        service = new LibraryService();
        service.tambahBuku("978-1", "Basis Data", 1, "Adi");
        service.tambahBuku("978-2", "Algoritma", 3, "Adi");
        service.tambahBuku("978-3", "Jaringan", 1, "Budi");
        service.daftarkanAnggota("A-001");
        service.daftarkanAnggota("A-002");
    }

    // ---- Skenario sesi 10 ----

    @Test
    @DisplayName("Skenario 1: ISBN tidak terdaftar -> BukuTidakDitemukanException")
    void skenario1BukuTidakDitemukan() {
        BukuTidakDitemukanException ex = assertThrows(BukuTidakDitemukanException.class,
                () -> service.pinjam("A-001", "978-9"));

        assertEquals("978-9", ex.getIsbn());
        assertEquals("Buku dengan ISBN 978-9 tidak ditemukan", ex.getMessage());
    }

    @Test
    @DisplayName("Skenario 2: anggota tidak terdaftar -> AnggotaTidakDitemukanException")
    void skenario2AnggotaTidakDitemukan() {
        assertThrows(AnggotaTidakDitemukanException.class,
                () -> service.pinjam("A-009", "978-1"));
    }

    @Test
    @DisplayName("Skenario 3: eksemplar habis -> BukuSedangDipinjamException")
    void skenario3BukuSedangDipinjam() {
        service.pinjam("A-001", "978-1");

        assertThrows(BukuSedangDipinjamException.class,
                () -> service.pinjam("A-002", "978-1"));
        assertEquals(0, service.sisaEksemplar("978-1"));
    }

    @Test
    @DisplayName("Skenario 4: batas 3 buku tercapai -> BatasPinjamTerlampauiException")
    void skenario4BatasPinjam() {
        service.pinjam("A-001", "978-2");
        service.pinjam("A-001", "978-2");
        service.pinjam("A-001", "978-2");

        assertThrows(BatasPinjamTerlampauiException.class,
                () -> service.pinjam("A-001", "978-3"));
    }

    @Test
    @DisplayName("Skenario 5: anggota diperiksa lebih dulu daripada buku")
    void skenario5AnggotaDiperiksaLebihDulu() {
        assertThrows(AnggotaTidakDitemukanException.class,
                () -> service.pinjam("A-009", "978-9"));
    }

    // ---- Perilaku normal dan Repository ----

    @Test
    @DisplayName("pinjam yang berhasil mengurangi stok satu eksemplar")
    void pinjamBerhasilMengurangiStok() {
        service.pinjam("A-001", "978-2");

        assertEquals(2, service.sisaEksemplar("978-2"));
    }

    @Test
    @DisplayName("tambahBuku dengan ISBN yang sama menjumlahkan stok")
    void tambahBukuMenjumlahkanStok() {
        service.tambahBuku("978-1", "Basis Data", 2, "Adi");

        assertEquals(3, service.sisaEksemplar("978-1"));
    }

    @Test
    @DisplayName("cariBuku mengembalikan Optional kosong untuk ISBN yang tidak ada")
    void cariBukuTidakAda() {
        assertTrue(service.cariBuku("978-9").isEmpty());
        assertEquals("(tidak ada)", service.judulDari("978-9"));
    }

    // ---- Laporan fungsional ----

    @Test
    @DisplayName("Laporan 1: bukuTersedia hanya berisi buku dengan stok > 0")
    void laporanBukuTersedia() {
        service.pinjam("A-001", "978-1");   // stok 978-1 menjadi 0

        assertEquals(2, service.bukuTersedia().size());
        assertTrue(service.bukuTersedia().stream()
                .noneMatch(b -> b.getNomorInduk().equals("978-1")));
    }

    @Test
    @DisplayName("Laporan 2: daftarJudul menggabungkan judul dengan koma")
    void laporanDaftarJudul() {
        assertEquals("Basis Data, Algoritma, Jaringan", service.daftarJudul());
    }

    @Test
    @DisplayName("Laporan 3: jumlahPerPenulis mengelompokkan per penulis")
    void laporanJumlahPerPenulis() {
        assertEquals(Map.of("Adi", 2L, "Budi", 1L), service.jumlahPerPenulis());
    }
}
