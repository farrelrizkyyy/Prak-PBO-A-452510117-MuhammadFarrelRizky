<?php

declare(strict_types=1);

/**
 * Langkah 6 — latihan mandiri.
 *
 * Buat hierarki Notifikasi dengan tiga turunan: Email, SMS, WhatsApp.
 * Lalu lengkapi kirimSemua() TANPA satu pun pemeriksaan tipe.
 */

// TODO 1: buat kelas abstrak Notifikasi
abstract class Notifikasi
{
    public function __construct(protected readonly string $tujuan) {}

    abstract public function kirim(string $pesan): void;
    abstract public function saluran(): string;

    public function getTujuan(): string
    {
        return $this->tujuan;
    }
}

// TODO 2: buat tiga turunan: Email, SMS, WhatsApp.
class Email extends Notifikasi
{
    public function kirim(string $pesan): void
    {
        printf("[EMAIL ke %s] Mengirim pesan: %s%s", $this->tujuan, $pesan, PHP_EOL);
    }

    public function saluran(): string
    {
        return 'Email';
    }
}

class SMS extends Notifikasi
{
    public function kirim(string $pesan): void
    {
        printf("[SMS ke %s] Mengirim pesan: %s%s", $this->tujuan, $pesan, PHP_EOL);
    }

    public function saluran(): string
    {
        return 'SMS';
    }
}

class WhatsApp extends Notifikasi
{
    public function kirim(string $pesan): void
    {
        printf("[WHATSAPP ke %s] Mengirim pesan: %s%s", $this->tujuan, $pesan, PHP_EOL);
    }

    public function saluran(): string
    {
        return 'WhatsApp';
    }
}

/**
 * TODO 3: kirim pesan ke seluruh notifikasi dalam daftar.
 *
 * ATURAN: tidak boleh ada instanceof, tidak boleh ada match/switch
 *         atas jenis notifikasi.
 *
 * @param Notifikasi[] $daftar
 */
function kirimSemua(array $daftar, string $pesan): void
{
    foreach ($daftar as $notif) {
        // Polimorfisme memastikan method kirim() yang tepat dipanggil secara otomatis
        $notif->kirim($pesan);
    }
}

// Uji setelah TODO 1-3 selesai:
kirimSemua([
    new Email('ani@univpancasila.ac.id'),
    new SMS('081234567890'),
    new WhatsApp('081234567890'),
], 'Buku yang Anda pesan sudah tersedia.');
