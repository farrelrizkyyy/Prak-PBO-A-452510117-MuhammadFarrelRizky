package id.ac.univpancasila.perpustakaan;

import id.ac.univpancasila.perpustakaan.exception.*;
import id.ac.univpancasila.perpustakaan.service.EksporService;
import id.ac.univpancasila.perpustakaan.service.LibraryService;
import id.ac.univpancasila.perpustakaan.util.CatatanLog;

import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        LibraryService service = new LibraryService();
        service.tambahBuku("978-1", "Basis Data", 1);
        service.tambahBuku("978-2", "Pemrograman Java", 5);
        service.tambahBuku("978-3", "Struktur Data", 2);
        service.daftarkanAnggota("A-001");
        service.daftarkanAnggota("A-002");

        EksporService ekspor = new EksporService();

        try {
            while (true) {
                System.out.println("\n=== PERPUSTAKAAN ===");
                System.out.println("1. Pinjam buku");
                System.out.println("2. Tambah buku");
                System.out.println("3. Ekspor daftar buku");
                System.out.println("4. Ekspor dengan simulasi gagal");
                System.out.println("0. Keluar");
                int pilihan = bacaAngka("Pilih menu", 0, 4);

                if (pilihan == 0) {
                    System.out.println("Sampai jumpa.");
                    return;
                }
                jalankan(pilihan, service, ekspor);
            }
        } catch (NoSuchElementException eof) {
            // input ditutup (Ctrl+D / pipe habis): keluar dengan rapi
            System.out.println("\nInput berakhir. Program selesai.");
        }
    }

    private static void jalankan(int pilihan, LibraryService service, EksporService ekspor) {
        try {
            switch (pilihan) {
                case 1 -> {
                    String idAnggota = bacaTeks("ID anggota");
                    String isbn = bacaTeks("ISBN");
                    service.pinjam(idAnggota, isbn);
                    System.out.println("Berhasil: " + service.judulDari(isbn) + " dipinjam oleh " + idAnggota
                            + " (sisa " + service.sisaEksemplar(isbn) + ").");
                }
                case 2 -> {
                    String isbn = bacaTeks("ISBN");
                    String judul = bacaTeks("Judul");
                    int jumlah = bacaAngka("Jumlah eksemplar", 1, 1000);
                    service.tambahBuku(isbn, judul, jumlah);
                    System.out.println("Berhasil menambah " + jumlah + " eksemplar '" + judul + "'.");
                }
                case 3 -> {
                    ekspor.eksporDaftarBuku(service, Path.of("daftar-buku.txt"), 0);
                    System.out.println("Berhasil diekspor ke daftar-buku.txt");
                }
                case 4 -> ekspor.eksporDaftarBuku(service, Path.of("daftar-buku.txt"), 2);
                default -> { }
            }
        } catch (PerpustakaanException e) {          // satu catch untuk SEMUA kegagalan domain
            System.out.println("[GAGAL] " + e.getMessage());
            // pemeriksaan tipe: ambil data spesifik
            if (e instanceof BukuTidakDitemukanException b) {
                System.out.println("        -> ISBN yang dicari: " + b.getIsbn());
            } else if (e instanceof AnggotaTidakDitemukanException a) {
                System.out.println("        -> ID yang dicari: " + a.getIdAnggota());
            } else if (e instanceof BukuSedangDipinjamException d) {
                System.out.println("        -> Judul: " + d.getJudul() + ", ISBN: " + d.getIsbn());
            } else if (e instanceof BatasPinjamTerlampauiException t) {
                System.out.println("        -> Batas: " + t.getBatas() + " buku");
            }
            CatatanLog.catat(e);
        } catch (IOException e) {
            System.out.println("[GAGAL] Berkas tidak dapat ditulis: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("[GAGAL] " + e.getMessage());
        }
    }

    /** Minta teks; ulangi sampai tidak kosong (spasi saja dianggap kosong). */
    private static String bacaTeks(String label) {
        while (true) {
            System.out.print(label + ": ");
            String teks = in.nextLine().trim();
            if (!teks.isEmpty()) {
                return teks;
            }
            System.out.println("[!] " + label + " tidak boleh kosong.");
        }
    }

    /** Minta angka dalam rentang [min, maks]; ulangi bila huruf, kosong, atau di luar rentang. */
    private static int bacaAngka(String label, int min, int maks) {
        while (true) {
            String teks = bacaTeks(label);
            try {
                int angka = Integer.parseInt(teks);
                if (angka < min || angka > maks) {
                    System.out.println("[!] Masukkan angka " + min + " sampai " + maks + ".");
                    continue;
                }
                return angka;
            } catch (NumberFormatException e) {
                System.out.println("[!] '" + teks + "' bukan angka yang valid.");
            }
        }
    }
}
