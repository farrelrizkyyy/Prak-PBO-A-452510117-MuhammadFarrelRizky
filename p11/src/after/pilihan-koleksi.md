# Pilihan Koleksi — Sesi 11

## 1. Penggantian struktur data

| Sebelumnya (sesi 10) | Sekarang (sesi 11) | Alasan memilih ini, bukan yang lain |
|---|---|---|
| `Map<String,Integer> stok` + `Map<String,String> judul` (dua Map paralel dengan kunci ISBN yang sama) | satu `Repository<Buku>` (di dalamnya `LinkedHashMap<String,Buku>`, kunci = nomor induk) | Data satu buku kini terkumpul dalam satu objek `Buku`, tidak tersebar di dua Map yang bisa tidak sinkron. Pencarian per ISBN langsung lewat `get(isbn)`, tidak perlu perulangan. Bukan `List` karena `List` harus dicek satu per satu (linear), sedangkan `Map` langsung ke kuncinya. `LinkedHashMap` dipilih (bukan `HashMap`) agar urutan buku sesuai urutan dimasukkan, sehingga laporan stabil. |
| `array $stok` + `array $judul` di PHP | satu `KoleksiBuku` (typed collection, array asosiatif isbn => Buku di dalamnya) | Alasan sama. PHP tidak punya generics, jadi `KoleksiBuku` memvalidasi isinya lewat type hint `Buku`. |
| `Map<String,Integer> pinjaman` (idAnggota -> jumlah dipinjam) | tetap `Map` | Ini memang pasangan kunci-nilai yang dicari lewat kunci, jadi Map sudah tepat. |
| `new TreeSet<>(stok.keySet())` untuk `semuaIsbn()` | tetap `Set` (`TreeSet`) | ISBN tidak boleh kembar dan perlu terurut. |

## 2. Catatan null-safety

- `judulDari(isbn)` dulu memakai `judul.get(isbn)` yang mengembalikan `null` bila ISBN tidak ada. Sekarang
  `repoBuku.cariById(isbn).map(Buku::getJudul).orElse("(tidak ada)")`, sehingga tidak ada null yang bocor.
- `sisaEksemplar(isbn)` memakai `Optional.map(...).orElse(0)`.
- `pinjam()` memakai `cariById(...).orElseThrow(...)`, jadi kasus "buku tidak ada" tidak mungkin terlupa.
- PHP: `cariByIsbn()` bertipe `?Buku`, dipakai dengan `?->` dan `??`, misalnya
  `$this->koleksi->cariByIsbn($isbn)?->getJudul() ?? '(tidak ada)'`.
- Baris yang MASIH berpotensi NullPointerException: `Collectors.groupingBy(Buku::getPenulis, ...)` akan
  melempar NPE bila ada buku dengan penulis `null`. Saat ini aman karena `tambahBuku` selalu mengisi penulis
  (default "Tidak diketahui"), tetapi konstruktor `Buku` sendiri belum menolak penulis null.
- `pinjaman.get(idAnggota) >= BATAS_PINJAM` melakukan unboxing; aman hanya karena baris sebelumnya
  sudah memastikan `containsKey(idAnggota)`.

## 3. Pesan kegagalan uji (Langkah 7)

(ISI SENDIRI dari terminal kamu: ubah `List.copyOf(...)` di `Repository.cariSemua()` menjadi
`new ArrayList<>(data.values())`, jalankan `mvn test`, lalu tempel pesan kegagalan yang asli di sini.)

```
(tempel output mvn test di sini)
```

Penjelasan: (tulis dengan kata-katamu: nama uji yang gagal, apa yang diharapkan vs yang terjadi,
dan bagaimana itu menunjuk ke `cariSemua()` sebagai letak masalahnya.)
