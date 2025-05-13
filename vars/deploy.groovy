
def call(String imageName) {
    def tag = "${imageName}:${env.TAG}"

    withCredentials([
        string(credentialsId: 'vm-host', variable: 'Remote_Host'),
        string(credentialsId: 'vm-user', variable: 'Remote_User')
    ]) {    
            echo "Deploying Docker image: ${tag} to VM"

            sshagent (credentials: ['ssh-vm-creds-id']) {
                sh """
                    ssh -o StrictHostKeyChecking=no ${Remote_User}@${Remote_Host} '
                        docker compose -f docker-compose.yaml down || true &&
                        docker compose -f docker-compose.yaml build &&
                        docker compose -f docker-compose.yaml up -d

                    '
                """
            }
        }   

    echo "Docker image deployed successfully to the VM: ${tag}"
}
