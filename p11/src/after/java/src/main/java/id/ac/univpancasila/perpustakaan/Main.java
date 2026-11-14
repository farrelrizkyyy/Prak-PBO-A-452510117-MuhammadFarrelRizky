package id.ac.univpancasila.perpustakaan;

import id.ac.univpancasila.perpustakaan.model.Buku;
import id.ac.univpancasila.perpustakaan.service.LibraryService;

import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        LibraryService s = new LibraryService();
        s.tambahBuku("978-1", "Basis Data", 1, "Adi");
        s.tambahBuku("978-2", "Algoritma", 3, "Adi");
        s.tambahBuku("978-3", "Jaringan", 0, "Budi");

        System.out.println("Laporan 1 - Buku tersedia : "
                + s.bukuTersedia().stream().map(Buku::getJudul).collect(Collectors.joining(", ")));
        System.out.println("Laporan 2 - Semua judul   : " + s.daftarJudul());
        System.out.println("Laporan 3 - Buku per penulis:");
        s.jumlahPerPenulis().forEach((penulis, jumlah) ->
                System.out.println("  " + penulis + ": " + jumlah));
    }
}
