Feature: Subjects - End to End Flow


Scenario: Admin Subjects life cycle (Create, Edit Name, Edit Password, Delete)

    Given Admin sudah login dan berada di halaman Subjects
    
    # 1. Create Subject
    When Admin mengisi subject name "Science_Ultimate"
    And Admin mengisi subject code "SCI_ULT"
    And Admin mengisi description "Science Ultimate Subject"
    And Admin klik tombol Add Subject
    Then Subject baru "Science_Ultimate" seharusnya muncul di daftar tabel
    
    # 2. Edit Name
    When Admin klik tombol Edit pada subject "Science_Ultimate"
    And Admin mengubah subject name menjadi "Science_Ultimate_Updated"
    And Admin klik tombol Update Subject
    Then Data user "Science_Ultimate" seharusnya terupdate dengan name "Science_Ultimate_Updated"
    
    # 3. Delete User
    When Admin klik tombol delete pada name "Science_Ultimate_Updated"
    Then Admin klik ok untuk hapus data subject