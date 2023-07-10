pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh './gradlew build --exclude-task test'
            }
        }
        stage('Unit Tests') {
            steps {
                sh './gradlew :stocks-service:test --tests "unit.*"'
            }
            junit keepLongStdio: true, testResults: '/stocks-service/build/test-results/*.xml'
        }
        stage('Integration Tests') {
            steps {
                sh './gradlew :stocks-service:test --tests "integration.*"'
            }
            junit keepLongStdio: true, testResults: '/stocks-service/build/test-results/*.xml'
        }
    }
}