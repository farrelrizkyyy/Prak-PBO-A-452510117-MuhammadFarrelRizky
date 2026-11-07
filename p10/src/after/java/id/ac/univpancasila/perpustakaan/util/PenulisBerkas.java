package id.ac.univpancasila.perpustakaan.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Pembungkus penulis berkas yang mencatat kapan berkas ditutup (untuk pembuktian). */
public class PenulisBerkas implements AutoCloseable {

    private final BufferedWriter writer;
    private boolean tertutup = false;

    public PenulisBerkas(Path path) throws IOException {
        this.writer = Files.newBufferedWriter(path);
    }

    public void tulisBaris(String baris) throws IOException {
        writer.write(baris);
        writer.newLine();
    }

    public boolean isTertutup() { return tertutup; }

    @Override
    public void close() throws IOException {
        writer.close();
        tertutup = true;
        System.out.println("[INFO] Berkas ditutup.");
    }
}
