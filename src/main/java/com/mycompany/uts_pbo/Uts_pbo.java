package com.mycompany.uts_pbo;

import java.sql.*;
import java.util.*;

public class Uts_pbo {

    public static void main(String[] args) {
        Map<Integer, MenuItem> menuMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Ambil data menu dan simpan ke map
            loadMenu(menuMap, conn);

            int pilihan;
            do {
                System.out.println("\n=== MENU UTAMA ===");
                System.out.println("1. Lihat Daftar Menu");
                System.out.println("2. Lihat Semua Pesanan");
                System.out.println("3. Tambah Pesanan Baru");
                System.out.println("4. Bayar Pesanan");
                System.out.println("0. Keluar");
                System.out.print("Pilih menu: ");
                pilihan = scanner.nextInt();
                scanner.nextLine(); // Buang newline

                switch (pilihan) {
                    case 1:
                        tampilkanDaftarMenu(menuMap);
                        break;
                    case 2:
                        tampilkanPesanan(conn, menuMap);
                        break;
                    case 3:
                        inputManual(conn, menuMap);
                        break;
                    case 4:
                        lihatDanBayarPesanan(conn, menuMap);
                        break;
                    case 0:
                        System.out.println("Terima kasih. Program selesai.");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } while (pilihan != 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadMenu(Map<Integer, MenuItem> menuMap, Connection conn) throws SQLException {
        String sql = "SELECT * FROM menu";
        ResultSet rs = conn.createStatement().executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            String nama = rs.getString("nama");
            double harga = rs.getDouble("harga");
            String jenis = rs.getString("jenis");

            MenuItem item = jenis.equalsIgnoreCase("Makanan")
                    ? new Makanan(id, nama, harga)
                    : new Minuman(id, nama, harga);

            menuMap.put(id, item);
        }
    }

    private static void tampilkanDaftarMenu(Map<Integer, MenuItem> menuMap) {
        System.out.println("\n=== Daftar Menu ===");
        System.out.printf("%-5s %-20s %-10s %-10s\n", "ID", "Nama", "Jenis", "Harga");
        System.out.println("-----------------------------------------------");
        for (MenuItem item : menuMap.values()) {
            String jenis = item instanceof Makanan ? "Makanan" : "Minuman";
            System.out.printf("%-5d %-20s %-10s Rp%.0f\n", item.getId(), item.getNama(), jenis, item.getHarga());
        }
    }

    private static void tampilkanPesanan(Connection conn, Map<Integer, MenuItem> menuMap) throws SQLException {
        String pesananSql = "SELECT * FROM pesanan";
        ResultSet psRs = conn.createStatement().executeQuery(pesananSql);

        while (psRs.next()) {
            int id = psRs.getInt("id");
            String meja = psRs.getString("meja");
            String status = psRs.getString("status");

            Pesanan pesanan = new Pesanan(id, meja, status);

            String detailSql = "SELECT * FROM detail_pesanan WHERE pesanan_id = " + id;
            ResultSet dtRs = conn.createStatement().executeQuery(detailSql);

            while (dtRs.next()) {
                int menuId = dtRs.getInt("menu_id");
                int jumlah = dtRs.getInt("jumlah");

                MenuItem item = menuMap.get(menuId);
                if (item != null) {
                    pesanan.addDetail(new DetailPesanan(item, jumlah));
                }
            }

            System.out.println("\nPesanan ID: " + pesanan.getId());
            System.out.println("Meja: " + pesanan.getMeja());
            System.out.println("Status: " + pesanan.getStatus());
            System.out.println("Detail:");
            for (DetailPesanan detail : pesanan.getDetailList()) {
                System.out.println("- " + detail.getMenuItem().getNama() + " x" + detail.getJumlah()
                        + " = Rp" + detail.getSubtotal());
            }
            System.out.println("Total: Rp" + pesanan.getTotalHarga());
            System.out.println("---------------------------");
        }
    }

    private static void inputManual(Connection conn, Map<Integer, MenuItem> menuMap) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Masukkan nomor meja: ");
            String meja = scanner.nextLine();

            System.out.print("Masukkan status pesanan (Belum Bayar/Selesai): ");
            String status = scanner.nextLine();

            String sqlPesanan = "INSERT INTO pesanan (meja, status) VALUES (?, ?)";
            PreparedStatement psPesanan = conn.prepareStatement(sqlPesanan, Statement.RETURN_GENERATED_KEYS);
            psPesanan.setString(1, meja);
            psPesanan.setString(2, status);
            psPesanan.executeUpdate();

            ResultSet rs = psPesanan.getGeneratedKeys();
            int pesananId = -1;
            if (rs.next()) {
                pesananId = rs.getInt(1);
            }

            System.out.println("Masukkan item menu:");
            while (true) {
                System.out.print("ID menu (0 untuk selesai): ");
                int menuId = scanner.nextInt();
                if (menuId == 0) break;

                if (!menuMap.containsKey(menuId)) {
                    System.out.println("ID menu tidak ditemukan.");
                    continue;
                }

                System.out.print("Jumlah: ");
                int jumlah = scanner.nextInt();

                String sqlDetail = "INSERT INTO detail_pesanan (pesanan_id, menu_id, jumlah) VALUES (?, ?, ?)";
                PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
                psDetail.setInt(1, pesananId);
                psDetail.setInt(2, menuId);
                psDetail.setInt(3, jumlah);
                psDetail.executeUpdate();

                System.out.println("Item ditambahkan.");
            }

            System.out.println("Pesanan berhasil disimpan.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void lihatDanBayarPesanan(Connection conn, Map<Integer, MenuItem> menuMap) {
        Scanner scanner = new Scanner(System.in);
        try {
            String sql = "SELECT * FROM pesanan WHERE status = 'Belum Bayar'";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<Integer> daftarId = new ArrayList<>();

            System.out.println("Daftar Pesanan Belum Dibayar:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String meja = rs.getString("meja");
                System.out.println("- ID: " + id + ", Meja: " + meja);
                daftarId.add(id);
            }

            if (daftarId.isEmpty()) {
                System.out.println("Tidak ada pesanan yang perlu dibayar.");
                return;
            }

            System.out.print("Masukkan ID pesanan yang ingin dibayar: ");
            int bayarId = scanner.nextInt();

            if (!daftarId.contains(bayarId)) {
                System.out.println("ID pesanan tidak valid.");
                return;
            }

            // Ambil detail pesanan
            Pesanan pesanan = new Pesanan(bayarId, "", "");
            String detailSql = "SELECT * FROM detail_pesanan WHERE pesanan_id = " + bayarId;
            ResultSet detailRs = conn.createStatement().executeQuery(detailSql);

            while (detailRs.next()) {
                int menuId = detailRs.getInt("menu_id");
                int jumlah = detailRs.getInt("jumlah");

                MenuItem item = menuMap.get(menuId);
                if (item != null) {
                    pesanan.addDetail(new DetailPesanan(item, jumlah));
                }
            }

            System.out.println("Detail Pesanan:");
            for (DetailPesanan detail : pesanan.getDetailList()) {
                System.out.println("- " + detail.getMenuItem().getNama() + " x" + detail.getJumlah()
                        + " = Rp" + detail.getSubtotal());
            }
            System.out.println("Total yang harus dibayar: Rp" + pesanan.getTotalHarga());

            System.out.print("Masukkan jumlah uang: Rp");
            double uang = scanner.nextDouble();

            if (uang < pesanan.getTotalHarga()) {
                System.out.println("Uang tidak cukup.");
            } else {
                double kembalian = uang - pesanan.getTotalHarga();
                System.out.println("Pembayaran berhasil. Kembalian: Rp" + kembalian);

                String updateSql = "UPDATE pesanan SET status = 'Selesai' WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(updateSql);
                ps.setInt(1, bayarId);
                ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
