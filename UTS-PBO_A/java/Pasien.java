public class Pasien extends Orang {
    private static int jumlahPasien = 0;

    private final String nomorArsip;
    private Alamat alamat;
    private Penyakit penyakit;
    private Dokter dokter; 

    public Pasien(String nama, int umur, Alamat alamat, Penyakit penyakit, Dokter dokter) {
        super(nama, umur);
        jumlahPasien++;
        this.nomorArsip = String.format("%s/%03d", KATEGORI, jumlahPasien);
        this.alamat = alamat;
        this.penyakit = penyakit;
        this.dokter = dokter;
    }
    public Pasien(String nama, int umur, Alamat alamat, Penyakit penyakit) {
        this(nama, umur, alamat, penyakit, null);
    }

    public static int getJumlahPasien() { return jumlahPasien; }

    public String getNomorArsip() { return nomorArsip; }

    public Alamat getAlamat() { return alamat; }
    public void setAlamat(Alamat alamat) { this.alamat = alamat; }

    public Penyakit getPenyakit() { return penyakit; }
    public void setPenyakit(Penyakit penyakit) { this.penyakit = penyakit; }

    public Dokter getDokter() { return dokter; }
    public void setDokter(Dokter dokter) { this.dokter = dokter; }

    @Override
    public String getPeran() { return "Pasien"; }

    @Override
    public String cetakArsip() {
        String namaDokter = (dokter != null) ? dokter.getNama() : "Belum ditentukan";
        return "No. Arsip : " + nomorArsip + "\n"
             + "Nama      : " + getNama() + " (" + getUmur() + " tahun)\n"
             + "Alamat    : " + alamat + "\n"
             + "Penyakit  : " + penyakit + "\n"
             + "Dokter    : " + namaDokter;
    }
}