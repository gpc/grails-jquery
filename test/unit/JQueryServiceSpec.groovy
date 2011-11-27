import grails.plugin.spock.*

class JQueryServiceSpec extends UnitSpec {
    // I think this is a _stateful_ service whose sole purpose in life is to provide 
    // properties to JQueryResource... :/
    // it looks like exist() and cleanPath() are the only publicly accessible methods
    // cleanPath() appears to mean appending a trailing slash if necessary. In which case, it should be renamed
    // (used in jQueryResource resource()), the other helper methods should probably be private
    // seems like there's lots of duplication of implementation here too
    
    JQueryService toTest = new JQueryService()
    
    def "verify that paths are correctly 'cleaned' by adding a trailing slash"(){
        when: "we try to clean a path without a trailing slash"
            def test = toTest.cleanPath("foo")
        then: "the result contains a trailing slash"
            test == "foo/"
    }
    
    def "verify that paths while already contain a trailing slash are unmodified"(){
        when: "we try to clean a path containing a trailing slash"
            def test = toTest.cleanPath("foo/")
        then: "the result does not have another slash added to it"
            test == "foo/"
    }
    
    def "verify that empty strings as paths are unmodified"(){
        when: "we try to clean a path that's the empty string"
            def test = toTest.cleanPath("")
        then: "the result is the empty string"
            test == ""
    }
}
