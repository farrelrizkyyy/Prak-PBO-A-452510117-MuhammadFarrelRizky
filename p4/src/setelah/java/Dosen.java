public class Dosen extends Pegawai {
    private final int jumlahSks;
    private final double honorPerSks;

    public Dosen(String nip, String nama, double gajiPokok, int jumlahSks, double honorPerSks) {
        super(nip, nama, gajiPokok);
        this.jumlahSks = jumlahSks;
        this.honorPerSks = honorPerSks;
    }

    @Override
    public double hitungGaji() {
        // Gaji pokok ditambah honor SKS
        return super.hitungGaji() + (jumlahSks * honorPerSks);
    }

    @Override
    public String jenis() {
        return "DOSEN";
    }

    public int getJumlahSks() { return jumlahSks; }
    public double getHonorPerSks() { return honorPerSks; }
}