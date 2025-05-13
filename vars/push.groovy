def call(String imageName) {
    def tag = "${imageName}:${env.TAG}"

    echo "Preparing to push Docker image: ${tag}"

    withCredentials([usernamePassword(credentialsId: 'docker-hub-jenkins', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh """
            echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin
            docker push ${tag}
        """
    }

    echo "✅ Image pushed: ${tag}"
}
