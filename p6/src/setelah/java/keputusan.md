# Keputusan Desain: Abstract Class, Interface, Enum, dan Trait

## 1. Mengapa Java memperbolehkan satu class mewarisi SATU class (Single Inheritance) tetapi mengimplementasikan BANYAK interface (Multiple Inheritance of Type)?
Java menerapkan aturan ini untuk menghindari masalah **Diamond Problem** yang sering terjadi pada multiple inheritance antar class (di mana sebuah subclass kebingungan mewarisi method dari dua parent class yang memiliki struktur/state internal yang sama). Dengan interface, tidak ada state/atribut instance yang diwarisi (hanya kontrak method), sehingga aman untuk mengimplementasikan banyak interface sekaligus guna mendefinisikan berbagai perilaku (*behavior*) objek.

## 2. Pesan Kesalahan Kompilasi saat `isiPenuh(sepeda)` dipanggil:
```text
error: incompatible types: Sepeda cannot be converted to Fuelable
    isiPenuh(sepeda);
             ^