<?php

declare(strict_types=1);

abstract class BangunDatar
{
    public function __construct(private readonly string $nama) {}

    abstract public function luas(): float;
    abstract public function keliling(): float;

    public function getNama(): string
    {
        return $this->nama;
    }

    public function __toString(): string
    {
        return sprintf(
            '%-12s luas=%10.2f  keliling=%10.2f',
            $this->nama,
            $this->luas(),
            $this->keliling()
        );
    }
}

class Lingkaran extends BangunDatar
{
    public function __construct(private readonly float $jariJari)
    {
        parent::__construct('Lingkaran');
        if ($jariJari <= 0) {
            throw new InvalidArgumentException("Jari-jari harus lebih besar dari 0");
        }
    }

    public function luas(): float
    {
        return M_PI * $this->jariJari * $this->jariJari;
    }
    public function keliling(): float
    {
        return 2 * M_PI * $this->jariJari;
    }

    public function getJariJari(): float
    {
        return $this->jariJari;
    }
}

class Persegi extends BangunDatar
{
    public function __construct(private readonly float $sisi)
    {
        parent::__construct('Persegi');
        if ($sisi <= 0) {
            throw new InvalidArgumentException("Sisi harus lebih besar dari 0");
        }
    }

    public function luas(): float
    {
        return $this->sisi * $this->sisi;
    }
    public function keliling(): float
    {
        return 4 * $this->sisi;
    }
}

class Segitiga extends BangunDatar
{
    public function __construct(
        private readonly float $sisiA,
        private readonly float $sisiB,
        private readonly float $sisiC
    ) {
        parent::__construct('Segitiga');
        if ($sisiA <= 0 || $sisiB <= 0 || $sisiC <= 0) {
            throw new InvalidArgumentException("Sisi segitiga harus lebih besar dari 0");
        }
        // Validasi pertidaksamaan segitiga
        if (($sisiA + $sisiB <= $sisiC) || ($sisiA + $sisiC <= $sisiB) || ($sisiB + $sisiC <= $sisiA)) {
            throw new InvalidArgumentException("Panjang sisi tidak dapat membentuk sebuah segitiga yang valid");
        }
    }

    public function keliling(): float
    {
        return $this->sisiA + $this->sisiB + $this->sisiC;
    }

    public function luas(): float
    {
        // Menggunakan rumus Heron
        $s = $this->keliling() / 2;
        return sqrt($s * ($s - $this->sisiA) * ($s - $this->sisiB) * ($s - $this->sisiC));
    }
}

class Trapesium extends BangunDatar
{
    public function __construct(
        private readonly float $sisiAtas,
        private readonly float $sisiBawah,
        private readonly float $tinggi,
        private readonly float $sisiMiring1,
        private readonly float $sisiMiring2
    ) {
        parent::__construct('Trapesium');
        if ($sisiAtas <= 0 || $sisiBawah <= 0 || $tinggi <= 0 || $sisiMiring1 <= 0 || $sisiMiring2 <= 0) {
            throw new InvalidArgumentException("Dimensi trapesium harus lebih besar dari 0");
        }
    }

    public function luas(): float
    {
        return 0.5 * ($this->sisiAtas + $this->sisiBawah) * $this->tinggi;
    }

    public function keliling(): float
    {
        return $this->sisiAtas + $this->sisiBawah + $this->sisiMiring1 + $this->sisiMiring2;
    }
}
