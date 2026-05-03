Feature: Manage User - Edit Password User

Scenario: Admin successfully edit password an existing user
    Given Admin sudah login dan berada di halaman Manage Users
    When Admin klik tombol password pada user "johndoe123"
    And Admin mengisi new password menjadi "Johnny123"
    And Admin mengisi confirm password menjadi "Johnny123"
    And Admin klik tombol change password
    Then Password user sukses diubah
