public class AntiPatternRefaktor {

    // Menggunakan kontrak interface/abstract class polimorfik
    public interface HitungLuas {
        double luas();
    }

    public static class LingkaranObj implements HitungLuas {
        private final double r;
        public LingkaranObj(double r) { this.r = r; }
        @Override public double luas() { return Math.PI * r * r; }
    }

    public static class PersegiObj implements HitungLuas {
        private final double sisi;
        public PersegiObj(double sisi) { this.sisi = sisi; }
        @Override public double luas() { return sisi * sisi; }
    }

    public static class SegitigaObj implements HitungLuas {
        private final double alas, tinggi;
        public SegitigaObj(double alas, double tinggi) { this.alas = alas; this.tinggi = tinggi; }
        @Override public double luas() { return 0.5 * alas * tinggi; }
    }

    public static void main(String[] args) {
        HitungLuas[] daftar = {
            new LingkaranObj(7),
            new PersegiObj(5),
            new SegitigaObj(4, 3)
        };

        double total = 0;
        for (HitungLuas h : daftar) {
            total += h.luas();
        }
        System.out.printf("Total luas (cara polimorfik): %.2f%n", total);
    }
}