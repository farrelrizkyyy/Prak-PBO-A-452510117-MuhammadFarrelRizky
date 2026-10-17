public class AntiPaternRefaktor {
    static double hitungLuas(BangunDatar bangun) {
        return bangun.luas();
    }

    public static void main(String[] args) {
        BangunDatar[] bangunDatarArray = {
            new Lingkaran(7),
            new Persegi(5),
            new Segitiga(3, 4, 5),
            new Trapesium(6,10,4,5,5)
        };

        System.out.println("=== Bangun Datar ===");
        for (BangunDatar bangun : bangunDatarArray) {
            System.out.printf("%s: Luas = %.2f, Keliling = %.2f%n",
                    bangun.getNama(), hitungLuas(bangun), bangun.keliling());
        }
    }
}