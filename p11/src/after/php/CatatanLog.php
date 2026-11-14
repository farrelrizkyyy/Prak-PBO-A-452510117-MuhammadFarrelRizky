<?php

declare(strict_types=1);

class CatatanLog
{
    public static function catat(PerpustakaanException $e): void
    {
        $dir = __DIR__ . '/../logs';
        if (!is_dir($dir) && !mkdir($dir, 0777, true) && !is_dir($dir)) {
            fwrite(STDERR, "[PERINGATAN] Folder log tidak dapat dibuat\n");
            return;
        }

        $baris = date('Y-m-d H:i:s')
            . ' | ' . $e::class
            . ' | ' . json_encode($e->getData(), JSON_UNESCAPED_UNICODE)
            . ' | ' . $e->getMessage() . PHP_EOL;

        if (file_put_contents($dir . '/perpustakaan.log', $baris, FILE_APPEND) === false) {
            fwrite(STDERR, "[PERINGATAN] Gagal menulis log\n");
        }
    }
}
