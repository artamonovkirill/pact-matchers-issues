package com.github.artamonovkirill.pact.minlike

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import groovyx.net.http.RESTClient
import spock.lang.Specification

class NestedMinLikeConsumerSpec extends Specification {

    public static PORT = 9000
    public static PATH = '/nested-array'
    public static BODY = [parent: [
            [child: ['a']],
            [child: ['a']]]]

    @SuppressWarnings('GrUnresolvedAccess')
    def 'Generate pact'() {
        given:
        def provider = new PactBuilder()
        provider {
            serviceConsumer 'NestedMinLikeConsumer'
            hasPactWith 'NestedMinLikeProvider'
            port PORT

            uponReceiving('request for nested array json')
            withAttributes(
                    method: 'get',
                    path: PATH)
            willRespondWith(status: 200)
            withBody {
                parent minLike(2, 2) {
                    child(['a'])
                }
            }
        }

        when:
        def result = provider.runTest {
            given:
            def client = new RESTClient("http://localhost:$PORT/")

            when:
            def response = client.get(path: PATH)

            then:
            with(response) {
                status == 200
                data == BODY
            }
        }

        then:
        result == PactVerificationResult.Ok.INSTANCE
    }

}
