def call(String imageName) {
    def tag = "${imageName}:${env.BUILD_NUMBER}"
    echo "Scanning Docker image for vulnerabilities: ${tag}"

    withCredentials([string(credentialsId: 'snyk-api-token', variable: 'SNYK_TOKEN')]) {
        sh """
            snyk config set api=$SNYK_TOKEN
            snyk container test ${tag}
        """
    }

    echo "Snyk scan completed for: ${tag}"
}

