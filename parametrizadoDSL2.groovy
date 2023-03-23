job('Ejemplo2-job-DSL'){
  	description("Job DSL de ejemplo en Jenkins")
  	scm {
        git('https://github.com/alvaro2042/jenkins.git', 'main') { node -> 
            node / gitConfigName('alvaro2042')
            node / gitConfigEmail('acamargo@lucasian.com')
        }
    }
  	parameters {
    	stringParam('nombre', defaultValue = 'Alvaro', description = 'Parametro de cadena para el Job Booleano')
      	choiceParam('planeta', ['Mercurio', 'Marte', 'Tierra', 'Saturno','Pluton', 'Urano'])
      	booleanParam('agente', false)
    }
    triggers {
      	cron('H/1 * * * *')
        githubPush()
    }
    steps {
      	shell("bash jobscript.sh")
    }
  	publishers {
    	mailer('acamargo@lucasian.com', true, true)
      	slackNotifier{
        	notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(false)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
        }
    }
}
