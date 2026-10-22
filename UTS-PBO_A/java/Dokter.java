interface Arsipable {
    String KATEGORI = "SURAT-RS";
    String cetakArsip(); 
}

abstract class Orang implements Arsipable {
    private String nama;
    private int umur;

    public Orang(String nama, int umur) {
        this.nama = nama;
        this.umur = umur;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getUmur() { return umur; }
    public void setUmur(int umur) { this.umur = umur; }

    public abstract String getPeran();

    public String getInfo() {
        return getPeran() + ": " + nama + " (" + umur + " tahun)";
    }
}

public class Dokter extends Orang {
    private String spesialis;

    public Dokter(String nama, int umur, String spesialis) {
        super(nama, umur);
        this.spesialis = spesialis;
    }

    public String getSpesialis() { return spesialis; }
    public void setSpesialis(String spesialis) { this.spesialis = spesialis; }

    @Override
    public String getPeran() { return "Dokter"; }

    @Override
    public String getInfo() {
        return super.getInfo() + " - Spesialis " + spesialis;
    }

    @Override
    public String cetakArsip() {
        return "[" + KATEGORI + "] " + getInfo();
    }
}

