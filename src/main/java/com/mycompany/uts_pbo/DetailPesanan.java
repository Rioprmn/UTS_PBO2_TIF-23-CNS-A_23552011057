package com.mycompany.uts_pbo;

public class DetailPesanan {
    private MenuItem menuItem;
    private int jumlah;

    public DetailPesanan(MenuItem menuItem, int jumlah) {
        this.menuItem = menuItem;
        this.jumlah = jumlah;
    }

    public MenuItem getMenuItem() { return menuItem; }
    public int getJumlah() { return jumlah; }

    public double getSubtotal() {
        return menuItem.getTotalHarga() * jumlah;
    }
}