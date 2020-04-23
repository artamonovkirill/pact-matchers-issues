package com.github.artamonovkirill.pact.eachlikevalidaiton

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import spock.lang.Specification

class EachLikeSpec extends Specification {

    static port = 9002

    def 'raw array'() {
        given:
        def provider = new PactBuilder()
        provider {
            serviceConsumer 'EachLikeConsumer'
            hasPactWith 'EachLikeProvider'
            port EachLikeSpec.port

            uponReceiving('request for raw array json')
            withAttributes(
                    method: 'get',
                    path: '/fruits')
            willRespondWith(status: 200)
            withBody eachLike(1) {
                type regexp('.*', 'banana')
            }
        }

        when:
        def result = provider.runTest {
            expect:
            assert "http://localhost:$port/fruits".toURL().text == '[{"type":"banana"}]'
        }

        then:
        result instanceof PactVerificationResult.Ok
    }

    def 'named array'() {
        given:
        def provider = new PactBuilder()
        provider {
            serviceConsumer 'EachLikeConsumer'
            hasPactWith 'EachLikeProvider'
            port EachLikeSpec.port

            uponReceiving('request for raw array json')
            withAttributes(
                    method: 'get',
                    path: '/fruits')
            willRespondWith(status: 200)
            withBody {
                fruits eachLike(1) {
                    type regexp('.*', 'banana')
                }
            }
        }

        when:
        def result = provider.runTest {
            expect:
            assert "http://localhost:$port/fruits".toURL().text == '{"fruits":[{"type":"banana"}]}'
        }

        then:
        result instanceof PactVerificationResult.Ok
    }

    def 'wrong DSL'() {
        given:
        def provider = new PactBuilder()
        provider {
            serviceConsumer 'EachLikeConsumer'
            hasPactWith 'EachLikeProvider'
            port EachLikeSpec.port

            uponReceiving('request for raw array json')
            withAttributes(
                    method: 'get',
                    path: '/fruits')
            willRespondWith(status: 200)
            withBody {
                eachLike(1) {
                    type regexp('.*', 'banana')
                }
            }
        }

        when:
        def result = provider.runTest {
            expect:
            assert "http://localhost:$port/fruits".toURL().text == '{}'
        }

        then:
        result instanceof PactVerificationResult.Ok
    }

}
