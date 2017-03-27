package com.github.artamonovkirill.pact

import au.com.dius.pact.consumer.PactVerified$
import au.com.dius.pact.consumer.groovy.PactBuilder
import groovyx.net.http.RESTClient
import spock.lang.Specification

class ConsumerSpec extends Specification {

    static PATH = '/path'
    static BODY = [parent: [
            [child: ['a']],
            [child: ['a']]]]

    @SuppressWarnings('GrUnresolvedAccess')
    'Generate pact'() {
        given:
        def rulesService = new PactBuilder()
        rulesService {
            serviceConsumer 'Consumer'
            hasPactWith 'Provider'
            port 9000

            uponReceiving('request')
            withAttributes(
                    method: 'get',
                    path: PATH)
            willRespondWith(
                    status: 200)
            withBody {
                parent minLike(2, 2) {
                    child(['a'])
                }
            }
        }

        when:
        def result = rulesService.run() {
            given:
            def client = new RESTClient('http://localhost:9000/')

            when:
            def response = client.get(path: PATH)

            then:
            assert response.status == 200
            assert response.data == BODY
        }

        then:
        result == PactVerified$.MODULE$
    }

}
