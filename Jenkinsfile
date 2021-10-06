
pipeline {
    agent any
    environment {
        serviceName = 'dev-transaction'
        awsRegion = 'us-east-2'
        mavenProfile = 'dev'
        commitIDShort = sh(returnStdout: true, script: "git rev-parse --short HEAD")
        organizationName = 'SSEastCoders'
        appEnv = 'jtdo'
        servicePort = 8224
        healthPath = '/api/v1/transactions/health'
    }
    stages {
        stage('Clean and Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        // stage('SonarQube Analysis') {
        //     steps {
        //         withSonarQubeEnv('sonarScanner') {
        //             sh 'mvn sonar:sonar'
        //         }
        //     }
        // }
        // stage('Quality Gate') {
        //     steps {
        //         timeout(time: 10, unit: 'MINUTES') {
        //             waitForQualityGate abortPipeline: true
        //         }
        //     }
        // }
        stage('Maven Build') {
            steps {
                sh 'mvn clean package -P ${mavenProfile} -Dskiptests'
            }
        }
        stage('Docker Image Build and ECR Image Push') {
            steps {
                withCredentials([string(credentialsId: 'awsAccountNumber', variable: 'awsID')]) {
                    sh '''
                        # authenticate aws account
                        aws ecr get-login-password --region ${awsRegion} | docker login --username AWS --password-stdin ${awsID}.dkr.ecr.${awsRegion}.amazonaws.com
                        docker build -t ${awsID}.dkr.ecr.${awsRegion}.amazonaws.com/${serviceName}:${commitIDShort} .
                        docker push ${awsID}.dkr.ecr.${awsRegion}.amazonaws.com/${serviceName}:${commitIDShort}
                        docker build -t ${awsID}.dkr.ecr.${awsRegion}.amazonaws.com/${serviceName}:latest .
                        docker push ${awsID}.dkr.ecr.${awsRegion}.amazonaws.com/${serviceName}:latest
                    '''
                }
            }
        }
        stage("Deploy") {

            steps {
                sh '''
                    aws cloudformation deploy \
                    --stack-name ${serviceName}-stack \
                    --template-file deploystack.yml \
                    --parameter-overrides \
                        AppEnv=${appEnv} \
                        AppName=${organizationName} \
                        ServiceName=${serviceName} \
                        ServicePort=${servicePort} \
                        HealthPath=${healthPath} \
                    --capabilities CAPABILITY_NAMED_IAM \
                    --no-fail-on-empty-changeset
                '''
            }
        }
    }
    post {
        success {
            sh 'docker image prune -af'
        }
    }
}