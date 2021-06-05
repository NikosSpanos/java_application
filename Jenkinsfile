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
                        echo "The output is: ${OUTOUT}"
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
                    mail to: "${email_address}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on production branch was executed successfully. Link to job: ${env.BUILD_URL}"
                }
                failure{
                    mail to: "${email_address}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on production branch was executed successfully. Link to job: ${env.BUILD_URL}"
                }
            }
        }
        stage("Development branch"){
            when{
                branch "development"
            }
            stages{
                stage("Clean old mvn output"){
                    steps{
                        sh "mvn clean"
                        input_message: "Finished cleaning old mvn output (Click 'Proceed' to continue)"
                    }
                }
                stage("Execute applications's unit test"){
                    steps{
                        sh "mvn test"
                        input_message: "Finished application's unit test (Click 'Proceed' to continue)"
                    }
                }
                stage("Compile source code to binary"){
                    steps{
                        sh "mvn compile"
                        input_message: "Finished application's compilation (Click 'Proceed' to continue)"
                    }
                }
                stage("Packaging the .jar file"){
                    steps{
                        sh "mvn package" //or mvn clean package? since we run 'mvn clean' on top we don't need 'mvn clean package', comment 2: pass the database_link and database_port as arguments in maven package
                        input_message: "Finished application's packaging (Click 'Proceed' to continue)"
                    }
                }
            }
            post{
                success{
                    mail to: "${email_address}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on development branch was executed successfully. Link to job: ${env.BUILD_URL}"
                }
                failure{
                    mail to: "${email_address}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on development branch was executed successfully. Link to job: ${env.BUILD_URL}"
                }
            }
        }
    }
}