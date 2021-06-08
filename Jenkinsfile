pipeline {
    agent {
        node {
            label 'master'
        }
    }
    environment{
        email_address_admin = "nspanos@athtech.gr"
        email_address_developer = "sofiazagori@gmail.com"
        image_version_prod = "version5"
        image_version_dev = "version1"
    }
    tools{
        maven "maven-3.6.1"
    }
    stages{
        stage("Changes pushed to features branch. Review the commit message and either accept or discard the changes."){
            when{
                branch "features"
            }
            steps {
                input message: 'Application developer made changes. Do you accept the application changes? (Click "Proceed" to continue)'
            }
            post{
                success{
                    mail to: "${env.email_address_admin}, ${env.email_address_developer}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on features branch was executed successfully. Link to job: ${env.BUILD_URL}\nDon't reply to this message."
                }
                failure{
                    mail to: "${env.email_address_admin}",
                    subject: "FAILURE BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on features branch failed. Link to job: ${env.BUILD_URL}\nDon't reply to this message."
                }
            }
        }
        stage("Deliver for development"){
            when{
                branch "development"
            }
            stages{
                stage("Compile source code to binary"){
                    steps{
                        sh "mvn clean compile"
                    }
                }
                stage("Execute applications's unit test"){
                    steps{
                        sh "mvn test"
                    }
                }
                stage("Packaging the .jar file"){
                    steps{
                        input message: 'Do you want to create the .jar application for the development environment? (Click "Proceed" to continue)'
                        sh "mvn package"
                        echo "Application .jar file is created for the development environment."
                    }
                }
                stage("Build application docker image in development environment"){
                    environment{
                        docker_user = credentials('Username')
                        docker_pass = credentials('Password')
                    }
                    steps{
                        sh "sudo docker login -u $env.docker_user -p $env.docker_pass"
                        sh "sudo docker build -t nikspanos/cicd-pipeline_dev:${env.image_version_dev} ."
                        sh "sudo docker push nikspanos/cicd-pipeline_dev:${env.image_version_dev}"
                    }
                }
            }
            post{
                success{
                    mail to: "${env.email_address_admin}, ${env.email_address_developer}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on development branch was executed successfully. Link to job: ${env.BUILD_URL}\nDon't reply to this message."
                }
                failure{
                    mail to: "${env.email_address_admin}",
                    subject: "FAILURE BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on development branch failed. Link to job: ${env.BUILD_URL}\nDon't reply to this message."
                }
            }
        }
        stage("Deploy for production"){
            when{
                branch "production"
            }
            stages{
                stage("Compile source code to binary"){
                    steps{
                        sh "mvn clean compile"
                    }
                }
                stage("Execute applications's unit test"){
                    steps{
                        sh "mvn test"
                    }
                }
                stage("Packaging the .jar file"){
                    steps{
                        input message: 'Do you want to create the .jar application for the production environment?\nBE CAREFULL, ONLY CHANGES APPROVED BY THE SYSTEM ADMIN SHOULD BE PACKAGED\n(Click "Proceed" to continue)'
                        sh "mvn package" //or mvn clean package? since we run 'mvn clean' on top we don't need 'mvn clean package', comment 2: pass the database_link and database_port as arguments in maven package
                        echo "Application .jar file is created for the production environment."
                    }
                }
                stage("Build application docker image"){
                    environment{
                        docker_user = credentials('Username')
                        docker_pass = credentials('Password')
                    }
                    steps{
                        sh "sudo docker login -u $env.docker_user -p $env.docker_pass"
                        sh "sudo docker build -t nikspanos/cicd-pipeline_prod:${env.image_version_prod} ."
                        sh "sudo docker push nikspanos/cicd-pipeline_prod:${env.image_version_prod}"
                    }
                }
            }
            post{
                success{
                    mail to: "${env.email_address_admin}, ${env.email_address_developer}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on features branch was executed successfully. Link to job: ${env.BUILD_URL}\nDon't reply to this message."
                }
                failure{
                    mail to: "${env.email_address_admin}",
                    subject: "FAILURE BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on development branch failed. Link to job: ${env.BUILD_URL}\nDon't reply to this message."
                }
            }
        }
    }
}