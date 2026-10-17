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
        System.out.println(merek + " dikayuh dengan santai di jalur sepeda");
    }

    @Override
    public double kecepatanMaksimum() {
        return 35.0;
    }
}