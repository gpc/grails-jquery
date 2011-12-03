// Resource declarations for Resources plugin
def dev = grails.util.GrailsUtil.isDevelopmentEnv()
def jqver = org.codehaus.groovy.grails.plugins.jquery.JQueryConfig.SHIPPED_VERSION
def jqueryFile = "jquery-${jqver}${dev?'':'.min'}.js"

modules = {
    'jquery' {
        resource id:'js', url:[plugin: 'jquery', dir:'js/jquery', file:jqueryFile], 
            disposition:'head', nominify: !dev
    }
}