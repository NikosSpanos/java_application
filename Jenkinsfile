pipeline {
    agent {
        node {
            label 'master'
        }
    }
    environment{
        email_address_admin = "nspanos@athtech.gr"
        email_address_developer = "sofiazagori@gmail.com"
        image_version = "version4"
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
        boolean packagePassed = true
        stage("Deploy for production"){
            when{
                branch "production"
            }
            stages{
                stage("Packaging the .jar file"){
                        try{
                            steps{
                                sh "mvn package" //or mvn clean package? since we run 'mvn clean' on top we don't need 'mvn clean package', comment 2: pass the database_link and database_port as arguments in maven package
                                echo "Application .jar file is created."
                            }
                        } catch(Exception e){
                            packagePassed = false
                    }
                }
                stage("Build application docker image"){
                    node {
                            if(packagePassed){
                                def newApp = docker.build("nikspanos/cicd-pipeline:${env.image_version}", ".")
                                newApp.push()
                        }
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