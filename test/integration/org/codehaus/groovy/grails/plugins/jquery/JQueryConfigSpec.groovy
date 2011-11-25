package org.codehaus.groovy.grails.plugins.jquery;

import grails.plugin.spock.*
import org.codehaus.groovy.grails.commons.ApplicationHolder

class JQueryConfigSpec extends IntegrationSpec {
    def "verify our current version is the latest release, 1 7 1"(){
        expect: "our current version is the latest release. Update this test when you update jQuery!"
            new JQueryConfig().SHIPPED_VERSION == "1.7.1"
    }
    def "verify that we can initialize the config with default plugins"(){
        when: "we try to load a config file and build a JQueryConfig"
            // doesn't work. Can't set metadata
            ApplicationHolder.application.config.jquery.defaultPlugins = ["jqueryui", "cycle"]
            def test = new JQueryConfig()
            test.init()
        then: "we should have the plugin info available"
            test.defaultPlugins == ["jqueryui", "cycle"]
    }
    
    def "verify that we can initialize the config with application metadata"(){
        // I have no idea how we can mock the ApplicationHolder the class will use 
        // or dynamically set the metadata (we could insert fake properties in a test, but
        // it doesn't seem that metadata has setters as well as getters
        when: "we try to load an application's metadata and build a JQueryConfig"
            // doesn't work. Can't set metadata
            ApplicationHolder.application.metadata +=["jquery.plugins.foo":"baz"]
            def test = new JQueryConfig()
            test.init()
        then: "we should have the plugin info available"
            test.plugins.foo == ["baz"]
    }
}
