def call(String kubeconfigPath = "${env.WORKSPACE}/admin.conf", String k8sDir = "k8s") {
    withCredentials([file(credentialsId: 'kubeconfig-prod', variable: 'KUBECONFIG_FILE')]) {
        sh """
            echo '[INFO] Copying kubeconfig to workspace'
            cp \$KUBECONFIG_FILE ${kubeconfig-prodPath}
            export KUBECONFIG=${kubeconfig-prodPath}
            echo '[INFO] Applying Kubernetes deployment and service files'
            kubectl apply -f ${k8sDir}/deployment.yaml
            kubectl apply -f ${k8sDir}/service.yaml
        """
    }
}
