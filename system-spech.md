# Spesifikasi Sistem (System Specification) - Petunjuk Arah V2

**Nama Proyek:** Petunjuk Arah (Smart Travel Assistant)
**Versi Dokumen:** 2.0
**Tanggal:** 29 Desember 2024
**Bahasa Pemrograman Utama:** Java (Spring Boot) & Web Responsive (Proposal: Flutter for Mobile)

---

## 1. Pendahuluan

### 1.1 Tujuan
Membangun asisten perjalanan cerdas yang menyediakan navigasi real-time, pencarian lokasi terpadu, manajemen rencana perjalanan (Trip Planner), dan fitur khusus traveler seperti mode offline dan rekomendasi cerdas.

### 1.2 Lingkup Fitur Baru
1. **Navigasi & Rute**: Dukungan rute tercepat, terpendek, hemat biaya, dan rute wisata.
2. **Pencarian Terpadu**: Filter rating, jarak, dan harga untuk Wisata, Hotel, Restoran, dan Fasilitas Umum.
3. **Informasi Detail**: Deskripsi, jam operasional, harga tiket, dan review pengguna.
4. **Traveler Mode**: Cuaca real-time, konversi mata uang, dan pengingat waktu (alarm).
5. **Smart Recommendation**: Itinerary otomatis berdasarkan minat dan lokasi.

---

## 2. Struktur Menu (UI/UX)

- **Home**: Peta interaktif, pencarian cepat, rekomendasi terdekat.
- **Explore**: Penjelajahan destinasi berdasarkan kategori dan filter.
- **Navigation**: Panel kontrol rute dan instruksi arah.
- **Trip Planner**: Manajemen itinerary (Lokasi, Tanggal, Jam).
- **Profile**: Preferensi perjalanan, riwayat, dan favorit.

---

## 3. Fitur Utama (Core Features)

### 3.1 Navigasi & Petunjuk Arah
- **Peta Interaktif**: Integrasi OpenStreetMap/Leaflet dengan posisi GPS real-time.
- **Algoritma Rute**: Pilihan rute berdasarkan preferensi (Waktu, Jarak, Biaya, Wisata).
- **Voice Navigation**: Dukungan multibahasa untuk instruksi arah.

### 3.2 Pencarian & Informasi
- **Geo-Search**: Pencarian POI (Point of Interest) dengan filter mendalam.
- **Content Aggregator**: Menampilkan info lengkap tempat (Foto, Video, Link).

### 3.3 Mode Traveler & Cerdas
- **Trip Planner Tool**: Membuat dan menyimpan rencana perjalanan harian.
- **Cerdas**: Rekomendasi otomatis menggunakan input minat pengguna.

---

## 4. Stack Teknologi

### 4.1 Backend (Service Provider)
- **Java Spring Boot**: RESTful API untuk menyajikan data lokasi, user, dan itinerary.
- **H2/PostgreSQL**: Penyimpanan data (Migrasi ke tetap jika diperlukan).

### 4.2 Frontend (User Interface)
- **Thymeleaf & Tailwind CSS**: Dashboard web modern dan responsif (Mobile-first).
- **Proposal Mobile**: Flutter/React Native untuk integrasi native GPS dan offline mode lebih baik.

### 4.3 Integrasi API
- **Overpass API (OSM)**: Data peta dan lokasi.
- **OpenWeather API**: Data cuaca.
- **Exchange Rates API**: Konversi mata uang.
