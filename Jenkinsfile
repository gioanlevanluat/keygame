pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn clean install'
            }
        }

	    stage('Deploy') { 
            steps {
                echo '${WORKSPACE}'
                sh 'cp target/bookcar-0.0.1-SNAPSHOT.jar /home/bookcar.deploy/'
                sh 'sh /home/bookcar.deploy/deploy-bookcar-cmd.sh'
            }
        }
    }
}
