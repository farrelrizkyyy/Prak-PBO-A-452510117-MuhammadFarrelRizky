# Catatan Keputusan & Analisis Eksperimen

## 1. Aturan Pewarisan di Java (Single vs Multiple Inheritance)
**Pertanyaan:** Mengapa Java hanya mengizinkan kelas mewarisi SATU kelas (`extends`), tetapi dapat mengimplementasikan BANYAK interface (`implements`)?

**Jawaban:**
Java membatasi pewarisan kelas (*Single Inheritance*) untuk menghindari masalah **Diamond Problem** (ambiguitas saat dua kelas induk memiliki atribut/method dengan nama yang sama). Sebaliknya, interface di Java tidak menyimpan *state* (atribut instance), melainkan hanya kontrak deklarasi method. Oleh karena itu, sebuah kelas aman untuk mengimplementasikan banyak interface (*Multiple Interface*) tanpa menimbulkan bentrokan logika.

---

## 2. Eksperimen Penolakan `Sepeda` pada Method `isiPenuh()`
**Pertanyaan:** Apa yang terjadi saat memanggil `isiPenuh(sepeda)`? Mengapa penolakan ini menguntungkan?

**Pesan Error (Java - Compile-time Error):**
```text
Main.java:34: error: incompatible types: Sepeda cannot be converted to Fuelable
        isiPenuh(sepeda);
                 ^