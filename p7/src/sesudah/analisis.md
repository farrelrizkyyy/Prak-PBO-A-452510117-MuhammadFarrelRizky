# Analisis Kebutuhan dan Pemodelan Objek — Sistem Perpustakaan

Dokumen ini merangkum hasil analisis kebutuhan Sistem Perpustakaan untuk Sesi 7. Fokus utama pada sesi ini adalah menerapkan serta membedakan konsep **komposisi** dan **agregasi**, menjaga enkapsulasi data, serta menegakkan aturan invariant dan validasi sederhana pada entitas kode Java dan PHP.

---

## 1. Kebutuhan Utama Sistem

1. Sistem menyimpan data **`Buku`** yang memiliki nomor induk, judul, penulis, dan jumlah eksemplar.
2. Setiap objek **`Buku`** terdiri atas beberapa **`Bab`**, di mana bab lahir melalui parameter nomor dan judul di dalam buku yang menaunginya.
3. Sistem memiliki entitas **`Jurusan`** (kode dan nama) serta **`Mahasiswa`** (NIM dan nama) sebagai bentuk perbandingan relasi agregasi.
4. Kelas **`Jurusan`** dapat menerima/mendaftarkan objek mahasiswa yang dibuat dari luar serta dapat mengeluarkannya kembali tanpa menghancurkan eksistensi objek mahasiswa tersebut.
5. Lapisan layanan (**`PerpustakaanService`**) bertugas mengelola koleksi buku, menambah koleksi, mencari koleksi berdasarkan nomor induk, serta memproses aksi peminjaman dan pengembalian.

---

## 2. Aturan Bisnis dan Invariant

- Jumlah eksemplar buku pada constructor tidak boleh bernilai negatif (`< 0`).
- Stok eksemplar buku tidak boleh dikurangi (`kurangiStok()`) apabila stok sudah habis (`<= 0`).
- Objek `Bab` pada relasi komposisi hanya dapat dibuat melalui method `tambahBab()` di dalam kelas `Buku`; pemanggil dari luar tidak boleh menginjeksikan atau membuat objek `Bab` secara mandiri.
- Method pengambil daftar bab (`getBab()`) pada Java harus mengembalikan salinan tak-berubah menggunakan `List.copyOf(bab)` guna mencegah kebocoran koleksi (*collection leakage*) dari luar.
- Objek `Mahasiswa` yang didaftarkan ke `Jurusan` tidak boleh bernilai `null` (pada Java) dan tidak boleh terdaftar duplikat (berdasarkan pengecekan NIM).
- Objek `Mahasiswa` yang dikeluarkan dari jurusan tetap hidup dan eksis di luar lingkup jurusan tersebut.
- Operasi peminjaman dan pengembalian pada service memvalidasi keberadaan buku; peminjaman mengurangi stok sebanyak satu dan pengembalian menambahkannya kembali.

---

## 3. Kata Benda dan Keputusan Kelas

| Kata Benda | Keputusan | Alasan |
| :--- | :--- | :--- |
| **Buku** | Kelas Utama | Menyimpan state/atribut buku, mengelola koleksi bab internal, dan mengatur ketersediaan stok eksemplar. |
| **Bab** | Kelas Bagian (Komposisi) | Memiliki nomor dan judul, tetapi tidak memiliki arti atau siklus hidup mandiri di luar objek `Buku` yang memuatnya. |
| **Jurusan** | Kelas Agregasi | Mewakili entitas wadah yang menaungi dan mengelola daftar objek `Mahasiswa` melalui operasi pendaftaran. |
| **Mahasiswa** | Kelas Independen | Dibuat secara mandiri di luar jurusan, dimasukkan melalui agregasi, dan tetap hidup meskipun dikeluarkan dari jurusan. |
| **PerpustakaanService** | Kelas Layanan (*Service*) | Menyediakan antarmuka operasional sistem seperti pendaftaran koleksi, pencarian, peminjaman, dan pengembalian. |
| *nomor induk, judul, penulis, kode, nama, NIM* | Atribut | Hanya menyimpan nilai data primitif atau teks, bukan entitas objek yang memiliki perilaku mandiri. |
| *jumlah eksemplar* | Atribut | Menyatakan status kuantitas stok buku yang dimodifikasi melalui method khusus (`kurangiStok`/`tambahStok`). |
| *koleksi buku, daftar bab, daftar mahasiswa* | Atribut Koleksi | Menyimpan struktur asosiasi antar-objek di dalam kelas induknya (`Buku` atau `Jurusan`). |
| *peminjaman, pengembalian* | Method / Operasi | Menyatakan bentuk perilaku atau tindakan layanan sistem, bukan kelas mandiri pada cakupan sesi ini. |

