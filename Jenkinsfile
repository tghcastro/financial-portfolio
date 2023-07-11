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
//         stage('Component Tests') {
//             steps {
//                 sh './gradlew :stocks-service:bootrun >application.log 2>&1 &'
//                 sh './tools/wait-for-it/wait-for-it.sh localhost:8081 -t 60 -- echo "Service is Up!" && ./gradlew :stocks-service:componentTests'
//                 sh 'pkill -9 -f stocks-service:bootrun'
//                 junit 'stocks-service/build/component-tests/xml/*.xml'
//             }
//         }
    }
}