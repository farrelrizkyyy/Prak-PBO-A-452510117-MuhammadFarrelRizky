# LAPORAN UTS PRAKTIKUM PEMROGRAMAN BERBASIS OBJEK

| Informasi Proyek | Keterangan |
| :--- | :--- |
| **Mata Kuliah** | Pemrograman Berbasis Objek (PBO) |
| **Kelas** | A |
| **PJ** | [Muhammad Shehan Algi] |
| **Tema** | [Admnistrasi Arsip Rumah Sakit] |
| **Tanggal** | [Kamis 22 Oktober 2026] |
| **Nama** | [Muhammad Farrel Rizky] |
| **NPM** | [4525210117] |

---

## Detail Pengerjaan & Anggota Kelompok

| Nama | NPM | Tugas / Fitur yang Dikerjakan |
| :--- | :--- | :--- |
| [El-jordan Fathi Arrizky] | [4525210115] | [Penyakit.Java] |
| [Muhammad Farrel Rizky] | [4525210117] | [RumahSakit.Java] |
| [Azzahra Navisha] | [4525210118] | [Dokter.Java] |
| [Delia Veris Maureta] | [4525210119] | [Pasien.Java] |
| [Muhammad Rafif Akbar] | [4525210125] | [Alamat.Java] |
| [Arsanti Nur Indriani] | [4525210126] | [Main.Java] |
| [Nisrina Akilla] | [4525210128] | [GA MASUK] |


---

## Deskripsi Proyek
> [Disini kelompok kami membuat Administrasi Surat Rumah Sakit yang di dalamnya ada beberapa keterangan seperti Nama Rumah Sakit, Penyakit Pasiennya, Dokter yang memeriksanya, nama Pasiennya, Alamat si pasiennya dan pastinya ada Main.Javanya untuk melihatnya outputnya.]

**Daftar Fitur:**
1. Nama Penyakit Pasien
2. Nama Rumah Sakit
3. Nama Dokter
4. Nama Pasien
5. Alamat Pasien

---

## 1. Implementasi Java

### 1.1. File: `Penyakit.java`
**Penjelasan Kode:**
> [Penyakit.java ini mendefinisikan class Penyakit yang menyimpan data penyakit pasien, yaitu nama dan tingkat keparahan. Atribut dibuat private dan diakses lewat getter dan setter (enkapsulasi). Data diisi lewat konstruktor Penyakit(String nama, Tingkat tingkat). Di dalam class ini ada enum bersarang bernama Tingkat dengan tiga nilai tetap, yaitu RINGAN, SEDANG, dan BERAT. Setiap nilai punya label yang diisi lewat konstruktor enum dan dibaca dengan getLabel(). Dengan enum, tingkat penyakit tidak bisa diisi sembarang teks. Method toString() di override agar objek Penyakit tercetak rapi, misalnya Influenza (Tingkat: Ringan).]

**Screenshot Kode:**

<img width="787" height="814" alt="image" src="https://github.com/user-attachments/assets/3f419e5e-eb08-4f65-8b80-36df51bd5ed4" />

### 1.2. File: `RumahSakit.java`
**Penjelasan Kode:**
> [RumahSakit.java ini berisi class RumahSakit, yaitu pusat pengelolaan arsip. Class ini punya konstanta MAKS_DOKTER = 10 (static final) sebagai batas jumlah dokter, dan variabel static totalSurat yang menghitung seluruh surat yang diarsipkan. Atribut alamat bertipe Alamat, serta dua ArrayList (daftarDokter dan daftarPasien) menunjukkan relasi objek, karena rumah sakit memiliki banyak dokter dan banyak pasien (has-a, agregasi). Method tambahDokter() menambah dokter selama kapasitas belum penuh, dan daftarkanPasien() menambah pasien sambil menaikkan totalSurat. Method cetakSemuaArsip() menggabungkan dokter dan pasien ke dalam satu ArrayList<Arsipable>, lalu memanggil cetakArsip() pada setiap elemen. Ini contoh polimorfisme, karena Dokter dan Pasien dicetak dengan cara masing-masing lewat satu tipe yang sama, yaitu interface Arsipable.]

**Screenshot Kode:**

<img width="686" height="918" alt="Screenshot 2026-10-22 214106" src="https://github.com/user-attachments/assets/1975bc7f-15da-4106-b946-316ae3504f81" />
<img width="865" height="396" alt="Screenshot 2026-10-22 214133" src="https://github.com/user-attachments/assets/c3c1222a-0119-4239-8c53-5e2eee117bd4" />


