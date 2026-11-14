<?php
declare(strict_types=1);

use Perpustakaan\Model\Buku;

require_once __DIR__ . '/LibraryService.php';

$s = new LibraryService();
$s->tambahBuku('978-1', 'Basis Data', 1, 'Adi');
$s->tambahBuku('978-2', 'Algoritma', 3, 'Adi');
$s->tambahBuku('978-3', 'Jaringan', 0, 'Budi');

echo 'Laporan 1 - Buku tersedia : '
    . implode(', ', array_map(fn(Buku $b) => $b->getJudul(), $s->bukuTersedia())) . PHP_EOL;
echo 'Laporan 2 - Semua judul   : ' . $s->daftarJudul() . PHP_EOL;
echo 'Laporan 3 - Buku per penulis:' . PHP_EOL;

$perPenulis = $s->jumlahPerPenulis();
array_walk($perPenulis, fn(int $jumlah, string|int $penulis) => print("  {$penulis}: {$jumlah}" . PHP_EOL));
