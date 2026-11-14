package id.ac.univpancasila.perpustakaan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    record Buku(String isbn, String judul) {}

    private Repository<Buku> repo;

    @BeforeEach
    void siapkan() {
        repo = new Repository<>(Buku::isbn);
    }

    @Test
    @DisplayName("Item yang disimpan dapat ditemukan kembali")
    void simpanLaluTemukan() {
        repo.simpan(new Buku("978-1", "PBO"));

        assertTrue(repo.cariById("978-1").isPresent());
        assertEquals("PBO", repo.cariById("978-1").get().judul());
    }

    @Test
    @DisplayName("Mencari id yang tidak ada mengembalikan Optional kosong")
    void cariYangTidakAda() {
        assertTrue(repo.cariById("tidak-ada").isEmpty());
    }

    @Test
    @DisplayName("Item yang dihapus tidak dapat ditemukan lagi")
    void hapusItem() {
        repo.simpan(new Buku("978-1", "PBO"));

        assertTrue(repo.hapus("978-1"));
        assertTrue(repo.cariById("978-1").isEmpty());
    }

    @Test
    @DisplayName("Repository baru berisi nol item")
    void repositoryKosong() {
        assertEquals(0, repo.jumlah());
        assertTrue(repo.cariSemua().isEmpty());
    }

    @Test
    @DisplayName("Koleksi yang dikembalikan cariSemua tidak dapat diubah dari luar")
    void koleksiTidakBocor() {
        repo.simpan(new Buku("978-1", "PBO"));

        assertThrows(UnsupportedOperationException.class,
                () -> repo.cariSemua().add(new Buku("978-2", "Lain")));
    }

    @Test
    @DisplayName("Menyimpan dengan kunci yang sama menimpa item lama")
    void simpanKunciSamaMenimpa() {
        repo.simpan(new Buku("978-1", "PBO"));
        repo.simpan(new Buku("978-1", "PBO Edisi 2"));

        assertEquals(1, repo.jumlah());
        assertEquals("PBO Edisi 2", repo.cariById("978-1").get().judul());
    }

    @Test
    @DisplayName("Menghapus id yang tidak ada mengembalikan false")
    void hapusIdTidakAda() {
        assertFalse(repo.hapus("tidak-ada"));
    }
}
