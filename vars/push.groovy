def call(String imageName) {
    def tag = "${imageName}:${env.TAG}"

    withCredentials([usernamePassword(credentialsId: 'dockerhub-creds-id', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh """
            echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin
            docker push ${tag}
            docker logout
        """
    }

    echo "Image pushed: ${tag}"
}
