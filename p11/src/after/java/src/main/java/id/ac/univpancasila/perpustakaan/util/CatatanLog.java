package id.ac.univpancasila.perpustakaan.util;

import id.ac.univpancasila.perpustakaan.exception.PerpustakaanException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Mencatat setiap exception yang tertangkap: waktu, jenis, dan data penyebab. */
public class CatatanLog {

    private static final Path BERKAS = Path.of("logs", "perpustakaan.log");
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void catat(PerpustakaanException e) {
        String baris = LocalDateTime.now().format(FORMAT)
                + " | " + e.getClass().getSimpleName()
                + " | " + e.getData()
                + " | " + e.getMessage();
        try {
            Files.createDirectories(BERKAS.getParent());
            Files.writeString(BERKAS, baris + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ioe) {
            // logging tidak boleh menjatuhkan program; laporkan, jangan telan diam-diam
            System.err.println("[PERINGATAN] Gagal menulis log: " + ioe.getMessage());
        }
    }
}
