package com.mycompany.uts_pbo;
import java.util.ArrayList;
import java.util.List;

public class Pesanan {
    private int id;
    private String meja;
    private String status;
    private List<DetailPesanan> detailList;

    public Pesanan(int id, String meja, String status) {
        this.id = id;
        this.meja = meja;
        this.status = status;
        this.detailList = new ArrayList<>();
    }

    // Getter & Setter
    public int getId() { return id; }
    public String getMeja() { return meja; }
    public String getStatus() { return status; }
    public List<DetailPesanan> getDetailList() { return detailList; }

    public void addDetail(DetailPesanan detail) {
        detailList.add(detail);
    }

    public double getTotalHarga() {
        double total = 0;
        for (DetailPesanan detail : detailList) {
            total += detail.getSubtotal();
        }
        return total;
    }
}