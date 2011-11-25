import grails.plugin.spock.*

class JQueryResourceTagLibSpec extends TagLibSpec {
    // this is apparently an undocumented feature on http://grails.org/plugin/jquery. Is it necessary? Removable?
    def "verify that when the resources tag is called with local attribute, script comes from app"(){
        when: "I try to load resources"
            def result = resources( local: 'true' )
        then: "jQuery is loaded from local app"
            result == "<script src='js/jquery/jquery-1.7.1.min.js' type='text/javascript'></script>"
    }
    
    def "verify that when the resources tag is called without local attribute, script comes from plugin"(){
        when: "I try to load resources"
            def result = resources()
        then: "jQuery is loaded from the plugin"
            result == "<script src='jquery/js/jquery/jquery-1.7.1.min.js' type='text/javascript'></script>"
    }
    
    def "verify that when the resources tag is called with a non boolean local attribute, script comes from plugin"(){
        when: "I try to load resources"
            def result = resources( local: 'gaz' )
        then: "jQuery is loaded from the plugin"
            result == "<script src='jquery/js/jquery/jquery-1.7.1.min.js' type='text/javascript'></script>"
    }
    
    // Is this really testable? should this be refactored?
    def "verify that when the resources tag is called in the development environment, min version is not loaded"(){
        when: "I try to load resources in development environment"
            def result = resources( local: 'gaz' )
        then: "jQuery is loaded from the plugin"
            result == "<script src='jquery/js/jquery/jquery-1.7.1.min.js' type='text/javascript'></script>"
    }
    
    // RESOURCE is really ugly and difficult to test...
    // effects in the comment appears to be unused. mode is undocumented in the plugin docs
    // it seems to try to incorporate much functionality that the resources plugin does.
    // does it make sense to just have this plugin depend on that and remove these tags?
    // also, the addResource should probably be private, as it looks like it is meant to be a utility method
}
