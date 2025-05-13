def call(String imageName) {
    def tag = "${imageName}:${env.TAG}"
    echo "Building Docker image: ${tag}"

    // Build the image
    sh """
        docker build -t ${tag} .
    """

    // Verify if the image exists locally
    sh "docker images | grep ${tag}"

    echo "Docker image built successfully: ${tag}"
}
