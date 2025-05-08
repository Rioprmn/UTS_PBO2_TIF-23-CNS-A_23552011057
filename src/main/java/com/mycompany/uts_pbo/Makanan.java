package com.mycompany.uts_pbo;


public class Makanan extends MenuItem {
    public Makanan(int id, String nama, double harga) {
        super(id, nama, harga, "Makanan");
    }

    @Override
    public void displayInfo() {
        System.out.println("Makanan: " + nama + " - Rp" + harga);
    }

    @Override
    public double getTotalHarga() {
        return harga; // Bisa ditambah pajak/diskon jika diperlukan
    }
}