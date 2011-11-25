package org.codehaus.groovy.grails.plugins.jquery;

import grails.plugin.spock.*

class JQueryProviderIntegrationSpec extends IntegrationSpec {
    def "verify that ajax forms are prepared successfully for submit tags"(){
        given: "a set of attributes, including forSubmitTag"
            def attrs = ["blah" :"boo", "forSubmitTag" : "true"]
        when: "an ajax form is prepared"
            def test = new JQueryProvider().prepareAjaxForm(attrs)
        then: "the resultant jQuery should serialize the surrounding form"
            test == "jQuery(this).parents('form:first').serialize()".toString()
            attrs.params == "jQuery(this).parents('form:first').serialize()".toString()
    }
    
    def "verify that ajax forms are prepared successfully"(){
        given: "a set of attributes"
            def attrs = [blah :"boo"]
        when: "an ajax form is prepared"
            def test = new JQueryProvider().prepareAjaxForm(attrs)
        then: "the resultant jQuery should serialize the form"
            test == "jQuery(this).serialize()".toString()
            attrs.params == "jQuery(this).serialize()".toString()
    }
    
    // the buildCallback() helper method should probably be private 
    def "verify that remote function works in happy case"(){
        
    }
}
