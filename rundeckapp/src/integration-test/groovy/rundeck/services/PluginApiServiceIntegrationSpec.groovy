package rundeck.services

import grails.testing.mixin.integration.Integration
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.WebUtils
import spock.lang.Specification

@Integration
class PluginApiServiceIntegrationSpec extends Specification {

    PluginApiService pluginApiService

    def setup() {
        def gwr = Stub(GrailsWebRequest) {
            getLocale() >> Locale.ENGLISH
        }
        WebUtils.metaClass.static.retrieveGrailsWebRequest = { -> gwr }
    }

    def cleanup() {
    }

    void "list plugins"() {
        when:
        def pluginList = pluginApiService.listPluginsDetailed()

        then:
        pluginList.descriptions.size() == 21
        pluginList.serviceDefaultScopes.size() == 2
        pluginList.bundledPlugins.size() == 7
        pluginList.embeddedFilenames != null
        pluginList.specialConfiguration.size() == 7
        pluginList.specialScoping.size() == 2
        pluginList.uiPluginProfiles != null

    }

    void "list plugins option value plugin enabled"() {
        setup:
        pluginApiService.grailsApplication.config.rundeck.feature.'option-values-plugin'.enabled=true

        when:
        def pluginList = pluginApiService.listPluginsDetailed()

        then:
        pluginList.descriptions.size() == 22
        pluginList.serviceDefaultScopes.size() == 2
        pluginList.bundledPlugins.size() == 7
        pluginList.embeddedFilenames != null
        pluginList.specialConfiguration.size() == 7
        pluginList.specialScoping.size() == 2
        pluginList.uiPluginProfiles != null

    }
}