pipeline {
    agent any
    environment{
        email_address = "nspanos@athtech.gr"
    }
    tools{
        maven "maven-3.6.1"
    }
    stages{
        stage("Main branch"){
            when{
                branch "main"
            }
            stages{
                stage("Input message"){
                    input{
                        message "Do you want to deploy?"
                        ok "Yes!"
                        parameters{
                            string(name: 'OUTPUT', defaultValue:'', description: "Enter a text")
                        }
                    }
                    steps{
                        echo "The output is: ${OUTPUT}"
                    }
                }
                stage("Deployment"){
                    steps{
                        echo "Application image is deployed to production"
                    }
                }
            }
            post{
                success{
                    mail to: "${env.email_address}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on production branch was executed successfully. Link to job: ${env.BUILD_URL}"
                }
                failure{
                    mail to: "${env.email_address}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on production branch was executed successfully. Link to job: ${env.BUILD_URL}"
                }
            }
        }
        stage("Development branch"){
            when{
                branch "dev"
            }
            stages{
                stage("Compile source code to binary"){
                    steps{
                        sh "mvn clean compile"
                        input message: "Finished application's compilation (Click 'Proceed' to continue)"
                    }
                }
                stage("Execute applications's unit test"){
                    steps{
                        sh "mvn test"
                        input message: "Finished application's unit test (Click 'Proceed' to continue)"
                    }
                }
                stage("Packaging the .jar file"){
                    steps{
                        sh "mvn package" //or mvn clean package? since we run 'mvn clean' on top we don't need 'mvn clean package', comment 2: pass the database_link and database_port as arguments in maven package
                        input message: "Finished application's packaging (Click 'Proceed' to continue)"
                    }
                }
            }
            post{
                success{
                    mail to: "${env.email_address}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on development branch was executed successfully. Link to job: ${env.BUILD_URL}"
                }
                failure{
                    mail to: "${env.email_address}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on development branch was executed successfully. Link to job: ${env.BUILD_URL}"
                }
            }
        }
    }
}