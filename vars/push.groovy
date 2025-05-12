def call(String imageName) {
    def tag = "${imageName}:${env.BUILD_NUMBER}"

    echo "Pushing Docker image: ${tag} to Docker Hub"

    sh """
        docker push ${tag}
    """

    echo "Image pushed: ${tag}"
}

