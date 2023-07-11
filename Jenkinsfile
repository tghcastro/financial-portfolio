pipeline {
    agent any
    stages {
        stage('Build Service') {
            steps {
                sh './gradlew build --exclude-task test'
            }
        }
        stage('Unit Tests') {
            steps {
                sh './gradlew :stocks-service:unitTests'
                junit 'stocks-service/build/unit-tests/xml/*.xml'
            }
        }
        stage('Integration Tests') {
            steps {
                sh './gradlew :stocks-service:integrationTests'
                junit 'stocks-service/build/integration-tests/xml/*.xml'
            }
        }
    }
}