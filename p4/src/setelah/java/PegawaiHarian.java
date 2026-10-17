public class PegawaiHarian extends Pegawai {
    private final int jumlahHariMasuk;
    private final double harian;

    public PegawaiHarian(String nip, String nama, double gajiPokok, int jumlahHariMasuk, double harian) {
        super(nip, nama, gajiPokok);
        this.jumlahHariMasuk = jumlahHariMasuk;
        this.harian = harian;
    }

    @Override
    public double hitungGaji() {
        // Gaji pokok ditambah total upah harian berdasarkan hari masuk
        return super.hitungGaji() + (jumlahHariMasuk * harian);
    }

    @Override
    public String jenis() {
        return "HARIAN";
    }

    public int getJumlahHariMasuk() { return jumlahHariMasuk; }
    public double getHarian() { return harian; }
}