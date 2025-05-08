# UTS Pemrograman Berorientasi Obyek 2
<ul>
  <li>Mata Kuliah: Pemrograman Berorientasi Obyek 2</li>
  <li>Dosen Pengampu: <a href="https://github.com/Muhammad-Ikhwan-Fathulloh">Muhammad Ikhwan Fathulloh</a></li>
</ul>

## Profil
<ul>
  <li>Nama: {Rio Permana}</li>
  <li>NIM: {23552011057}</li>
  <li>Studi Kasus: {Kasir Restoran}</li>
</ul>

## Judul Studi Kasus
<p>Kasir Restoran</p>

## Penjelasan Studi Kasus
<p>Program ini di buat untuk mengelola pesanan,pembayaran makanan dan minuman di sebuah kasir restoran</p>

## Penjelasan 4 Pilar OOP dalam Studi Kasus

### 1. Inheritance
<p>public class Makanan extends MenuItem {
extends artinya Makanan mewarisi semua properti dan method dari class MenuItem
Class Makanan otomatis memiliki
Property: id, nama, harga, jenis (dari protected di MenuItem, bisa diakses di subclass)
Method: getId(), getNama(), getHarga(), getJenis()
</p>

### 2. Encapsulation
<p>class DetailPesanan ini menggunakan encapsulation.
Data (atribut) disembunyikan (private)
Diakses melalui method getter/setter (public)
Method getSubtotal() juga bagian dari encapsulation secara fungsional
Karena menyembunyikan logika perhitungan total dan hanya mengembalikan hasil.
</p>

### 3. Polymorphism
<p> Polimorfisme-nya ada di class MenuItem dan cara penggunaannya melalui objek-objek seperti Makanan dan Minuman.
Makanan dan Minuman meng-override method ini
  @Override
public double getTotalHarga() {
    return harga;
}

</p>

### 4. Abstract
<p>abstract berarti kelas ini tidak bisa dibuat objek secara langsung.
Hanya bisa diwarisi oleh kelas turunan seperti Makanan atau Minuman.
Ini digunakan untuk membuat kerangka dasar (template) yang akan digunakan bersama oleh semua jenis menu.
</p>

## Demo Proyek
<ul>
  <li>Github: <a href="">https://github.com/Rioprmn/UTS_PBO2_TIF-23-CNS-A_23552011057</a></li>
  <li>Youtube: <a href="">Youtube</a></li>
</ul>
