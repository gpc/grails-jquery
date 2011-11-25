import grails.plugin.spock.*

class JQueryTagLibSpec extends TagLibSpec {
    def "verify that using the jQuery tag adds to the jQuery ready function"(){
        when: "a piece of Javascript to be executed on ready is passed to jQuery"
            def test = jquery(attrs: []) { "alert('Hello world');" }
        then: "the code is wrapped in jQuery"
            test == '<script type="text/javascript">jQuery(function(){alert(\'Hello world\');}); </script>'
    }
    
    def "verify that we can toggle an item using defaults"(){
        when: "we pass toggle, giving the html id of the element, and the element to be toggled"
            def test = toggle(sourceId:"foo", targetId:"bar")
        then: "we get jQuery output that toggles the element"
            test == /jQuery("#foo").click(function(){jQuery("#bar").toggle("normal"); return false; });/
    }
    
    // if toggleelement is deprecated, it should be removed. 
    // until then, since it appears to do the same thing with a different API, 
    // it should redirect to toggle internally with the correct parameters rather than duplicating implementation
    
    def "verify that we can toggle an item with a custom event"(){
        when: "we pass toggle, giving the html id of the element, the element to be toggled, and an event"
            def test = toggle(sourceId:"foo", targetId:"bar", event:"hover")
        then: "we get jQuery output that toggles the element on the event"
            test == /jQuery("#foo").hover(function(){jQuery("#bar").toggle("normal"); return false; });/
    }
    
    def "verify that we can toggle an item with custom speed"(){
        when: "we pass toggle, giving the html id of the element, the element to be toggled, and custom speed"
            def test = toggle(sourceId:"foo", targetId:"bar", speed:"fast")
        then: "we get jQuery output that toggles the element at the custom speed"
            test == /jQuery("#foo").click(function(){jQuery("#bar").toggle("fast"); return false; });/
    }
    
    def "verify that we can toggle an item with custom speed and custom event"(){
        when: "we pass toggle, giving the html id of the element, the element to be toggled, a custom event, and custom speed"
            def test = toggle(sourceId:"foo", targetId:"bar", event:"hover", speed:"fast")
        then: "we get jQuery output that toggles the element on the custom event at the custom speed"
            test == /jQuery("#foo").hover(function(){jQuery("#bar").toggle("fast"); return false; });/
    }
    
    def "verify that we can get the field value for an attribute selector"(){
        when: "we try to select for an attribute"
            def test = fieldValue(selector:'input[name*="man"]')
        then: "we get jQuery output that gets the field Value"
            test == /jQuery('input[name*="man"]').fieldValue()[0]/
    }
    
    def "verify that we can get the field value for an element id"(){
        when: "we try to select for an element"
            def test = fieldValue(elementId:'foo')
        then: "we get jQuery output that gets the field Value"
            test == /jQuery('#foo').fieldValue()[0]/
    }
    
    // note that the body of the closure implies that if _both_ the attribute selector
    // and an element id are specified, the closure chooses the selector silently and 
    // ignores the elementId. While such usage may seem stupid, it may be worth refactoring
    // to alert the developer that it should be one OR the other, NOT both
    // since this is just wrapping a jQuery call in javascript, I'm not sure how much value this adds
}
