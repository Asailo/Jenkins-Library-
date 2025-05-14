def call(String imageName) {
    withCredentials([file(credentialsId: 'kubeconfig-prod', variable: 'KUBECONFIG_FILE')]) {
        sh """
            echo '[INFO] Creating temp directory for kubeconfig'
            mkdir -p \$WORKSPACE/tmp

            echo '[INFO] Copying kubeconfig to temp directory'
            cp \$KUBECONFIG_FILE \$WORKSPACE/tmp/kubeconfig-prod.yaml

            export KUBECONFIG=\$WORKSPACE/tmp/kubeconfig-prod.yaml

            echo '[INFO] Applying Kubernetes deployment and service files'
            kubectl apply -f k8s/deployment.yaml
            kubectl apply -f k8s/service.yaml
        """
    }
}
