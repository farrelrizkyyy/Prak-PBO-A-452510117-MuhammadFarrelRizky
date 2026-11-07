package id.ac.univpancasila.perpustakaan.exception;

import java.util.Map;

public class AnggotaTidakDitemukanException extends PerpustakaanException {

    private final String idAnggota;

    public AnggotaTidakDitemukanException(String idAnggota) {
        super("Anggota dengan ID " + idAnggota + " tidak ditemukan");
        this.idAnggota = idAnggota;
    }

    public String getIdAnggota() {
        return idAnggota;
    }

    @Override
    public Map<String, Object> getData() {
        return Map.of("idAnggota", idAnggota);
    }
}
