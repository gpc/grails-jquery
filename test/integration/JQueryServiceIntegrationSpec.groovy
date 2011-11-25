import grails.plugin.spock.*

class JQueryServiceIntegrationSpec extends IntegrationSpec {
    JQueryService jQueryService
    
    def "verify that paths that exist are successfully found"(){
        when: "we try to see if a path exists"
            def test = jQueryService.exist("grails-app", "domain")
        then: "it is successfully found"
            test == true
    }
    
    def "verify that paths that don't exist are successfully identified"(){
        when: "we try to see if a path exists"
            def test = jQueryService.exist("foo", "bar")
        then: "it is not found"
            test == false
    }
}
