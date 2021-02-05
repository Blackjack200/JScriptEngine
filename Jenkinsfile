timestamps {
    node () {
	    stage ('JScriptEngine - Checkout') {
	        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'github', url: 'https://github.com/Blackjack200/JScriptEngine']]])
	    }
	    stage ('JScriptEngine - Build') {
        sh """
        ./build
        """
        		archiveArtifacts allowEmptyArchive: false, artifacts: 'out/*', caseSensitive: true, defaultExcludes: true, fingerprint: false, onlyIfSuccessful: false
        }
    }
}