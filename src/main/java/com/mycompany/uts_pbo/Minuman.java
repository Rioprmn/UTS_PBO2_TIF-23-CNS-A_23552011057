package com.mycompany.uts_pbo;

public class Minuman extends MenuItem {
    public Minuman(int id, String nama, double harga) {
        super(id, nama, harga, "Minuman");
    }

    @Override
    public void displayInfo() {
        System.out.println("Minuman: " + nama + " - Rp" + harga);
    }

    @Override
    public double getTotalHarga() {
        return harga; // Misalnya nanti bisa dikurangi diskon
    }
}