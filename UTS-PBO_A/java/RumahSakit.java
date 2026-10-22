import java.util.ArrayList;

public class RumahSakit {
    public static final int MAKS_DOKTER = 10;
    private static int totalSurat = 0; 

    private String nama;
    private Alamat alamat;

    private ArrayList<Dokter> daftarDokter = new ArrayList<>();
    private ArrayList<Pasien> daftarPasien = new ArrayList<>();

    public RumahSakit(String nama, Alamat alamat) {
        this.nama = nama;
        this.alamat = alamat;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public Alamat getAlamat() { return alamat; }

    public void tambahDokter(Dokter d) {
        if (daftarDokter.size() < MAKS_DOKTER) {
            daftarDokter.add(d);
        } else {
            System.out.println("Kapasitas dokter penuh!");
        }
    }

    public void daftarkanPasien(Pasien p) {
        daftarPasien.add(p);
        totalSurat++;
    }

    public static int getTotalSurat() { return totalSurat; }

    public void cetakSemuaArsip() {
        System.out.println("==================================================");
        System.out.println("   ARSIP SURAT " + nama.toUpperCase());
        System.out.println("   " + alamat);
        System.out.println("==================================================");

        ArrayList<Arsipable> semua = new ArrayList<>();
        semua.addAll(daftarDokter);
        semua.addAll(daftarPasien);

        for (Arsipable a : semua) {
            System.out.println(a.cetakArsip());
            System.out.println("--------------------------------------------------");
        }
    }
}