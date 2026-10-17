public class Sepeda extends Kendaraan implements Movable {
    
    public Sepeda(String merek, int tahun) {
        super(merek, tahun);
    }

    @Override
    public int jumlahRoda() {
        return 2;
    }

    @Override
    public void bergerak() {
        System.out.println(merek + " dikayuh di jalan raya");
    }

    @Override
    public double kecepatanMaksimum() {
        return 30.0; // Kecepatan maksimum sepeda
    }
}