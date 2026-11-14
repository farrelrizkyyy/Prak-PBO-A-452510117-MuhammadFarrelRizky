package id.ac.univpancasila.perpustakaan;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Repository generik. T adalah type parameter: tipe item baru ditentukan
 * saat kelas dipakai, misalnya Repository<Buku>.
 */
public class Repository<T> {

    private final Map<String, T> data = new LinkedHashMap<>();

    /** Fungsi pengambil kunci, supaya Repository tidak perlu tahu isi T. */
    private final Function<T, String> pengambilKunci;

    public Repository(Function<T, String> pengambilKunci) {
        this.pengambilKunci = pengambilKunci;
    }

    /** Menyimpan item. Kunci yang sama menimpa item lama. */
    public void simpan(T item) {
        data.put(pengambilKunci.apply(item), item);
    }

    /** Optional berisi item bila ada, kosong bila tidak. Tidak pernah null. */
    public Optional<T> cariById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    /** Salinan tak-berubah, jadi koleksi internal tidak bocor. */
    public List<T> cariSemua() {
        return List.copyOf(data.values());
    }

    /** true bila ada item yang terhapus. */
    public boolean hapus(String id) {
        return data.remove(id) != null;
    }

    public int jumlah() { return data.size(); }
}
