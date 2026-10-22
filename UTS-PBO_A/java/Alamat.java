public class Alamat {
    
    public static final String NEGARA = "Indonesia";

   
    private String jalan;
    private String kota;
    private String provinsi;

    public Alamat(String jalan, String kota, String provinsi) {
        this.jalan = jalan;
        this.kota = kota;
        this.provinsi = provinsi;
    }

    public String getJalan() { return jalan; }
    public void setJalan(String jalan) { this.jalan = jalan; }

    public String getKota() { return kota; }
    public void setKota(String kota) { this.kota = kota; }

    public String getProvinsi() { return provinsi; }
    public void setProvinsi(String provinsi) { this.provinsi = provinsi; }

    @Override
    public String toString() {
        return jalan + ", " + kota + ", " + provinsi + ", " + NEGARA;
    }
}