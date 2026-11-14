<?php
declare(strict_types=1);

namespace Perpustakaan\Tests;

use PHPUnit\Framework\TestCase;
use Perpustakaan\Model\Buku;
use Perpustakaan\Model\KoleksiBuku;

final class KoleksiBukuTest extends TestCase
{
    private KoleksiBuku $koleksi;

    protected function setUp(): void
    {
        $this->koleksi = new KoleksiBuku();
    }

    // Satu-satunya tempat yang memanggil konstruktor Buku.
    private function buatBuku(string $isbn, string $judul, string $penulis, int $eksemplar = 1): Buku
    {
        return new Buku($isbn, $judul, $penulis, $eksemplar);
    }

    public function testItemYangDisimpanDapatDitemukanKembali(): void
    {
        $this->koleksi->simpan($this->buatBuku('978-1', 'PBO', 'Adi'));

        $hasil = $this->koleksi->cariByIsbn('978-1');
        $this->assertNotNull($hasil);
        $this->assertSame('PBO', $hasil->getJudul());
    }

    public function testMencariIsbnYangTidakAdaMengembalikanNull(): void
    {
        $this->assertNull($this->koleksi->cariByIsbn('tidak-ada'));
    }

    public function testKoleksiBaruBerisiNolItem(): void
    {
        $this->assertCount(0, $this->koleksi);
        $this->assertSame(0, count($this->koleksi));
    }

    public function testFilterHanyaMengembalikanYangLolosPredikat(): void
    {
        $this->koleksi->simpan($this->buatBuku('1', 'Java', 'Adi', 1));
        $this->koleksi->simpan($this->buatBuku('2', 'PHP', 'Adi', 0));

        $tersedia = $this->koleksi->filter(fn(Buku $b) => $b->tersedia());

        $this->assertCount(1, $tersedia);
        $this->assertSame('Java', $tersedia->semua()[0]->getJudul());
    }

    public function testJumlahkanMenghitungTotalDenganBenar(): void
    {
        $this->koleksi->simpan($this->buatBuku('1', 'Java', 'Adi', 3));
        $this->koleksi->simpan($this->buatBuku('2', 'PHP', 'Adi', 2));

        $total = $this->koleksi->jumlahkan(fn(Buku $b) => $b->getJumlahEksemplar());

        $this->assertSame(5.0, $total);
    }

    public function testSimpanIsbnSamaMenimpa(): void
    {
        $this->koleksi->simpan($this->buatBuku('1', 'Java', 'Adi'));
        $this->koleksi->simpan($this->buatBuku('1', 'Java Edisi 2', 'Adi'));

        $this->assertCount(1, $this->koleksi);
        $this->assertSame('Java Edisi 2', $this->koleksi->cariByIsbn('1')->getJudul());
    }

    public function testKelompokkanPerPenulis(): void
    {
        $this->koleksi->simpan($this->buatBuku('1', 'Java', 'Adi'));
        $this->koleksi->simpan($this->buatBuku('2', 'PHP', 'Adi'));
        $this->koleksi->simpan($this->buatBuku('3', 'Laravel', 'Budi'));

        $grup = $this->koleksi->kelompokkan(fn(Buku $b) => $b->getPenulis());

        $this->assertCount(2, $grup['Adi']);
        $this->assertCount(1, $grup['Budi']);
    }

    public function testMapMengembalikanJudul(): void
    {
        $this->koleksi->simpan($this->buatBuku('1', 'Java', 'Adi'));
        $this->koleksi->simpan($this->buatBuku('2', 'PHP', 'Adi'));

        $this->assertSame(['Java', 'PHP'], $this->koleksi->map(fn(Buku $b) => $b->getJudul()));
    }
}
