# Catatan Uji — Sesi 11

## Kasus uji yang PALING sulit ditulis

(Draf — ubah dengan pengalamanmu sendiri.)

Menurut saya yang paling sulit adalah skenario 13 dan 14 dari `skenario-uji.md`: kegagalan di tengah
penulisan berkas (ekspor) dan input yang ditutup (Ctrl+D). Keduanya bergantung pada hal di luar program:
berkas, dan standard input. Untuk mengujinya perlu mengganti sumber tersebut dengan versi palsu
(misalnya `StringReader` / `ByteArrayInputStream`, atau penulis yang sengaja gagal), padahal kode sesi 10
memanggil input dan berkas secara langsung.

Skenario 6 sampai 12 (masukan menu salah) juga sulit karena logikanya ada di lapisan masukan (`bacaAngka`,
`bacaTeks`), bukan di `LibraryService`. Agar bisa diuji, method itu harus dipisah dari `Scanner`
sehingga bisa diberi teks langsung.

Sebaliknya skenario 1 sampai 5 paling mudah, karena semuanya murni logika di `LibraryService.pinjam()`:
buat objek, panggil method, periksa exception yang keluar.

## Cakupan uji saat ini

| Skenario | Terwakili oleh |
|---|---|
| 1 | `LibraryServiceTest.skenario1BukuTidakDitemukan` / `testSkenario1BukuTidakDitemukan` |
| 2 | `skenario2AnggotaTidakDitemukan` |
| 3 | `skenario3BukuSedangDipinjam` |
| 4 | `skenario4BatasPinjam` |
| 5 | `skenario5AnggotaDiperiksaLebihDulu` |
| 6-14 | belum (butuh App / EksporService, lihat catatan di atas) |
