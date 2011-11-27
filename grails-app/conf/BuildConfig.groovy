grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.dependency.resolution = {
    inherits "global"
    log "warn"
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
    }
    dependencies {
    }
    plugins{
        test ":spock:0.5-groovy-1.7"
        build ":codenarc:0.16.1"
    }
    
    codenarc.reports = {
        StockAnalysis('html') { 
            outputFile = 'CodeNarc-Report.html' 
            title = 'Stock installation CodeNarc Report' 
        }
    }
}
