pipeline {
    agent any

    tools {
        maven 'maven' // Pastikan nama tool 'maven' sudah di-config di Jenkins -> Global Tool Configuration
        jdk 'jdk21'   // Pastikan nama tool 'jdk21' sudah di-config di Jenkins -> Global Tool Configuration
    }

    environment {
        // Jika butuh environment variable khusus, tambahkan di sini
        // Contoh: BROWSER = 'chrome-headless'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout sudah otomatis jika menggunakan 'Pipeline from SCM'
                echo 'Checking out source code...'
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Running Maven Tests...'
                sh 'mvn clean test'
            }
        }
    }

    post {
        always {
            echo 'Archiving Test Results...'
            // Archive Cucumber HTML reports yang di-generate oleh maven-cucumber-reporting
            publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/cucumber-report-html',
                reportFiles: 'index.html',
                reportName: 'Cucumber HTML Report'
            ])
            
            // Archive JUnit/TestNG results for Jenkins graphs
            junit 'target/surefire-reports/*.xml'
        }
        success {
            echo 'Build and Test Passed!'
        }
        failure {
            echo 'Build or Test Failed!'
        }
    }
}
