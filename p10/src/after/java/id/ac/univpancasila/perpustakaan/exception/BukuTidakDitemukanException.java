package id.ac.univpancasila.perpustakaan.exception;

import java.util.Map;

public class BukuTidakDitemukanException extends PerpustakaanException {

    private final String isbn;

    public BukuTidakDitemukanException(String isbn) {
        super("Buku dengan ISBN " + isbn + " tidak ditemukan");
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public Map<String, Object> getData() {
        return Map.of("isbn", isbn);
    }
}
