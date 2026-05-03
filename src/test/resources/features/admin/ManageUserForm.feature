Feature: Manage User - Add New User

  Scenario: Admin successfully adds a new user
    Given Admin sudah login dan berada di halaman Manage Users
    When Admin mengisi first name "John"
    And Admin mengisi last name "Doe"
    And Admin mengisi username "johndoe123"
    And Admin mengisi email "johndoe@example.com"
    And Admin memilih role "admin"
    And Admin mengisi password "password123"
    And Admin klik tombol Add User
    Then User baru "johndoe123" seharusnya muncul di daftar tabel
