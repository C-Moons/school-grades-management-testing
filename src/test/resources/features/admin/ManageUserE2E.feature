Feature: Manage User - End to End Flow

Scenario: Admin manage user life cycle (Create, Edit Name, Edit Password, Delete)
    Given Admin sudah login dan berada di halaman Manage Users
    
    # 1. Create User
    When Admin mengisi first name "Super"
    And Admin mengisi last name "Admin"
    And Admin mengisi username "the_ultimate_tester"
    And Admin mengisi email "ultimate_tester_999@testmail.com"
    And Admin memilih role "admin"
    And Admin mengisi password "password123"
    And Admin klik tombol Add User
    Then User baru "the_ultimate_tester" seharusnya muncul di daftar tabel
    
    # 2. Edit Name
    When Admin klik tombol Edit pada user "the_ultimate_tester"
    And Admin mengubah first name menjadi "Johnny"
    And Admin klik tombol Update User
    Then Data user "the_ultimate_tester" seharusnya terupdate dengan first name "Johnny"
    
    # 3. Edit Password
    When Admin klik tombol password pada user "the_ultimate_tester"
    And Admin mengisi new password menjadi "newpassword123"
    And Admin mengisi confirm password menjadi "newpassword123"
    And Admin klik tombol change password
    Then Password user sukses diubah
    
    # 4. Delete User
    When Admin klik tombol delete pada user "the_ultimate_tester"
    Then Admin klik ok untuk hapus data