public class Mobil extends Kendaraan implements Movable, Fuelable {

    private final double kapasitasTangki;
    private double isiTangki = 0;

    public Mobil(String merek, int tahun, double kapasitasTangki) {
        super(merek, tahun);
        this.kapasitasTangki = kapasitasTangki;
    }

    @Override public int jumlahRoda() { return 4; }

    @Override public void bergerak() {
        System.out.println(merek + " melaju di jalan raya");
    }

    @Override public double kecepatanMaksimum() { return 180.0; }

    @Override public void isiBahanBakar(double jumlah) {
        if (jumlah <= 0) {
            System.out.println("  [Error] Jumlah pengisian harus lebih dari 0.");
            return;
        }
        if (isiTangki + jumlah > kapasitasTangki) {
            isiTangki = kapasitasTangki;
            System.out.println("  [Info] Tangki penuh! Kelebihan tumpah.");
        } else {
            isiTangki += jumlah;
        }
    }

    @Override public double kapasitasTangki() { return kapasitasTangki; }

    @Override public TipeBahanBakar tipeBahanBakar() { return TipeBahanBakar.BENSIN; }

    public double getIsiTangki() { return isiTangki; }
}