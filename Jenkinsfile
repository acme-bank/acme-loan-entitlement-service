node {

    stage('Environmnet setup') {
        echo 'Setup environmnet...'

        env.APPLICATION_NAME = env.JOB_NAME.replaceAll("/.*", "")
        env.FORMATTED_BRANCH_NAME = env.BRANCH_NAME.replaceAll("[^A-Za-z0-9-]", "_").toLowerCase()

        if (env.BRANCH_NAME == 'master') {
            env.DOCKER_PREFIX = 'docker.acme.com/release/'
            env.DOCKER_SUFFIX = 'release'
        } else {
            env.DOCKER_PREFIX = 'docker.acme.com/snapshot/'
            env.DOCKER_SUFFIX = 'snapshot'
        }

        env.DOCKER_TAG = "${DOCKER_PREFIX}${APPLICATION_NAME}:${DOCKER_SUFFIX}"
        env.DOCKER_BRANCH_TAG = "${DOCKER_PREFIX}${APPLICATION_NAME}:${FORMATTED_BRANCH_NAME}-${BUILD_NUMBER}"

        sh 'env | sort'
    }

    stage ('Checkout the code') {
        echo 'Checkout code...'

        checkout scm
    }

    stage('Build') {
        echo 'Building code...'

        sh 'mvn clean verify'
    }

    stage('Test') {
        echo 'Testing code...'
    }

    stage('Deploy Maven Artifact') {
        echo 'Deploying artifact...'

        sh 'mvn deploy'
    }

    stage('Docker build') {
        echo 'Building Docker image...'

        sh 'docker build --tag ${DOCKER_TAG} --tag ${DOCKER_BRANCH_TAG} --build-arg APPLICATION_NAME=${APPLICATION_NAME} .'
    }

    stage('Docker push') {
        echo 'Pushing Docker image...'

        sh 'docker push ${DOCKER_TAG}'
        sh 'docker push ${DOCKER_BRANCH_TAG}'
    }
}