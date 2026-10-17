/**
 * Enum: hanya nilai yang terdaftar di sini yang mungkin ada.
 */
public enum TipeBahanBakar {

    BENSIN("Bensin", 12000),
    SOLAR("Solar", 10500),
    LISTRIK("Listrik", 2500);

    private final String label;
    private final double hargaPerSatuan;

    TipeBahanBakar(String label, double hargaPerSatuan) {
        this.label = label;
        this.hargaPerSatuan = hargaPerSatuan;
    }

    public String getLabel() { 
        return label; 
    }

    /** Menghitung total biaya pengisian */
    public double biayaPengisian(double jumlah) {
        return jumlah * hargaPerSatuan;
    }

    /** Kembalikan true hanya untuk LISTRIK */
    public boolean ramahLingkungan() {
        return this == LISTRIK;
    }
}