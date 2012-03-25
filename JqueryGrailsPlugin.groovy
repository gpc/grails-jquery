/*
 * Copyright 2007-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import grails.util.GrailsUtil
import org.codehaus.groovy.grails.plugins.web.taglib.*
import org.codehaus.groovy.grails.plugins.jquery.JQueryConfig
import org.codehaus.groovy.grails.plugins.jquery.JQueryProvider
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class JqueryGrailsPlugin {
    // Only change the point release. Edit o.c.g.g.o.j.JQueryConfig.SHIPPED_VERSION when changing jQuery resource version
    // This should match JQueryConfig.SHIPPED_VERSION but must be a literal here due to how AstPluginDescriptorReader parses this file
    def version = "1.7.2"

    static SHIPPED_SRC_DIR = 'jquery'

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.2.2 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

        def title = "JQuery for Grails" // Headline display name of the plugin
    def author = "Sergey Nebolsin, Craig Jone, Marc Palmer, Konstantinos Kostarellis and Finn Herpich"
    def authorEmail = "nebolsin@gmail.com, craigjones@maximsc.com, marc@grailsrocks.com, kosta.grails@gmail.com and finn.herpich@marfinn-software.de"
    def description = '''Provides integration for the JQuery library with grails JavascriptProvider'''
    def documentation = "http://grails.org/plugin/jquery"
    
    static jQueryVersion
    static jQuerySources

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPJQUERY" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/gpc/grails-jquery" ]

    void loadConfig() {
        GroovyClassLoader classLoader = new GroovyClassLoader(getClass().getClassLoader())
        def confClass
        try {
            confClass = classLoader.loadClass('JQueryConfig')
        } catch (Exception e) {
            // <gulp>
        }
        ConfigObject config = confClass ? new ConfigSlurper().parse(confClass).merge(ConfigurationHolder.config) : ConfigurationHolder.config

        JqueryGrailsPlugin.jQueryVersion = config.jquery.version instanceof String ? config.jquery.version : JQueryConfig.SHIPPED_VERSION
        JqueryGrailsPlugin.jQuerySources = config.jquery.sources instanceof String ? config.jquery.sources : JqueryGrailsPlugin.SHIPPED_SRC_DIR
    }

    def doWithSpring = {
        jQueryConfig(JQueryConfig)
    }

    def doWithApplicationContext = { applicationContext ->
        // We need to init our own config first
        loadConfig()

        if(GrailsUtil.isDevelopmentEnv()) {
            JavascriptTagLib.LIBRARY_MAPPINGS.jquery = ["${JqueryGrailsPlugin.jQuerySources}/jquery-${JqueryGrailsPlugin.jQueryVersion}"]
        } else {
            JavascriptTagLib.LIBRARY_MAPPINGS.jquery = ["${JqueryGrailsPlugin.jQuerySources}/jquery-${JqueryGrailsPlugin.jQueryVersion}.min"]
        }

        def jQueryConfig = applicationContext.jQueryConfig
        jQueryConfig.init()

        if(jQueryConfig.defaultPlugins) {
            jQueryConfig.defaultPlugins.each { pluginName ->
                jQueryConfig.plugins."$pluginName".each {fileName ->
                    JavascriptTagLib.LIBRARY_MAPPINGS.jquery += ["${JqueryGrailsPlugin.jQuerySources}/${fileName}"[0..-4]]
                }
            }
        }

        JavascriptTagLib.PROVIDER_MAPPINGS.jquery = JQueryProvider.class
    }
}
