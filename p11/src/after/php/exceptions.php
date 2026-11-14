<?php

declare(strict_types=1);

/**
 * Sesi 10 — hierarki exception domain (PHP).
 *
 * PHP tidak mengenal checked exception; seluruh exception setara dengan
 * unchecked di Java. Karena itu anotasi @throws pada PHPDoc menjadi jauh
 * lebih penting: pembaca kode tidak dibantu kompilator.
 */

abstract class PerpustakaanException extends RuntimeException
{
    /** Data penyebab kegagalan (untuk logging dan respons HTTP di sesi 13-14). */
    abstract public function getData(): array;
}

final class BukuTidakDitemukanException extends PerpustakaanException
{
    public function __construct(private readonly string $isbn)
    {
        parent::__construct("Buku dengan ISBN {$isbn} tidak ditemukan");
    }

    public function getIsbn(): string
    {
        return $this->isbn;
    }

    public function getData(): array
    {
        return ['isbn' => $this->isbn];
    }
}

final class AnggotaTidakDitemukanException extends PerpustakaanException
{
    public function __construct(private readonly string $idAnggota)
    {
        parent::__construct("Anggota dengan ID {$idAnggota} tidak ditemukan");
    }

    public function getIdAnggota(): string
    {
        return $this->idAnggota;
    }

    public function getData(): array
    {
        return ['idAnggota' => $this->idAnggota];
    }
}

final class BukuSedangDipinjamException extends PerpustakaanException
{
    public function __construct(
        private readonly string $isbn,
        private readonly string $judul,
    ) {
        parent::__construct("Buku {$judul} dengan ISBN {$isbn} sedang dipinjam");
    }

    public function getIsbn(): string
    {
        return $this->isbn;
    }
    public function getJudul(): string
    {
        return $this->judul;
    }

    public function getData(): array
    {
        return ['isbn' => $this->isbn, 'judul' => $this->judul];
    }
}

final class BatasPinjamTerlampauiException extends PerpustakaanException
{
    public function __construct(
        private readonly string $idAnggota,
        private readonly int $batas,
    ) {
        parent::__construct("Anggota dengan ID {$idAnggota} telah mencapai batas maksimum peminjaman ({$batas} buku)");
    }

    public function getIdAnggota(): string
    {
        return $this->idAnggota;
    }
    public function getBatas(): int
    {
        return $this->batas;
    }

    public function getData(): array
    {
        return ['idAnggota' => $this->idAnggota, 'batas' => $this->batas];
    }
}
