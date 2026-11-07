<?php

declare(strict_types=1);

require_once __DIR__ . '/exceptions.php';

/** Hasil refactoring LibraryServiceKotor: guard clause + exception yang membawa data. */
final class LibraryService
{
    public const int BATAS_PINJAM = 3;

    /** @var array<string,int> */
    private array $stok = [];
    /** @var array<string,string> */
    private array $judul = [];
    /** @var array<string,int> */
    private array $pinjaman = [];

    public function tambahBuku(string $isbn, string $judulBuku, int $eksemplar): void
    {
        $this->stok[$isbn] = ($this->stok[$isbn] ?? 0) + $eksemplar;
        $this->judul[$isbn] = $judulBuku;
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
        if (!array_key_exists($isbn, $this->stok)) {
            throw new BukuTidakDitemukanException($isbn);
        }
        if ($this->stok[$isbn] <= 0) {
            throw new BukuSedangDipinjamException($isbn, $this->judul[$isbn]);
        }
        if ($this->pinjaman[$idAnggota] >= self::BATAS_PINJAM) {
            throw new BatasPinjamTerlampauiException($idAnggota, self::BATAS_PINJAM);
        }

        // logika utama, rata di kiri
        $this->stok[$isbn]--;
        $this->pinjaman[$idAnggota]++;
    }

    public function sisaEksemplar(string $isbn): int
    {
        return $this->stok[$isbn] ?? 0;
    }

    public function judulDari(string $isbn): ?string
    {
        return $this->judul[$isbn] ?? null;
    }

    /** @return string[] seluruh ISBN, diurutkan agar keluaran ekspor stabil */
    public function semuaIsbn(): array
    {
        $daftar = array_keys($this->stok);
        sort($daftar, SORT_STRING);
        return $daftar;
    }
}
