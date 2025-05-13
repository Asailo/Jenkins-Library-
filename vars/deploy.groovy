def call(String imageName) {
    def tag = "${imageName}:${env.TAG}"
    echo "Deploying Docker image: ${tag} to VM"

    sshagent (credentials: ['ssh-vm-creds-id']) {
        sh """
            ssh -o StrictHostKeyChecking=no ${VM_USER}@${VM_HOST} '
                docker pull ${tag} &&
                docker-compose -f resource/common/docker-compose.yml down || true &&
                docker-compose -f resource/common/docker-compose.yml up -d
            '
        """
    }

    echo "Docker image deployed successfully to the VM: ${tag}"
}

