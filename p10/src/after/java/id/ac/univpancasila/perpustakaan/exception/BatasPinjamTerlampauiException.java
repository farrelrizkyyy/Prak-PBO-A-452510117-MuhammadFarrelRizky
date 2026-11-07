package id.ac.univpancasila.perpustakaan.exception;

import java.util.Map;

public class BatasPinjamTerlampauiException extends PerpustakaanException {

    private final String idAnggota;
    private final int batas;

    public BatasPinjamTerlampauiException(String idAnggota, int batas) {
        super("Anggota dengan ID " + idAnggota
                + " telah mencapai batas maksimum peminjaman (" + batas + " buku)");
        this.idAnggota = idAnggota;
        this.batas = batas;
    }

    public String getIdAnggota() {
        return idAnggota;
    }

    public int getBatas() {
        return batas;
    }

    @Override
    public Map<String, Object> getData() {
        return Map.of("idAnggota", idAnggota, "batas", batas);
    }
}
