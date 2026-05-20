Feature: My Students Page End-to-End Tests

Background:
    Given Saya sudah login sebagai teacher
    And Saya berada di halaman "My Students"

Scenario: Menambahkan nilai (Add Grade) melalui halaman My Students
    When Saya mencari student bernama "Kobbie Mainoo" di tabel
    And Saya klik tombol "Add Grade" pada baris student tersebut
    Then Saya diarahkan ke halaman "Grade Management"
    And Saya memilih student "Kobbie Mainoo" pada dropdown Student
    And Saya memilih subject "Mathematics" pada dropdown Subject
    And Saya memilih classroom "Grade 1 - Section 2" pada dropdown Classroom
    And Saya mengisi nilai "95" dengan tipe "Exam"
    And Saya mengisi remarks "Kerja bagus"
    And Saya klik tombol Add Grade biru
    Then Muncul notifikasi sukses dan nilai masuk ke tabel Recent Grades
    When Saya klik tombol Delete pada baris nilai student "Kobbie Mainoo"
    And Saya konfirmasi pop-up delete
    Then Muncul notifikasi "Grade deleted successfully!"

Scenario: Melihat nilai (View Grades) melalui halaman My Students
    When Saya mencari student bernama "Kobbie Mainoo" di tabel
    And Saya klik tombol "View Grades" pada baris student tersebut
    Then Saya diarahkan ke halaman "Grade Management"
    And Saya bisa melihat tabel "Recent Grades"
