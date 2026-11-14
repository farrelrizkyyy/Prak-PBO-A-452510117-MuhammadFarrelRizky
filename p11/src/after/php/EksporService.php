<?php

declare(strict_types=1);

require_once __DIR__ . '/LibraryService.php';

final class EksporService
{
    /**
     * @param int $gagalDiBaris jika > 0, lempar exception tepat sebelum menulis baris ke-N
     * @throws RuntimeException bila berkas tidak dapat dibuka atau simulasi gagal dipicu
     */
    public function eksporDaftarBuku(LibraryService $service, string $tujuan, int $gagalDiBaris = 0): void
    {
        $handle = fopen($tujuan, 'w');
        if ($handle === false) {
            throw new RuntimeException("Berkas {$tujuan} tidak dapat dibuka untuk ditulis");
        }

        try {
            $nomor = 0;
            foreach ($service->semuaIsbn() as $isbn) {
                $nomor++;
                if ($nomor === $gagalDiBaris) {
                    throw new RuntimeException("Simulasi gagal saat menulis baris {$nomor}");
                }
                fwrite($handle, $isbn . ';' . $service->judulDari($isbn) . ';' . $service->sisaEksemplar($isbn) . PHP_EOL);
            }
        } finally {
            // PHP tidak punya try-with-resources: tutup manual di finally
            fclose($handle);
            echo "[INFO] Berkas ditutup.\n";
        }
    }
}
