
pipeline {
    agent any

    tools {
        jdk 'JDK-25'
        maven 'Maven-3'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out Order Service source code'
            }
        }

        stage('Build and Test') {
            steps {
                sh './mvnw clean package'
            }
        }
    }
}
