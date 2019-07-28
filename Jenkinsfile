pipeline {
    agent any
    stages {
        stage('Tests') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn verify'
                    } else {
                        bat 'mvn verify'
                    }
                    archiveArtifacts artifacts: '**', onlyIfSuccessful: false
                }
            }
        }
        stage('Cucumber Report') {
            steps {
                script {
                    cucumber fileIncludePattern: '**/*.json', jsonReportDirectory: 'target/json-cucumber-reports', sortingMethod: 'ALPHABETICAL'
                }
            }
        }
        stage('Live Documentation') {
            steps {
                script {
                    livingDocs featuresDir: 'target/json-cucumber-reports', format: 'ALL', hideScenarioKeyword: true, toc: 'LEFT'
                }
            }
        }
        stage('HTML Report') {
            steps {
                script {
                    publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, escapeUnderscores: false, keepAll: true, reportDir: 'target/generated-report/', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
                }
            }
        }
    }
    post {
        always { 
            step([$class: 'Publisher', reportFilenamePattern: 'target/testng-cucumber-reports/*.xml']
        }
    }
}