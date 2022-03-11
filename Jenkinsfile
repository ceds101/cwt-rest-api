pipeline {
    agent { any { image 'maven:3.8.4-openjdk-11-slim' } }
    stages {
        stage('Clean') {
            steps {
                bat 'mvn clean'
            }
        }
        
        stage('Build') {
            steps {
                bat 'mvn compile'
            }
        }
        
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        
        stage('Package') {
            steps {
                bat 'mvn package -DskipTests'
            }
        }
        
        stage('Deploy') {
        	steps {
                bat 'mvn deploy -DaltDeploymentRepository=snapshot-repo::default::file:C:/cwt-rest-api-final-jar -DskipTests'
            }
        }
        
    }
}