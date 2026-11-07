package id.ac.univpancasila.perpustakaan.service;

import id.ac.univpancasila.perpustakaan.util.PenulisBerkas;

import java.io.IOException;
import java.nio.file.Path;

public class EksporService {

    /**
     * @param gagalDiBaris jika > 0, lempar exception tepat sebelum menulis baris ke-N
     *                     (untuk membuktikan berkas tetap tertutup).
     */
    public void eksporDaftarBuku(LibraryService service, Path tujuan, int gagalDiBaris)
            throws IOException {
        // try-with-resources: close() dipanggil otomatis, juga saat exception
        try (PenulisBerkas penulis = new PenulisBerkas(tujuan)) {
            int nomor = 0;
            for (String isbn : service.semuaIsbn()) {
                nomor++;
                if (nomor == gagalDiBaris) {
                    throw new IllegalStateException("Simulasi gagal saat menulis baris " + nomor);
                }
                penulis.tulisBaris(isbn + ";" + service.judulDari(isbn) + ";" + service.sisaEksemplar(isbn));
            }
        }
    }
}
