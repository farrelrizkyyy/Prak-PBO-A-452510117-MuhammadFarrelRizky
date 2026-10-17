<?php
declare(strict_types=1);

// ══ INTERFACE — kontrak "apa yang bisa dilakukan" ═══════════════
interface Movable
{
    public function bergerak(): void;
    public function kecepatanMaksimum(): float;
}

interface Fuelable
{
    public function isiBahanBakar(float $jumlah): void;
    public function kapasitasTangki(): float;
    public function tipeBahanBakar(): TipeBahanBakar;
}

// ══ ENUM (PHP 8.1+) — backed enum, punya nilai string ══════════
enum TipeBahanBakar: string
{
    case Bensin  = 'bensin';
    case Solar   = 'solar';
    // TODO 1 (Langkah 2): tambahkan case Listrik = 'listrik';
    case Listrik = 'listrik';

    /** TODO 2: kembalikan label yang enak dibaca. Petunjuk: match ($this) { ... } */
    public function label(): string
    {
        return match ($this) {
            self::Bensin => 'Bensin',
            self::Solar => 'Solar',
            self::Listrik => 'Listrik',
        };
    }

    /** TODO 3: Bensin 12000 · Solar 10500 · Listrik 2500 */
    public function hargaPerSatuan(): float
    {
        return match ($this) {
            self::Bensin => 12000,
            self::Solar => 10500,
            self::Listrik => 2500,
        };
    }

    /** TODO 4 */
    public function biayaPengisian(float $jumlah): float
    {
        return $jumlah * $this->hargaPerSatuan();
    }

    /** TODO 5: hanya Listrik yang ramah lingkungan. */
    public function ramahLingkungan(): bool
    {
        return $this === self::Listrik;
    }
}

// ══ TRAIT — penggunaan ulang horizontal, khas PHP ══════════════
trait Loggable
{
    /**
     * TODO 6: cetak baris log berformat:
     *         [14:32:05] Mobil: servis berkala selesai
     *
     * Petunjuk: static::class memberi nama kelas yang memakai trait ini.
     */
    public function log(string $pesan): void
    {
        // TODO 6
        $waktu = date('H:i:s');
        $namaKelas = static::class;
        echo"[$waktu] $namaKelas: $pesan" . PHP_EOL;
    }
}

// ══ ABSTRACT CLASS — kode yang benar-benar sama ═══════════════
abstract class Kendaraan
{
    public function __construct(
        protected readonly string $merek,
        protected readonly int    $tahun,
    ) {}

    /** TODO 7: umur kendaraan, tidak boleh negatif. */
    public function umur(int $tahunSekarang): int
    {
        $umur = $tahunSekarang - $this->tahun;
        return max(0, $umur);
    }

    abstract public function jumlahRoda(): int;

    public function __toString(): string
    {
        return sprintf('%s (%d, %d roda)', $this->merek, $this->tahun, $this->jumlahRoda());
    }
}

final class Mobil extends Kendaraan implements Movable, Fuelable {
    use Loggable; // trait disisipkan

    private float $isiTangki = 0;

    public function __construct(
        string $merek, 
        int $tahun, 
        private readonly float $kapasitasTangki // DIBETULKAN: sebelumnya $kapasitas
    ) {
        parent::__construct($merek, $tahun);
    }

    public function jumlahRoda(): int { 
        return 4; 
    }

    // TODO 8: lengkapi kontrak Movable dan Fuelable.
    public function bergerak(): void {
        echo "{$this->merek} melaju di jalan raya" . PHP_EOL;
    }

    public function kecepatanMaksimum(): float {
        return 180.0;
    }

    public function isiBahanBakar(float $jumlah): void {
        if ($jumlah <= 0) return; // DIBETULKAN: sebelumnya < 0
        $this->isiTangki = min($this->kapasitasTangki, $this->isiTangki + $jumlah);
    }

    public function kapasitasTangki(): float {
        return $this->kapasitasTangki;
    }

    public function tipeBahanBakar(): TipeBahanBakar {
        return TipeBahanBakar::Bensin;
    }

    public function getIsiTangki(): float {
        return $this->isiTangki;
    }
}

// TODO Langkah 4: buat Sepeda — extends Kendaraan implements Movable,
//                 TETAPI BUKAN Fuelable.
class Sepeda extends Kendaraan implements Movable
{
    public function jumlahRoda(): int { return 2; }

    public function bergerak(): void {
        echo "{$this->merek} dikayuh di jalan raya" . PHP_EOL;
    }

    public function kecepatanMaksimum(): float {
        return 30.0; 
    }
}

/**
 * TODO Langkah 5: buat kelas Pesanan yang juga memakai trait Loggable.
 * Kelas ini sama sekali bukan kerabat Kendaraan — itulah maksud
 * "penggunaan ulang horizontal".
 */
class Pesanan
{
    use Loggable;
}