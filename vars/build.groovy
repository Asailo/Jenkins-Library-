// inside vars/build.groovy in Jenkins shared lib
def call(String imageName) {
    // Copy Dockerfile into current workspace
    sh 'cp $JENKINS_HOME/workspace/@libs/Jenkins-Library/resource/common/Dockerfile ./Dockerfile'

    // Build Docker image
    sh "docker build -t ${imageName}:${env.BUILD_NUMBER} -f Dockerfile ."
}
