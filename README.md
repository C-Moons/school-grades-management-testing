# 🎓 School Grades Management - Automation Testing

![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=Selenium&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-41B883?style=for-the-badge&logo=Cucumber&logoColor=white)

Repository ini berisi framework automation testing untuk aplikasi **School Grades Management** menggunakan Selenium WebDriver, Cucumber (BDD), dan TestNG. Project ini sudah terintegrasi dengan CI/CD menggunakan Jenkins.

---

## 📝 Tentang Proyek
Proyek ini bertujuan untuk memastikan kualitas dan keandalan sistem **Manajemen Nilai Sekolah**. Fokus utama pengujian meliputi:
*   **Keamanan & Autentikasi:** Verifikasi login untuk Admin dan Student.
*   **Manajemen Data (CRUD):** Pengujian fitur tambah, edit, dan hapus pada data User dan Mata Pelajaran (Subjects).
*   **Akurasi Data:** Memastikan nilai dan performa siswa tampil dengan benar sesuai database.

Dengan menggunakan pendekatan **BDD (Behavior Driven Development)**, skenario pengujian ditulis dalam bahasa yang mudah dimengerti (Gherkin), sehingga memudahkan kolaborasi antara QA, Developer, dan Stakeholder lainnya.

---

## 🏗️ Apa itu Jenkins?
**Jenkins** adalah sebuah *open-source automation server* yang membantu otomatisasi bagian dari pengembangan software yang berkaitan dengan membangun (*building*), menguji (*testing*), dan menyebarkan (*deploying*), memfasilitasi *continuous integration* (CI) dan *continuous delivery* (CD). 

Dalam proyek ini, Jenkins berperan sebagai:
*   **Execution Engine:** Menjalankan test secara otomatis setiap ada perubahan kode.
*   **Centralized Reporting:** Menyediakan satu tempat untuk melihat hasil test (Cucumber Reports).
*   **Quality Gate:** Memastikan kode yang masuk ke repository tetap stabil.

---

## 📂 Dokumentasi
Dokumentasi detail terkait penggunaan, hasil pengujian, dan contoh implementasi dapat ditemukan di folder [Dokumentasi/](./Dokumentasi/).

### 📊 Hasil Pengujian (Cucumber Report)
Berikut adalah contoh tampilan report yang dihasilkan oleh automation ini:
![Cucumber Report](./Dokumentasi/cucumber%20report.png)

### 📹 Demonstrasi Video
Klik gambar di bawah untuk memutar video demonstrasi jalannya pipeline di Jenkins:

[![Demo Video Jenkins](./Dokumentasi/cover%20demo.png)](https://drive.google.com/file/d/1A6W8Ryp0OyYDZvbjmDxSIzMo7lS9MtiW/view?usp=drive_link)

---


## 🚀 Tech Stack
*   **Language:** Java 21
*   **Build Tool:** Maven
*   **Automation:** Selenium WebDriver 4.27.0
*   **Testing Framework:** Cucumber 7.15.0 & TestNG
*   **Reporting:** 
    *   Cucumber HTML Report
    *   Maven Cucumber Reporting (Masterthought)
    *   Extent Reports

---

## 📁 Project Structure
```text
.
├── Jenkinsfile              # Pipeline configuration for CI/CD
├── pom.xml                  # Project dependencies & plugin management
├── src
│   └── test
│       ├── java             # Step definitions, Runner, and Hooks
│       └── resources
│           └── features     # Gherkin feature files (.feature)
│           └── testng.xml   # TestNG suite configuration
└── target                   # Build artifacts & reports (auto-generated)
```

---

## 🛠️ How to Run Locally

### Prerequisites
*   JDK 21 installed
*   Maven installed
*   Chrome Browser

### Run via Terminal
Jalankan perintah berikut di root directory project:
```bash
mvn clean test
```

---

## ⚙️ Jenkins Integration (CI/CD)

Project ini menggunakan **Jenkins Pipeline** (`Jenkinsfile`) untuk menjalankan automation secara otomatis.

### Konfigurasi di Jenkins:
1.  **Tools:** Pastikan JDK 21 dan Maven sudah terdaftar di *Global Tool Configuration*.
2.  **Plugin:** Install plugin `HTML Publisher` dan `Pipeline`.
3.  **Pipeline:**
    *   Definition: `Pipeline script from SCM`
    *   SCM: `Git`
    *   Repository URL: `https://github.com/C-Moons/school-grades-management-testing.git`
    *   Branch: `*/main`
    *   Script Path: `Jenkinsfile`

### Test Reports
Setelah build selesai, Anda dapat melihat report langsung di Jenkins:
*   **Cucumber HTML Report:** Tersedia di sidebar sebelah kiri project Jenkins.
*   **Surefire Reports:** Grafik trend hasil test.

---

## 📊 Automation Scenarios
Project ini mencakup pengujian untuk modul:
*   **Student:** Login, Cek Nilai, Lihat Performance.
*   **Admin:** Login, Manage User (CRUD), Manage Subjects (CRUD).

---

## 👨‍💻 Author
**Hendra Tri Ardiansyah**
