pipeline {
    agent any
    
    environment {
        TERRAFORM_HOME = "C:\\terraform"  // Use the path where Terraform is installed on the agent
        PATH = "${env.PATH};${TERRAFORM_HOME}" // Append Terraform path to the existing PATH
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', credentialsId: 'e2eaeead-2515-43df-af7e-f18717db87fd', url: 'https://github.com/sathiyamohan/sample_code.git'
            }
        }
        stage("set env variable"){
            steps{
                bat 'set AWS_PROFILE=sathiya'
            }
        }
        stage('Get Directory') {
            steps{
                println(WORKSPACE)
            }
        }
        stage('Terraform init'){
            steps{
                bat 'terraform init'
            }
        }
        stage('Terraform Apply'){
            steps{
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: "1f5bd398-226c-4656-9718-24072cff1f7a",
                    accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                    secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                bat 'terraform apply --auto-approve'
                }
        
            }
       }
    }
}