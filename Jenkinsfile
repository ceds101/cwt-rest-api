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
        
        stage('Code Quality') {
        
        	steps {
                withSonarQubeEnv('My SonarQube Server', envOnly: true) {
				  // This expands the evironment variables SONAR_CONFIG_NAME, SONAR_HOST_URL, SONAR_AUTH_TOKEN that can be used by any script.
				  bat 'mvn sonar:sonar'
				}
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