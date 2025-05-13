def call(String imageName) {
    def tag = "${imageName}:${env.TAG}"
    echo "Cleaning up Docker containers and images on Remote VM..."

    sshagent (credentials: [sshCredsId]) {
        sh """
            ssh -o StrictHostKeyChecking=no ${RemoteUser}@${RemoteHost} '
                echo "Cleaning up Docker..."
                docker container prune -f
                docker image prune -f
                echo "Cleanup complete!"
                docker system df
            '
        """
    }
}
