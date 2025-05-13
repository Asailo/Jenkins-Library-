def call(String imageName) {
    def tag = "${imageName}:${env.TAG}"

    withCredentials([
        string(credentialsId: 'Remote_Host', variable: 'Remote_Host'),
        string(credentialsId: 'Remote_User', variable: 'Remote_User')
    ]) {
        echo "🧹 Cleaning up Docker containers and images on Remote VM..."

        sshagent (credentials: ['ssh-vm-creds-id']) {
            sh """
                ssh -o StrictHostKeyChecking=no ${Remote_User}@${Remote_Host} '
                    echo "Stopping and removing all Docker containers..." &&
                    docker container prune -f &&
                    
                    echo "Removing dangling Docker images..." &&
                    docker image prune -f &&
                    
                    echo "Showing Docker disk usage summary..." &&
                    docker system df
                '
            """
        }

        echo "✅ Cleanup complete on Remote VM: ${Remote_Host}"
    }
}
