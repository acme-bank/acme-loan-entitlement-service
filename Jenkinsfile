node {
    
    stage ('Checkout the code') {
        checkout scm
    }

    stage('Environmnet setup') {
        env.FORMATTED_BRANCH_NAME = env.BRANCH_NAME.replaceAll("[^A-Za-z0-9-]", "_").toLowerCase()
        sh 'env | sort'
    }
    
    stage('Build') {
        echo 'Building..'
        sh 'mvn clean verify'
    }
    
    stage('Test') {
        echo 'Testing..'
    }
    
    stage('Docker build') {
        echo 'Building docker image'
        sh 'docker-compose -f docker-compose.ci.yml build'
    }

    stage('Docker push') {
        echo 'Pushing docker image'
        sh 'docker-compose -f docker-compose.ci.yml push'
    }
}