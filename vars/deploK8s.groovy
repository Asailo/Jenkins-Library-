def call(String imageName) {
    withCredentials([file(credentialsId: 'kubeconfig-prod', variable: 'KUBECONFIG_FILE')]) {
        sh """
            echo '[INFO] Copying kubeconfig to workspace'
            cp \$KUBECONFIG_FILE ./kubeconfig-prod.yaml
            export KUBECONFIG=./kubeconfig-prod.yaml
            echo '[INFO] Applying Kubernetes deployment and service files'
            kubectl apply -f k8s/deployment.yaml
            kubectl apply -f k8s/service.yaml
        """
    }
}
