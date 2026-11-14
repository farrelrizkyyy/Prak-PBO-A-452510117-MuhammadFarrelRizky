<?php

declare(strict_types=1);

use Perpustakaan\Model\Buku;
use Perpustakaan\Model\KoleksiBuku;

require_once __DIR__ . '/exceptions.php';
require_once __DIR__ . '/vendor/autoload.php';

/** Sesi 11: array paralel diganti KoleksiBuku; laporan memakai operasi fungsional. */
final class LibraryService
{
    public const int BATAS_PINJAM = 3;
    public const string PENULIS_DEFAULT = 'Tidak diketahui';

    // Dulu: array $stok dan array $judul (dua array paralel berkunci ISBN).
    // Sekarang: satu KoleksiBuku, kuncinya nomor induk (ISBN).
    private KoleksiBuku $koleksi;
    /** @var array<string,int> */
    private array $pinjaman = [];

    public function __construct()
    {
        $this->koleksi = new KoleksiBuku();
    }

    public function tambahBuku(
        string $isbn,
        string $judulBuku,
        int $eksemplar,
        string $penulis = self::PENULIS_DEFAULT,
    ): void {
        $stokLama = $this->koleksi->cariByIsbn($isbn)?->getJumlahEksemplar() ?? 0;
        $this->koleksi->simpan(new Buku($isbn, $judulBuku, $penulis, $stokLama + $eksemplar));
    }

    public function daftarkanAnggota(string $idAnggota): void
    {
        $this->pinjaman[$idAnggota] ??= 0;
    }

    /**
     * @throws AnggotaTidakDitemukanException
     * @throws BukuTidakDitemukanException
     * @throws BukuSedangDipinjamException
     * @throws BatasPinjamTerlampauiException
     */
    public function pinjam(string $idAnggota, string $isbn): void
    {
        if (!array_key_exists($idAnggota, $this->pinjaman)) {
            throw new AnggotaTidakDitemukanException($idAnggota);
        }
        $buku = $this->koleksi->cariByIsbn($isbn)
            ?? throw new BukuTidakDitemukanException($isbn);
        if (!$buku->tersedia()) {
            throw new BukuSedangDipinjamException($isbn, $buku->getJudul());
        }
        if ($this->pinjaman[$idAnggota] >= self::BATAS_PINJAM) {
            throw new BatasPinjamTerlampauiException($idAnggota, self::BATAS_PINJAM);
        }

        // logika utama, rata di kiri
        $buku->kurangiStok();
        $this->pinjaman[$idAnggota]++;
    }

    public function cariBuku(string $isbn): ?Buku
    {
        return $this->koleksi->cariByIsbn($isbn);
    }

    public function sisaEksemplar(string $isbn): int
    {
        return $this->koleksi->cariByIsbn($isbn)?->getJumlahEksemplar() ?? 0;
    }

    public function judulDari(string $isbn): string
    {
        return $this->koleksi->cariByIsbn($isbn)?->getJudul() ?? '(tidak ada)';
    }

    /** @return string[] seluruh ISBN, diurutkan agar keluaran ekspor stabil */
    public function semuaIsbn(): array
    {
        $daftar = $this->koleksi->map(fn(Buku $b) => $b->getNomorInduk());
        sort($daftar, SORT_STRING);
        return $daftar;
    }

    // ---------- Laporan fungsional (Langkah 4) ----------

    /** Laporan 1: buku yang tersedia (filter). @return Buku[] */
    public function bukuTersedia(): array
    {
        return $this->koleksi->filter(fn(Buku $b) => $b->tersedia())->semua();
    }

    /** Laporan 2: seluruh judul digabung (map + implode). */
    public function daftarJudul(): string
    {
        return implode(', ', $this->koleksi->map(fn(Buku $b) => $b->getJudul()));
    }

    /**
     * Laporan 3: jumlah buku per penulis, urut abjad (sama dengan TreeMap di Java).
     * @return array<string,int>
     */
    public function jumlahPerPenulis(): array
    {
        $hasil = array_map('count', $this->koleksi->kelompokkan(fn(Buku $b) => $b->getPenulis()));
        ksort($hasil, SORT_STRING);
        return $hasil;
    }
}