---

## 4. Kata Kerja dan Calon Method

- **membuat / menambah bab** $\rightarrow$ diwujudkan menjadi method `tambahBab(int nomor, String judulBab)` pada kelas `Buku`.
- **memeriksa ketersediaan** $\rightarrow$ diwujudkan menjadi method `tersedia()` pada kelas `Buku`.
- **mengurangi stok** $\rightarrow$ diwujudkan menjadi method `kurangiStok()` pada kelas `Buku`.
- **menambah stok** $\rightarrow$ diwujudkan menjadi method `tambahStok()` pada kelas `Buku`.
- **mengambil data (getter)** $\rightarrow$ diwujudkan menjadi `getNomorInduk()`, `getJudul()`, `getBab()`, `getNama()`, `getMahasiswa()`, dll.
- **mendaftarkan mahasiswa** $\rightarrow$ diwujudkan menjadi method `daftarkan(Mahasiswa m)` pada kelas `Jurusan`.
- **mengeluarkan mahasiswa** $\rightarrow$ diwujudkan menjadi method `keluarkan(Mahasiswa m)` pada kelas `Jurusan`.
- **menambah & mencari koleksi** $\rightarrow$ diwujudkan menjadi method `tambahKoleksi(Buku buku)` dan `cariKoleksi(String nomorInduk)` pada kelas `PerpustakaanService`.
- **meminjam & mengembalikan buku** $\rightarrow$ diwujudkan melalui method `pinjam()` dan `kembalikan()` pada kelas `PerpustakaanService`.

---

## 5. Keputusan Perancangan Relasi Objek

### A. `Buku` dan `Bab`: Komposisi (`*--`)
Kelas `Bab` merupakan bagian mutlak dari `Buku`. Siklus hidup bab sepenuhnya dikendalikan oleh buku:
- Pada Java, `Bab` didefinisikan sebagai *nested static class* dengan constructor bersifat `private` sehingga tidak bisa diinisialisasi secara bebas di luar kelas `Buku`.
- Objek bab hanya dapat lahir melalui pemanggilan `buku.tambahBab(...)`.
- Method `getBab()` dilindungi dengan `List.copyOf(bab)` untuk mencegah pemanggil luar mengubah atau mengosongkan koleksi internal dari luar (*collection leakage*).
- Pada PHP, karena ketiadaan *nested class*, disiplin ini dijaga melalui properti `readonly` pada `Bab` serta konvensi pembuatan objek yang hanya diakses melalui `Buku::tambahBab()`.

### B. `Jurusan` dan `Mahasiswa`: Agregasi (`o--`)
Objek `Mahasiswa` dibentuk secara terpisah di luar kelas `Jurusan`, lalu diinjeksikan ke dalam jurusan melalui method `daftarkan()`. Ketika method `keluarkan()` dipanggil, referensi hubungan dilepas dari daftar jurusan, tetapi objek `Mahasiswa` tersebut tetap hidup dan valid di luar lingkungan jurusan.

### C. `PerpustakaanService` dan `Buku`: Agregasi / Asosiasi Layanan
Kelas layanan `PerpustakaanService` mengelola daftar koleksi buku yang diterima dari luar melalui method `tambahKoleksi()`. Layanan ini bertindak sebagai pengelola referensi data tanpa memegang kendali atas siklus hidup atau pembuatan objek buku itu sendiri.

---

## 6. Kesimpulan Sesi 7
Implementasi kode pada Java dan PHP telah teruji berhasil membuktikan bahwa perbedaan komposisi dan agregasi tidak hanya digambarkan secara visual pada diagram UML, tetapi benar-benar ditegakkan melalui aturan struktur kode, pembatasan akses constructor, serta perlindungan integritas koleksi data di dalam program.