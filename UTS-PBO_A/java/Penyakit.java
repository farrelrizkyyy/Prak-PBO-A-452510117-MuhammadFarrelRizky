public class Penyakit {

    public enum Tingkat {
        RINGAN("Ringan"),
        SEDANG("Sedang"),
        BERAT("Berat");

        private final String label;

        Tingkat(String label) { this.label = label; }

        public String getLabel() { return label; }
    }

    private String nama;
    private Tingkat tingkat;

    public Penyakit(String nama, Tingkat tingkat) {
        this.nama = nama;
        this.tingkat = tingkat;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public Tingkat getTingkat() { return tingkat; }
    public void setTingkat(Tingkat tingkat) { this.tingkat = tingkat; }

    @Override
    public String toString() {
        return nama + " (Tingkat: " + tingkat.getLabel() + ")";
    }
}