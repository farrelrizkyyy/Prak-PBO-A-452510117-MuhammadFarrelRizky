# Skenario Kegagalan — Sistem Perpustakaan

| No | Masukan / tindakan | Kegagalan yang diharapkan | Jenis exception | Pesan yang ditampilkan |
|----|--------------------|---------------------------|-----------------|------------------------|
| 1  | Pinjam: ID `A-001`, ISBN `978-9` | Buku tidak ditemukan | BukuTidakDitemukanException | Buku dengan ISBN 978-9 tidak ditemukan |
| 2  | Pinjam: ID `A-009`, ISBN `978-1` | Anggota tidak terdaftar | AnggotaTidakDitemukanException | Anggota dengan ID A-009 tidak ditemukan |
| 3  | `A-001` pinjam `978-1`, lalu `A-002` pinjam `978-1` | Eksemplar habis | BukuSedangDipinjamException | Buku Basis Data dengan ISBN 978-1 sedang dipinjam |
| 4  | `A-001` pinjam `978-2` tiga kali, lalu pinjam `978-3` | Batas 3 buku tercapai | BatasPinjamTerlampauiException | Anggota dengan ID A-001 telah mencapai batas maksimum peminjaman (3 buku) |
| 5  | Pinjam: ID `A-009` dan ISBN `978-9` (keduanya salah) | Anggota diperiksa lebih dulu | AnggotaTidakDitemukanException | Anggota dengan ID A-009 tidak ditemukan |
| 6  | Menu: `abc` | Huruf saat diminta angka | NumberFormatException (ditangani di bacaAngka) | 'abc' bukan angka yang valid. |
| 7  | Menu: (Enter kosong) | Masukan kosong | tidak ada (guard di bacaTeks) | Pilih menu tidak boleh kosong. |
| 8  | Menu: (hanya spasi) | Spasi saja dianggap kosong | tidak ada (trim + guard) | Pilih menu tidak boleh kosong. |
| 9  | Menu: `9` | Angka di luar rentang menu | tidak ada (guard rentang) | Masukkan angka 0 sampai 4. |
| 10 | Tambah buku, jumlah: `-3` | Angka negatif | tidak ada (guard rentang) | Masukkan angka 1 sampai 1000. |
| 11 | Tambah buku, jumlah: `99999` | Angka terlalu besar | tidak ada (guard rentang) | Masukkan angka 1 sampai 1000. |
| 12 | Tambah buku, jumlah: `x` | Huruf saat diminta angka | NumberFormatException | 'x' bukan angka yang valid. |
| 13 | Ekspor dengan simulasi gagal (menu 4) | Gagal di tengah penulisan | IllegalStateException (Java) / RuntimeException (PHP) | [INFO] Berkas ditutup. lalu Simulasi gagal saat menulis baris 2 |
| 14 | Tekan Ctrl+D saat diminta menu | Input ditutup | NoSuchElementException (Java) / fgets false (PHP) | Input berakhir. Program selesai. |

## Jawaban pertanyaan

1. **Kesalahan pengguna vs pemrogram.** Skenario 1–5 adalah kesalahan pengguna atau keadaan data yang wajar terjadi,
   jadi dibuat exception domain yang membawa data agar bisa dilaporkan dengan jelas. Skenario 6–12 juga
   kesalahan pengguna, tetapi ditangani lebih awal di lapisan masukan (guard dan loop ulang) sehingga tidak
   perlu sampai ke service. Kesalahan pemrogram (misalnya null atau indeks salah) sengaja TIDAK ditangkap
   `catch (PerpustakaanException)`, supaya bug tidak tertelan.

2. **RuntimeException atau Exception?** Saya memilih RuntimeException (unchecked). Alasannya: kegagalan ini
   berasal dari permintaan yang tidak sah, bukan keadaan luar yang wajar. Pemanggil di tengah rantai juga tidak
   bisa berbuat apa-apa, sehingga cukup ditangani satu kali di lapisan terluar (App). Jika dibuat checked,
   setiap method di antara pinjam() dan App wajib menambah `throws`.

3. **Pengalaman catch kosong.** (Isi sendiri setelah mencoba Latihan 2: tulis apa yang kamu lihat di layar dan di log.)