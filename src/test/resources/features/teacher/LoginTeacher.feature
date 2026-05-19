Feature: Test Case Login Teacher
    Scenario: Login Teacher
    
    Given Saya buka browser & akses halaman login teacher.
    When Saya input username "teacher" & password "teacher123" klik login.
    Then menampilkan tampilan Dashboard teacher.
    And menampilkan card "My Classrooms" pada Dashboard.
