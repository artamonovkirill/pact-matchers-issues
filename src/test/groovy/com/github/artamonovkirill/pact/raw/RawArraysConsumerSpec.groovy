package com.github.artamonovkirill.pact.raw

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import groovyx.net.http.RESTClient
import spock.lang.Specification

class RawArraysConsumerSpec extends Specification {

    public static PORT = 9002
    public static PATH = '/raw'

    @SuppressWarnings('GrUnresolvedAccess')
    def 'Generate pact'() {
        given:
        def provider = new PactBuilder()
        provider {
            serviceConsumer 'RawArrayConsumer'
            hasPactWith 'RawArrayProvider'
            port PORT

            uponReceiving('request for raw array')
            withAttributes(
                    method: 'get',
                    path: PATH)
            willRespondWith(status: 200)
            withBody {
                eachLkEqTo eachLike(1, equalTo('1'))
                eachLkRgxp eachLike(1, regexp(~/.+/, '1'))
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
                data == [
                        eachLkEqTo: ['1'],
                        eachLkRgxp: ['1']]
            }
        }

        then:
        result == PactVerificationResult.Ok.INSTANCE
    }

}
