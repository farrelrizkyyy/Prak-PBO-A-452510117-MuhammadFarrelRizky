<?php
declare(strict_types=1);

namespace Perpustakaan\Tests;

use AnggotaTidakDitemukanException;
use BatasPinjamTerlampauiException;
use BukuSedangDipinjamException;
use BukuTidakDitemukanException;
use LibraryService;
use PHPUnit\Framework\TestCase;

require_once __DIR__ . '/../LibraryService.php';

/** Skenario 1-5 dari skenario-uji.md (sesi 10) + laporan fungsional sesi 11. */
final class LibraryServiceTest extends TestCase
{
    private LibraryService $service;

    protected function setUp(): void
    {
        $this->service = new LibraryService();
        $this->service->tambahBuku('978-1', 'Basis Data', 1, 'Adi');
        $this->service->tambahBuku('978-2', 'Algoritma', 3, 'Adi');
        $this->service->tambahBuku('978-3', 'Jaringan', 1, 'Budi');
        $this->service->daftarkanAnggota('A-001');
        $this->service->daftarkanAnggota('A-002');
    }

    // ---- Skenario sesi 10 ----

    public function testSkenario1BukuTidakDitemukan(): void
    {
        $this->expectException(BukuTidakDitemukanException::class);
        $this->expectExceptionMessage('Buku dengan ISBN 978-9 tidak ditemukan');

        $this->service->pinjam('A-001', '978-9');
    }

    public function testSkenario2AnggotaTidakDitemukan(): void
    {
        $this->expectException(AnggotaTidakDitemukanException::class);
        $this->expectExceptionMessage('Anggota dengan ID A-009 tidak ditemukan');

        $this->service->pinjam('A-009', '978-1');
    }

    public function testSkenario3BukuSedangDipinjam(): void
    {
        $this->service->pinjam('A-001', '978-1');

        $this->expectException(BukuSedangDipinjamException::class);
        $this->expectExceptionMessage('Buku Basis Data dengan ISBN 978-1 sedang dipinjam');

        $this->service->pinjam('A-002', '978-1');
    }

    public function testSkenario4BatasPinjamTerlampaui(): void
    {
        $this->service->pinjam('A-001', '978-2');
        $this->service->pinjam('A-001', '978-2');
        $this->service->pinjam('A-001', '978-2');

        $this->expectException(BatasPinjamTerlampauiException::class);
        $this->expectExceptionMessage('Anggota dengan ID A-001 telah mencapai batas maksimum peminjaman (3 buku)');

        $this->service->pinjam('A-001', '978-3');
    }

    public function testSkenario5AnggotaDiperiksaLebihDulu(): void
    {
        $this->expectException(AnggotaTidakDitemukanException::class);

        $this->service->pinjam('A-009', '978-9');
    }

    // ---- Perilaku normal dan koleksi ----

    public function testPinjamBerhasilMengurangiStok(): void
    {
        $this->service->pinjam('A-001', '978-2');

        $this->assertSame(2, $this->service->sisaEksemplar('978-2'));
    }

    public function testTambahBukuDenganIsbnSamaMenjumlahkanStok(): void
    {
        $this->service->tambahBuku('978-1', 'Basis Data', 2, 'Adi');

        $this->assertSame(3, $this->service->sisaEksemplar('978-1'));
    }

    public function testCariBukuTidakAdaMengembalikanNullDanJudulCadangan(): void
    {
        $this->assertNull($this->service->cariBuku('978-9'));
        $this->assertSame('(tidak ada)', $this->service->judulDari('978-9'));
    }

    // ---- Laporan fungsional ----

    public function testLaporanBukuTersedia(): void
    {
        $this->service->pinjam('A-001', '978-1');   // stok 978-1 menjadi 0

        $tersedia = $this->service->bukuTersedia();

        $this->assertCount(2, $tersedia);
        $this->assertSame(['978-2', '978-3'], array_map(fn($b) => $b->getNomorInduk(), $tersedia));
    }

    public function testLaporanDaftarJudul(): void
    {
        $this->assertSame('Basis Data, Algoritma, Jaringan', $this->service->daftarJudul());
    }

    public function testLaporanJumlahPerPenulis(): void
    {
        $this->assertSame(['Adi' => 2, 'Budi' => 1], $this->service->jumlahPerPenulis());
    }
}
