# Bukti Penerapan Relasi Komposisi dan Agregasi — Sesi 7

Dokumen ini mencatat bukti pengujian perilaku kode program terhadap penerapan relasi **Komposisi** (`Buku` dan `Bab`) serta **Agregasi** (`Jurusan` dan `Mahasiswa`) pada bahasa Java dan PHP.

---

## 1. Pembuktian Relasi Komposisi (`Buku` dan `Bab`)

### Konsep & Aturan
- Objek bagian (`Bab`) hanya boleh dibuat di dalam kelas induk (`Buku`) melalui method `tambahBab(int nomor, String judulBab)`.
- Pada Java, kelas `Bab` didefinisikan sebagai *static inner class* dengan *constructor* bersifat `private` agar tidak dapat dibuat secara mandiri dari luar.
- Method `getBab()` wajib mengembalikan salinan yang tidak dapat diubah (*immutable copy*) guna mencegah kebocoran koleksi (*collection leakage*).

### Bukti Eksekusi Kode (Java)
Di dalam `Main.java`, dilakukan percobaan untuk mengosongkan koleksi bab dari luar objek `buku`:

```java
System.out.println("  Mencoba mengubah koleksi internal dari luar...");
try {
    buku.getBab().clear();
    System.out.println("    MASALAH: koleksi internal berhasil dikosongkan dari luar!");
} catch (UnsupportedOperationException e) {
    System.out.println("    Ditolak — koleksi bab tidak bisa diubah dari luar. Benar.");
}