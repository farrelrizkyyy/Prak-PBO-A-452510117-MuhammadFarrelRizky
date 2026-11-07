package id.ac.univpancasila.perpustakaan.exception;

import java.util.Map;

/**
 * Induk seluruh exception domain perpustakaan.
 * Dipilih UNCHECKED (RuntimeException): kegagalan ini berasal dari permintaan
 * yang tidak sah, dan penanganannya cukup di satu lapisan terluar (App).
 */
public abstract class PerpustakaanException extends RuntimeException {

    protected PerpustakaanException(String pesan) {
        super(pesan);
    }

    protected PerpustakaanException(String pesan, Throwable penyebab) {
        super(pesan, penyebab);
    }

    /** Data penyebab kegagalan (untuk logging dan respons HTTP di sesi 13-14). */
    public abstract Map<String, Object> getData();
}
