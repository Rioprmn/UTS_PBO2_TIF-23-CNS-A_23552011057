package com.mycompany.uts_pbo;

public abstract class MenuItem {
    protected int id;
    protected String nama;
    protected double harga;
    protected String jenis;

    public MenuItem(int id, String nama, double harga, String jenis) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.jenis = jenis;
    }

    public abstract void displayInfo();
    public abstract double getTotalHarga(); // Polimorfisme di sini

    public int getId() { return id; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public String getJenis() { return jenis; }
}
