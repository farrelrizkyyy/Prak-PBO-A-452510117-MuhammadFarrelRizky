public class Main {
    public static void main(String[] args) {

        // TODO Langkah 4: tambahkan Dosen dan PegawaiHarian ke daftar ini
        Pegawai[] daftar = {
            new PegawaiTetap("198701012010", "Ani Lestari",  6_000_000, 15),
            new PegawaiKontrak("K-2024-007",  "Budi Santoso", 5_000_000, 12),
            new Dosen("D-199002", "Dr. Citra Dewi", 4_500_000, 6, 150_000),
            new PegawaiHarian("H-2026-01", "Dedi Mulyadi", 2_000_000, 20, 75_000)
        };

        System.out.println("=== Daftar Gaji ===");
        for (Pegawai p : daftar) {
            System.out.println("  " + p);
        }

        double total = 0;
        for (Pegawai p : daftar) total += p.hitungGaji();
        System.out.printf("%n  Total beban gaji: Rp%,.2f%n", total);

        System.out.println();
        System.out.println("Periksa: Ani (pokok 6.000.000, masa kerja 15 tahun)");
        System.out.println("  tunjangan 15 x 2% = 30%, jadi gaji seharusnya Rp7.800.000,00");

        // Percobaan Langkah 1: (Opsional untuk diuji coba lalu dikomen kembali)
        // Pegawai langsung = new Pegawai("X", "Y", 1000) { public String jenis() { return "?"; } };
    }
}