package com.github.artamonovkirill.pact.nested

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import groovyx.net.http.RESTClient
import spock.lang.Specification

class NestedEqualsConsumerSpec extends Specification {

    public static PORT = 9001
    public static PATH = '/nested'

    @SuppressWarnings('GrUnresolvedAccess')
    def 'Generate pact'() {
        given:
        def provider = new PactBuilder()
        provider {
            serviceConsumer 'NestedConsumer'
            hasPactWith 'NestedProvider'
            port PORT

            uponReceiving('request for nested array json')
            withAttributes(
                    method: 'get',
                    path: PATH)
            willRespondWith(status: 200)
            withBody {
                flag true
                str '1'
//                rawArray eachLike(1, equalTo('1'))
//                regexpRawArray eachLike(1, regexp(~/.+/, '1'))
                arr minLike(1, 1) {
                    flag true
                    str '1'
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
                data == [
                        flag            : true,
                        str             : '1',
//                        rawArray        : ['1'],
//                        regexpRawArray  : ['1'],
                        arr: [[
                                                   flag: true,
                                                   str : '1']]]
//                                                   eqls          : '1',
//                                                   rgxp          : '1',
//                                                   rawArray      : ['1'],
//                                                   regexpRawArray: ['1']]]]
            }
        }

        then:
        result == PactVerificationResult.Ok.INSTANCE
    }

}
