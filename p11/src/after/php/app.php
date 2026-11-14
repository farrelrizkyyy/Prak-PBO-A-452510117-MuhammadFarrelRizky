<?php

declare(strict_types=1);

require_once __DIR__ . '/exceptions.php';
require_once __DIR__ . '/LibraryService.php';
require_once __DIR__ . '/EksporService.php';
require_once __DIR__ . '/CatatanLog.php';

date_default_timezone_set('Asia/Jakarta');

/** Baca satu baris; keluar rapi bila input ditutup (EOF). */
function bacaBaris(string $label): string
{
    echo $label . ': ';
    $baris = fgets(STDIN);
    if ($baris === false) {
        echo "\nInput berakhir. Program selesai.\n";
        exit(0);
    }
    return trim($baris);
}

/** Minta teks; ulangi sampai tidak kosong (spasi saja dianggap kosong). */
function bacaTeks(string $label): string
{
    while (true) {
        $teks = bacaBaris($label);
        if ($teks !== '') {
            return $teks;
        }
        echo "[!] {$label} tidak boleh kosong.\n";
    }
}

/** Minta angka bulat dalam rentang [min, maks]; ulangi bila huruf, kosong, atau di luar rentang. */
function bacaAngka(string $label, int $min, int $maks): int
{
    while (true) {
        $teks = bacaTeks($label);
        $angka = filter_var($teks, FILTER_VALIDATE_INT);
        if ($angka === false) {
            echo "[!] '{$teks}' bukan angka yang valid.\n";
            continue;
        }
        if ($angka < $min || $angka > $maks) {
            echo "[!] Masukkan angka {$min} sampai {$maks}.\n";
            continue;
        }
        return $angka;
    }
}

$service = new LibraryService();
$service->tambahBuku('978-1', 'Basis Data', 1);
$service->tambahBuku('978-2', 'Pemrograman Java', 5);
$service->tambahBuku('978-3', 'Struktur Data', 2);
$service->daftarkanAnggota('A-001');
$service->daftarkanAnggota('A-002');

$ekspor = new EksporService();
$berkas = __DIR__ . '/../daftar-buku.txt';

while (true) {
    echo "\n=== PERPUSTAKAAN ===\n1. Pinjam buku\n2. Tambah buku\n3. Ekspor daftar buku\n4. Ekspor dengan simulasi gagal\n0. Keluar\n";
    $pilihan = bacaAngka('Pilih menu', 0, 4);

    if ($pilihan === 0) {
        echo "Sampai jumpa.\n";
        break;
    }

    try {
        switch ($pilihan) {
            case 1:
                $idAnggota = bacaTeks('ID anggota');
                $isbn = bacaTeks('ISBN');
                $service->pinjam($idAnggota, $isbn);
                echo "Berhasil: {$service->judulDari($isbn)} dipinjam oleh {$idAnggota} (sisa {$service->sisaEksemplar($isbn)}).\n";
                break;
            case 2:
                $isbn = bacaTeks('ISBN');
                $judul = bacaTeks('Judul');
                $jumlah = bacaAngka('Jumlah eksemplar', 1, 1000);
                $service->tambahBuku($isbn, $judul, $jumlah);
                echo "Berhasil menambah {$jumlah} eksemplar '{$judul}'.\n";
                break;
            case 3:
                $ekspor->eksporDaftarBuku($service, $berkas);
                echo "Berhasil diekspor ke daftar-buku.txt\n";
                break;
            case 4:
                $ekspor->eksporDaftarBuku($service, $berkas, 2);
                break;
        }
    } catch (PerpustakaanException $e) {   // satu catch untuk SEMUA kegagalan domain
        echo "[GAGAL] {$e->getMessage()}\n";
        // pemeriksaan tipe: ambil data spesifik
        if ($e instanceof BukuTidakDitemukanException) {
            echo "        -> ISBN yang dicari: {$e->getIsbn()}\n";
        } elseif ($e instanceof AnggotaTidakDitemukanException) {
            echo "        -> ID yang dicari: {$e->getIdAnggota()}\n";
        } elseif ($e instanceof BukuSedangDipinjamException) {
            echo "        -> Judul: {$e->getJudul()}, ISBN: {$e->getIsbn()}\n";
        } elseif ($e instanceof BatasPinjamTerlampauiException) {
            echo "        -> Batas: {$e->getBatas()} buku\n";
        }
        CatatanLog::catat($e);
    } catch (RuntimeException $e) {
        echo "[GAGAL] {$e->getMessage()}\n";
    }
}
