package id.ac.univpancasila.perpustakaan.exception;

import java.util.Map;

public class BukuSedangDipinjamException extends PerpustakaanException {

    private final String isbn;
    private final String judul;

    public BukuSedangDipinjamException(String isbn, String judul) {
        super("Buku " + judul + " dengan ISBN " + isbn + " sedang dipinjam");
        this.isbn = isbn;
        this.judul = judul;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getJudul() {
        return judul;
    }

    @Override
    public Map<String, Object> getData() {
        return Map.of("isbn", isbn, "judul", judul);
    }
}
