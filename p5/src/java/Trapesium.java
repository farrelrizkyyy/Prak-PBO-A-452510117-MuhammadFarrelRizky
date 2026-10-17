public class Trapesium extends BangunDatar {

    private final double alasatas;
    private final double alasbawah;
    private final double tinggi;
    private final double sisiMiring1;
    private final double sisiMiring2;

    public Trapesium(double alasatas, double alasbawah, double tinggi, double sisiMiring1, double sisiMiring2) {
        super("Trapesium");
        if (alasatas <= 0 || alasbawah <= 0 || tinggi <= 0 || sisiMiring1 <= 0 || sisiMiring2 <= 0) {
            throw new IllegalArgumentException("Semua parameter harus lebih besar dari 0");
        }
        this.alasatas = alasatas;
        this.alasbawah = alasbawah;
        this.tinggi = tinggi;
        this.sisiMiring1 = sisiMiring1;
        this.sisiMiring2 = sisiMiring2;
    }

    @Override 
    public double luas() {
        return ((alasatas + alasbawah) / 2) * tinggi;
    }

    @Override 
    public double keliling() { 
        return alasatas + alasbawah + sisiMiring1 + sisiMiring2; 
    }
    
}
