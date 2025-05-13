def call(String imageName) {
    def tag = "${imageName}:${env.TAG}"

    withCredentials([
        string(credentialsId: 'Remote_Host', variable: 'Remote_Host'),
        string(credentialsId: 'Remote_User', variable: 'Remote_User')
    ]) {
        echo "Deploying Docker image: ${tag} to VM"

        sshagent (credentials: ['ssh-vm-creds-id']) {
            sh """
                # Copy docker-compose.yaml to remote VM
                scp -o StrictHostKeyChecking=no docker-compose.yaml ${Remote_User}@${Remote_Host}:/home/${Remote_User}/docker-compose.yaml

                # Connect to remote and deploy
                ssh -o StrictHostKeyChecking=no ${Remote_User}@${Remote_Host} '
                    export TAG=${env.TAG} &&
                    docker pull ${tag} &&
                    docker compose -f /home/${Remote_User}/docker-compose.yaml down || true &&
                    docker compose -f /home/${Remote_User}/docker-compose.yaml up -d
                '
            """
        }

        echo "✅ Docker image deployed to VM: ${tag}"
    }
}
