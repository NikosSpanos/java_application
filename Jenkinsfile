pipeline {
    agent {
        node {
            label 'master'
        }
    }
    environment{
        email_address_admin = "spanos.nikolaos@outlook.com" //the email registered to jenkins for the user group Admin
    }
    tools{
        maven "maven-3.6.1"
    }
    stages{
        stage("Changes pushed to features branch.\nReview the commit message and the changes pushed/published by the application developers."){
            when{
                branch "features"
            }
            steps {
                echo 'Fetching changes from features branch'
            }
            post{
                success{
                    mail to: "${env.email_address_admin}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on features branch was executed successfully (Link to job: ${env.BUILD_URL}).\nNew application features uploaded.\nDon't reply to this message."
                }
                failure{
                    mail to: "${env.email_address_admin}",
                    subject: "FAILURE BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on features branch failed (Link to job: ${env.BUILD_URL}).\nDon't reply to this message."
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
                // stage("Execute applications's unit test"){
                //     steps{
                //         sh "mvn test"
                //     }
                // }
                stage("Packaging the .jar file"){
                    steps{
                        input message: 'Do you want to create the .jar application for the development environment? (Click "Proceed" to continue)'
                        sh "mvn clean package -DskipTests"
                        echo "Application .jar file is created for the development environment."
                    }
                }
                stage("Build application docker image in development environment"){
                    environment{
                        docker_user = credentials('Username')
                        docker_pass = credentials('Password')
                    }
                    input{
                        message "Please provide the version tag of the image artifact."
                        ok "Continue!"
                        parameters{
                            string(name: 'image_version_dev', defaultValue:'', description: "Enter a text")
                        }
                    }
                    steps{
                        sh "sudo docker login -u $env.docker_user -p $env.docker_pass"
                        sh "sudo docker build -t nikspanos/cicd-pipeline_dev:${image_version_dev} ."
                        sh "sudo docker push nikspanos/cicd-pipeline_dev:${image_version_dev}"
                    }
                }
            }
            post{
                success{
                    mail to: "${env.email_address_admin}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on development branch was executed successfully. Link to job: ${env.BUILD_URL}\n Image version: ${image_version_dev} is built for deployment/testing of the application.\nDon't reply to this message."
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
                // stage("Execute applications's unit test"){
                //     steps{
                //         sh "mvn test"
                //     }
                // }
                stage("Packaging the .jar file"){
                    steps{
                        input message: 'Do you want to create the .jar application for the production environment?\nBE CAREFULL, ONLY CHANGES APPROVED BY THE SYSTEM ADMIN SHOULD BE PACKAGED\n(Click "Proceed" to continue)'
                        sh "clean package -DskipTests"
                        echo "Application .jar file is created for the production environment."
                    }
                }
                stage("Build application docker image"){
                    environment{
                        docker_user = credentials('Username')
                        docker_pass = credentials('Password')
                    }
                    input{
                        message "Please provide the version tag of the image artifact."
                        ok "Continue!"
                        parameters{
                            string(name: 'image_version_prod', defaultValue:'', description: "Enter a text")
                        }
                    }
                    steps{
                        sh "sudo docker login -u $env.docker_user -p $env.docker_pass"
                        sh "sudo docker build -t nikspanos/cicd-pipeline_prod:${image_version_prod} ."
                        sh "sudo docker push nikspanos/cicd-pipeline_prod:${image_version_prod}"
                    }
                }
            }
            post{
                success{
                    mail to: "${env.email_address_admin}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on production branch was executed successfully. Link to job: ${env.BUILD_URL}\n Image version: ${image_version_prod} is built for production deployment of the application."
                }
                failure{
                    mail to: "${env.email_address_admin}",
                    subject: "FAILURE BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on production branch failed. Link to job: ${env.BUILD_URL}\nDon't reply to this message."
                }
            }
        }
    }
}