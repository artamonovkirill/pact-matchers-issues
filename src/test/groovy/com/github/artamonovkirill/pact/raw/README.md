Given a groovy DSL body like:
```
withBody {
    rawArray eachLike(1, '1')
    rawArrayEqTo eachLike(1, equalTo('1'))
    regexpRawArray eachLike(1, regexp(~/.+/, '1'))
}
```
Pact  JSON is generated as:
```
{
  "body": {
    "rawArray": ["1"],
    "rawArrayEqTo": ["1"],
    "regexpRawArray": ["1"]
  },
  "matchingRules": {
    "body": {
      "$.rawArray": {
        "matchers": [{
          "match": "type"
        }],
        "combine": "AND"
      },
      "$.rawArrayEqTo": {
        "matchers": [{
           "match": "type"
          }],
          "combine": "AND"
      },
      "$.rawArrayEqTo[*]": {
        "matchers": [{
          "match": "equality"
        }],
        "combine": "AND"
      },
      "$.regexpRawArray": {
        "matchers": [{
          "match": "type"
        }],
        "combine": "AND"
      },
      "$.regexpRawArray[*]": {
        "matchers": [{
          "match": "regex",
          "regex": ".+"
        }],
        "combine": "AND"
      }
    }
  }
}
```
what is as expected.

But then on the provider side, a JSON like:
```
{
  "rawArray": [],
  "rawArrayEqTo": [],
  "regexpRawArray": []
}
```
successfully passes Pact validation.

Bug logged: 
[JVM provider ignores raw array value matchers](https://github.com/DiUS/pact-jvm/issues/698)