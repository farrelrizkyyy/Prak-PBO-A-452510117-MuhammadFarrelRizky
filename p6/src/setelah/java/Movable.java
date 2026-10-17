public interface Movable {

    void bergerak();

    double kecepatanMaksimum();

    /**
     * TODO 1: default method — kembalikan ringkasan berupa
     *         "kecepatan maksimum 180 km/jam" memakai kecepatanMaksimum().
     */
    default String ringkasanGerak() {
        return String.format("kecepatan maksimum %.0f km/jam", kecepatanMaksimum());
    }
}