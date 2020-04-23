package com.github.artamonovkirill.pact.minlike

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import groovyx.net.http.RESTClient
import spock.lang.Specification

class NestedMinLikeConsumerSpec extends Specification {

    static port = 9000
    static path = '/nested-array'
    static body = [parent: [
            [child: ['a']],
            [child: ['a']]]]

    def 'Generate pact'() {
        given:
        def provider = new PactBuilder()
        provider {
            serviceConsumer 'NestedMinLikeConsumer'
            hasPactWith 'NestedMinLikeProvider'
            port NestedMinLikeConsumerSpec.port

            uponReceiving('request for nested array json')
            withAttributes(
                    method: 'get',
                    path: path)
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
            def client = new RESTClient("http://localhost:$port/")

            when:
            def response = client.get(path: path)

            then:
            with(response) {
                status == 200
                data == body
            }
        }

        then:
        result instanceof PactVerificationResult.Ok
    }

}