### 1.3. File: `Dokter.java`
**Penjelasan Kode:**
> [Pasien.java ini memuat tiga bagian.
> 1. Interface Arsipable, yang berisi konstanta KATEGORI = "SURAT-RS" dan method cetakArsip() yang wajib diimplementasikan semua class yang bisa diarsipkan.
> 2. Abstract class Orang, yang mengimplementasikan Arsipable dan menjadi superclass bagi Dokter dan Pasien. Class ini menyimpan atribut umum nama dan umur (private, dengan getter/setter), method abstrak getPeran() yang harus diisi oleh subclass, dan method getInfo() yang memakai getPeran().
> 3. Class Dokter, yang mewarisi Orang (extends) dan menambah atribut spesialis. Konstruktornya memanggil super(nama, umur) untuk mengisi data dari superclass. Dokter meng-override getPeran() (mengembalikan “Dokter”), getInfo() (memanggil super.getInfo() lalu menambahkan spesialis), dan cetakArsip() (memberi awalan [SURAT-RS]).
> Ketiganya contoh polimorfisme (overriding).]

**Screenshot Kode:**

<img width="628" height="885" alt="image" src="https://github.com/user-attachments/assets/0a61aff5-f3a9-4a34-9eb4-085c6dde6c1c" />


### 1.4. File: `Pasien.java`
**Penjelasan Kode:**
> [Pasien.java juga mewarisi Orang, sehingga memakai atribut nama dan umur dari superclass. Atribut khususnya adalah alamat (objek Alamat), penyakit (objek Penyakit), dan dokter (objek Dokter), yang membentuk relasi antar objek. Variabel static jumlahPasien menghitung semua pasien yang dibuat, dan atribut final nomorArsip dibuat otomatis di konstruktor dengan format SURAT-RS/001, SURAT-RS/002, dan seterusnya. Karena final, nomor ini tidak bisa diubah setelah dibuat. Ada dua konstruktor (overloading): satu menerima dokter, dan satu lagi tanpa dokter yang memanggil this(..., null). Method getPeran() dan cetakArsip() di-override dari superclass dan interface. Jika dokter masih null, arsip mencetak “Belum ditentukan”.]

**Screenshot Kode:**

<img width="808" height="933" alt="image" src="https://github.com/user-attachments/assets/f5a0130e-d544-4414-901f-404364e24001" />

### 1.5. File: `Alamat.java`
**Penjelasan Kode:**
> [Alamat.java menyimpan data alamat berupa jalan, kota, dan provinsi. Atributnya private dan diakses lewat getter dan setter (enkapsulasi). Nilai atribut diisi melalui konstruktor dengan parameter jalan, kota, dan provinsi. Class ini juga punya konstanta NEGARA = "Indonesia" yang bertipe public static final, sehingga nilainya sama untuk semua objek dan tidak bisa diubah. Method toString() di-override untuk menggabungkan seluruh bagian alamat menjadi satu teks. Class ini dipakai oleh RumahSakit dan Pasien.]

**Screenshot Kode:**

<img width="798" height="731" alt="image" src="https://github.com/user-attachments/assets/47d9feb0-1a3d-40a3-a8da-e5801a689e3b" />

### 1.6. File: `Main.java`
**Penjelasan Kode:**
> [Main.java adalah program utama yang menjalankan seluruh alur. Pertama dibuat objek RumahSakit (“RS Sehat Sentosa”) beserta Alamat-nya. Lalu dibuat tiga objek Dokter (d1, d2, d3) yang ditambahkan lewat tambahDokter(). Setelah itu dibuat tiga objek Pasien, masing-masing dengan Alamat dan Penyakit (memakai enum Penyakit.Tingkat). p1 langsung diberi dokter d1 lewat konstruktor, p2 diberi dokter d2 lewat setDokter(), dan p3 tidak diberi dokter sehingga arsipnya menampilkan “Belum ditentukan”. Semua pasien didaftarkan dengan daftarkanPasien(), lalu cetakSemuaArsip() mencetak arsip dokter dan pasien. Terakhir, program menampilkan total pasien (Pasien.getJumlahPasien()) dan total surat (RumahSakit.getTotalSurat()) yang diambil dari variabel static.]

**Screenshot Kode:**

<img width="878" height="777" alt="image" src="https://github.com/user-attachments/assets/c1030704-13b0-4116-8a64-83fb6986555c" />

### Output
**Hasil Running Program (Java):**

<img width="724" height="670" alt="Screenshot 2026-10-22 165631" src="https://github.com/user-attachments/assets/763abba6-4acd-44c3-9489-e06bf01e62e0" />

---

## 3. Kesimpulan
> Proyek kelompok kami untuk OOP nya memilih Java karena dikelmpok kami hampir semuanya lebih paham dengan Java di banding PHP. Dan untuk materi yang kami pakai semua materi dari pertemuan 1-7 yaitu: 01 (Kelas, Objek, dan Enkapsulasi), 02 (Koncstruktor, Static, dan Konstanta), 03 (Pewarisan), 04 (Polimorfisme), 05 (Abstract, Interface, Enum), 06 (Relasi Objek). Proyek kami membuat Admnistrasi Arsip Rumah Sakit yang nantinya hasilnya ada keterangan seperti Nama Rumah Sakit, Penyakit Pasiennya, Dokter yang memeriksanya, nama Pasiennya, Alamat si pasiennya dan pastinya ada Main.Javanya untuk melihatnya outputnya.
