Feature: Manage User - Edit User

Scenario: Admin successfully edit an existing user
    Given Admin sudah login dan berada di halaman Manage Users
    When Admin klik tombol Edit pada user "johndoe123"
    And Admin mengubah first name menjadi "Johnny"
    And Admin klik tombol Update User
    Then Data user "johndoe123" seharusnya terupdate dengan first name "Johnny"
