# Pact Issues Examples [![Gradle](https://github.com/artamonovkirill/pact-issues-examples/actions/workflows/gradle.yml/badge.svg)](https://github.com/artamonovkirill/pact-issues-examples/actions/workflows/gradle.yml)

Examples:
* [Arrays size matchers being propagated to child arrays](./src/test/groovy/com/github/artamonovkirill/pact/minlike/README.md)
* [Field value validation within and outside of minLike](./src/test/groovy/com/github/artamonovkirill/pact/nested/README.md)
* [No eachLike DSL validation](./src/test/groovy/com/github/artamonovkirill/pact/eachlikevalidaiton/README.md)

To run the full suite (including failing provider tests), execute:
```bash
./gradlew build providerTest
```