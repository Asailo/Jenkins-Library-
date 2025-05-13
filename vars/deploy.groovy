
def call(String imageName) {
    def tag = "${imageName}:${env.TAG}"

    withCredentials([
        string(credentialsId: 'vm-host', variable: 'Remote_Host'),
        string(credentialsId: 'vm-user', variable: 'Remote_User')
    ])    
    echo "Deploying Docker image: ${tag} to VM"

    sshagent (credentials: ['ssh-vm-creds-id']) {
        sh """
            ssh -o StrictHostKeyChecking=no ${Remote_User}@${Remote_Host} '
                docker pull ${tag} &&
                docker-compose -f resource/common/docker-compose.yml down || true &&
                docker-compose -f resource/common/docker-compose.yml up -d
            '
        """
    }

    echo "Docker image deployed successfully to the VM: ${tag}"
}
