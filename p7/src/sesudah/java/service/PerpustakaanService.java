package service;

import java.util.ArrayList;
import java.util.List;
import model.Buku;

/**
 * Kerangka lapisan layanan.
 * Pada sesi 7 cukup struktur dan tanda tangan method; isinya boleh minimal.
 * Lapisan ini akan diperdalam pada sesi 10 (exception) dan 12 (SOLID).
 *
 * TODO: sesuaikan seluruh isi berkas ini dengan class diagram Anda sendiri.
 *       Kelas dan method di sini hanya contoh awal.
 */
public class PerpustakaanService {
    
    // TODO 1: tentukan bagaimana layanan ini menyimpan koleksi dan anggota.
    //         Agregasi atau komposisi? Tuliskan alasannya di analisis.md,
    //         lalu wujudkan keputusan itu di sini.
    private final List<Buku> koleksiBuku = new ArrayList<>();

    public void tambahKoleksi(Buku buku) {
        // TODO 2
        if (buku != null && !koleksiBuku.contains(buku)) {
            koleksiBuku.add(buku);
        }
    }

    public Buku cariKoleksi(String nomorInduk) {
        // TODO 3
        for (Buku b : koleksiBuku) {
            if (b.getNomorInduk().equalsIgnoreCase(nomorInduk)) {
                return b;
            }
        }
        return null;
    }

    public void pinjam(String nomorAnggota, String nomorInduk) {
        // TODO 4: terapkan aturan bisnis dari deskripsi-kebutuhan.md.
        //         Aturan lengkapnya akan disempurnakan pada sesi 10.
        Buku buku = cariKoleksi(nomorInduk);
        if (buku != null && buku.tersedia()) {
            buku.kurangiStok();
            System.out.println("Anggota " + nomorAnggota + " berhasil meminjam buku: " + buku.getJudul());
        } else {
            System.out.println("Peminjaman gagal: Buku tidak ditemukan atau stok habis.");
        }
    }

    public void kembalikan(String nomorAnggota, String nomorInduk) {
        // TODO 5
        Buku buku = cariKoleksi(nomorInduk);
        if (buku != null) {
            buku.tambahStok();
            System.out.println("Anggota " + nomorAnggota + " berhasil mengembalikan buku: " + buku.getJudul());
        } else {
            System.out.println("Pengembalian gagal: Buku tidak ditemukan.");
        }
    }
}
