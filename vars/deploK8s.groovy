def call(String kubeconfigPath = "${env.WORKSPACE}/admin.conf", String k8sDir = "k8s") {
    withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG_FILE')]) {
        sh """
            echo '[INFO] Copying kubeconfig to workspace'
            cp \$KUBECONFIG_FILE ${kubeconfigPath}
            export KUBECONFIG=${kubeconfigPath}
            echo '[INFO] Applying Kubernetes deployment and service files'
            kubectl apply -f ${k8sDir}/deployment.yaml
            kubectl apply -f ${k8sDir}/service.yaml
        """
    }
}
