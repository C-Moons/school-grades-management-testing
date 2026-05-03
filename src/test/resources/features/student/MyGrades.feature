Feature: Test Case My Grades Student 
    Scenario Outline: My Grades Student 
    
    Given Saya buka browser dan akses halaman login.
    When Saya input username "student" dan password "student123" login.
    And  tampilan Dashboard.
    Then Pilih menu My Grades.
