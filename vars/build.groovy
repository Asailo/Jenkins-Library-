def call(String imageName) {
    def tag = "${imageName}:${env.BUILD_NUMBER}"
    echo "Building Docker image: ${tag}"

    sh """
        docker build -t ${tag} .
    """

    echo "Docker image built successfully: ${tag}"
}
