public class Main {
    public static void main(String[] args) {
        
        RumahSakit rs = new RumahSakit("RS Sehat Sentosa",
                new Alamat("Jl. Ahmad Yani No. 10", "Bekasi", "Jawa Barat"));

        Dokter d1 = new Dokter("dr. Andi Wijaya", 45, "Penyakit Dalam");
        Dokter d2 = new Dokter("dr. Siti Rahma", 38, "Anak");
        Dokter d3 = new Dokter("dr. Rahman Wahyudi", 50, "Psikologi");

        rs.tambahDokter(d1);
        rs.tambahDokter(d2);
        rs.tambahDokter(d3);

        Pasien p1 = new Pasien("Budi Santoso", 30,
                new Alamat("Jl. Mawar No. 5", "Bekasi", "Jawa Barat"),
                new Penyakit("Demam Berdarah", Penyakit.Tingkat.SEDANG), d1);

        Pasien p2 = new Pasien("Rina Putri", 7,
                new Alamat("Jl. Melati No. 12", "Jakarta Timur", "DKI Jakarta"),
                new Penyakit("Influenza", Penyakit.Tingkat.RINGAN));
        Pasien p3 = new Pasien("Sumanto", 34,
                new Alamat("Jl. Kamboja No. 10", "Bogor", "Jawa Barat"),
                new Penyakit("Influenza", Penyakit.Tingkat.RINGAN));
        p2.setDokter(d2);   

        rs.daftarkanPasien(p1);
        rs.daftarkanPasien(p2);
        rs.daftarkanPasien(p3);

        rs.cetakSemuaArsip();

        System.out.println("Total pasien terdaftar : " + Pasien.getJumlahPasien());
        System.out.println("Total surat diarsipkan : " + RumahSakit.getTotalSurat());
    }
}