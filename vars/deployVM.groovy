def call(String imageName) {
    def remoteUser = 'your_user'                
    def remoteHost = 'your.vm.ip.address'       
    def sshCreds = 'ssh-credentials-id'         
    def remoteDir = "/home/${remoteUser}/marketpeak"

    echo "Deploying ${imageName}:${env.TAG} to production VM"

    sshagent([sshCreds]) {
        sh """
            echo "📦 Copying project to ${remoteHost}..."
            ssh -o StrictHostKeyChecking=no ${remoteUser}@${remoteHost} 'rm -rf ${remoteDir}'
            scp -r -o StrictHostKeyChecking=no . ${remoteUser}@${remoteHost}:${remoteDir}

            echo "🚀 Running docker-compose on ${remoteHost}..."
            ssh -o StrictHostKeyChecking=no ${remoteUser}@${remoteHost} '
                cd ${remoteDir} &&
                docker-compose down &&
                docker-compose build &&
                docker-compose up -d
            '
        """
    }

    echo "✅ Deployed successfully!"
}
