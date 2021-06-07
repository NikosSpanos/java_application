pipeline {
    agent {
        node {
            label 'master'
        }
    }
    environment{
        email_address_admin = "nspanos@athtech.gr"
        email_address_developer = "sofiazagori@gmail.com"
        home_directory_cicd = "/home/codehubTeam5"
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
                input message: 'Do you accept the application changes? (Click "Proceed" to continue)'
            }
            post{
                success{
                    mail to: "${env.email_address_admin}, ${env.email_address_developer}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on features branch was executed successfully. Link to job: ${env.BUILD_URL}\nDon't reply to this message."
                }
                failure{
                    mail to: "${env.email_address_admin}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on features branch was executed successfully. Link to job: ${env.BUILD_URL}\nDon't reply to this message."
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
            }
            post{
                success{
                    mail to: "${env.email_address_admin}, ${env.email_address_developer}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on development branch was executed successfully. Link to job: ${env.BUILD_URL}\nDon't reply to this message."
                }
                failure{
                    mail to: "${env.email_address_admin}",
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on development branch was executed successfully. Link to job: ${env.BUILD_URL}\nDon't reply to this message."
                }
            }
        }
        stage("Deploy for production"){
            when{
                branch "production"
            }
            stages{
                stage("Packaging the .jar file"){
                    steps{
                        sh "mvn package" //or mvn clean package? since we run 'mvn clean' on top we don't need 'mvn clean package', comment 2: pass the database_link and database_port as arguments in maven package
                        echo "Application .jar file is created."
                    }
                }
                stage("Copy the created .jar file to home directory for docker deployment"){
                    steps{
                        fileOperations([fileCopyOperation(
                            excludes: '',
                            flattenFiles: false,
                            includes: "/var/lib/jenkins/workspace/cicd-pipeline_production/target/*.jar",
                            targetLocation: "${env.home_directory_cicd}"
                        )])
                        //sh "#!/bin/bash\necho $HOME\nsudo cp /var/lib/jenkins/workspace/cicd-pipeline_production/target/toDoAppWithLogin.jar ${env.home_directory_cicd}"
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
                    subject: "SUCCESSFUL BUILD: ${env.BUILD_TAG}",
                    body: "Your pipeline job on development branch was executed successfully. Link to job: ${env.BUILD_URL}\nDon't reply to this message."
                }
            }
        }
    }
}