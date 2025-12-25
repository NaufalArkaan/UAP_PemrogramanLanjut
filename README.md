# 🧪 Sistem Monitoring Kerusakan Peralatan Laboratorium Informatika

Aplikasi **Sistem Monitoring Kerusakan Peralatan Laboratorium Informatika** merupakan aplikasi berbasis **Java Swing** yang digunakan untuk mencatat, memantau, serta mengelola laporan kerusakan peralatan di Laboratorium Informatika.
Aplikasi ini dikembangkan sebagai pemenuhan **Ujian Akhir Praktikum (UAP) Mata Kuliah Pemrograman Lanjut**.

---

## 👩‍💻 Developer

* **Naufal Arkaan** — *Fullstack Developer*
* **Muhamad Robi Ardita** — *Fullstack Developer*

**Mata Kuliah:** Pemrograman Lanjut

**Program Studi:** Teknik Informatika

---

## 🎯 Tujuan Aplikasi

* Mempermudah mahasiswa dalam melaporkan kerusakan peralatan laboratorium.
* Membantu admin laboratorium dalam memantau, mengelola, dan merekap data kerusakan.
* Menyediakan riwayat laporan serta ringkasan data secara terstruktur dan tersimpan permanen.

---

## 🖥️ Teknologi yang Digunakan

* **Bahasa Pemrograman:** Java
* **GUI:** Java Swing
* **Penyimpanan Data (File Handling):**

    * File CSV (`laporan.csv`)
    * File JSON (`users.json`)
* **IDE:** IntelliJ IDEA
* **Version Control:** Git & GitHub

---

## 📂 Struktur Project

```
UAP_PemrogramanLanjut/
├── data/
│   ├── foto/
│   ├── laporan.csv
│   └── users.json
├── src/
│   ├── controller/
│   ├── model/
│   ├── util/
│   └── view/
│       ├── admin/
│       ├── auth/
│       ├── components/
│       └── user/
├── README.md
└── Main.java
```

---

## 🔐 Akun Login (Default)

### 👨‍💼 Admin

* **Username:** `admin`
* **Password:** `admin123`
* **Hak Akses:**

    * Mengelola data peralatan laboratorium
    * Melihat seluruh laporan kerusakan
    * Melakukan operasi CRUD (Create, Read, Update, Delete)
    * Melihat dashboard dan laporan ringkasan

### 👨‍🎓 Mahasiswa

* **Username:** `mahasiswa`
* **Password:** `123`
* **Hak Akses:**

    * Mengirim laporan kerusakan peralatan
    * Melihat riwayat laporan pribadi
    * Mengakses dashboard pengguna

📌 **Catatan:**
Data akun disimpan secara permanen menggunakan **file handling berbasis JSON**, sehingga tetap tersedia meskipun aplikasi ditutup dan dijalankan kembali.

---

## 🧩 Fitur Utama

### 1️⃣ Autentikasi

* Login menggunakan username dan password
* Hak akses berbeda antara Admin dan Mahasiswa

### 2️⃣ Dashboard

* Menampilkan ringkasan jumlah laporan
* Menampilkan statistik kondisi peralatan
* Navigasi ke halaman utama aplikasi

### 3️⃣ Manajemen Data (CRUD)

* Menambah data laporan kerusakan
* Menampilkan daftar laporan dalam bentuk tabel
* Mengedit dan menghapus data laporan
* Fitur **sorting** dan **searching** pada tabel data

### 4️⃣ Laporan & Riwayat

* Menampilkan riwayat laporan kerusakan
* Menampilkan detail laporan per item
* Penyimpanan data laporan secara permanen ke file CSV

### 5️⃣ File Handling

* Data laporan disimpan di file `laporan.csv`
* Data pengguna disimpan di file `users.json`
* Data tetap tersimpan walaupun aplikasi ditutup

---

## 📑 Halaman / Screen Aplikasi

Aplikasi memiliki minimal **4 halaman**, sesuai dengan ketentuan UAP:

1. **Halaman Login**
2. **Halaman Dashboard**
3. **Halaman List Data (Tabel + Sorting & Searching)**
4. **Halaman Input (Form Tambah/Edit Data)**
5. **Halaman Laporan / Riwayat**

---

## ⚙️ Cara Menjalankan Program

1. Clone repository:

   ```bash
   git clone <url-repository>
   ```
2. Buka project menggunakan **IntelliJ IDEA**
3. Pastikan folder `data/` berada di root project
4. Jalankan file:

   ```
   Main.java
   ```

---

## 🧪 Skenario Pengujian (Manual Testing)

### Pengujian Fitur CRUD

1. Login sebagai **Mahasiswa**
2. Tambahkan laporan kerusakan melalui form input
3. Pastikan data:

    * Muncul pada tabel aplikasi
    * Tersimpan pada file `laporan.csv`
4. Login sebagai **Admin**
5. Lakukan edit dan hapus data laporan
6. Pastikan perubahan tersimpan dengan benar

### Pengujian Error Handling

* Input kosong → sistem menampilkan pesan error
* Input angka tidak valid → ditangani menggunakan `try-catch`
* File tidak ditemukan → ditangani dengan exception handling

---

## 🧹 Code Review

Beberapa aspek yang diperhatikan dalam pengembangan kode:

* Penamaan variabel dan class bersifat deskriptif dan konsisten
* Penerapan konsep **MVC (Model–View–Controller)**
* Menghindari duplikasi kode
* Pemanfaatan utility class seperti:

    * `CsvLaporanHelper`
    * `JsonHelper`
    * `UIStyle`

---

## 📌 Kesimpulan

Aplikasi **Sistem Monitoring Kerusakan Peralatan Laboratorium Informatika** berhasil mengimplementasikan:

* Graphical User Interface (Java Swing)
* Operasi CRUD
* File Handling (CSV & JSON)
* Fitur Sorting & Searching
* Exception Handling
* Struktur kode yang rapi dan terorganisir

Dengan demikian, aplikasi ini telah **memenuhi seluruh spesifikasi teknis Ujian Akhir Praktikum Pemrograman Lanjut**.

---