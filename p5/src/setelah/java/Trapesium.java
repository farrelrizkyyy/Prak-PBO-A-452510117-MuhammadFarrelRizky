public class Trapesium extends BangunDatar {
    private final double sisiAtas;
    private final double sisiBawah;
    private final double tinggi;
    private final double sisiMiring1;
    private final double sisiMiring2;

    public Trapesium(double sisiAtas, double sisiBawah, double tinggi, double sisiMiring1, double sisiMiring2) {
        super("Trapesium");
        if (sisiAtas <= 0 || sisiBawah <= 0 || tinggi <= 0 || sisiMiring1 <= 0 || sisiMiring2 <= 0) {
            throw new IllegalArgumentException("Dimensi trapesium harus lebih besar dari 0");
        }
        this.sisiAtas = sisiAtas;
        this.sisiBawah = sisiBawah;
        this.tinggi = tinggi;
        this.sisiMiring1 = sisiMiring1;
        this.sisiMiring2 = sisiMiring2;
    }

    @Override
    public double luas() {
        return 0.5 * (sisiAtas + sisiBawah) * tinggi;
    }

    @Override
    public double keliling() {
        return sisiAtas + sisiBawah + sisiMiring1 + sisiMiring2;
    }
}