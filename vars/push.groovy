def call(String imageName) {
    def tag = "${imageName}:${env.BUILD_NUMBER}"

    withCredentials([usernamePassword(credentialsId: 'dockerhub-creds-id', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh "echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin"
        sh "docker push ${imageName}:${env.BUILD_NUMBER}"
    }


    echo "Image pushed: ${tag}"

}

