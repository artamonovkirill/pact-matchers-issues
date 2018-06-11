Given the following body matcher for Groovy DSL:
```
withBody {
    flag true
    str '1'
    arr minLike(1, 1) {
         flag true
         str '1'
    }
}
```
Groovy pact consumer library generates Pact JSON with:
```
{
  "body": {
    "flag": true,
    "str": "1",
    "arr": [{
      "flag": true,
      "str": "1"
    }]
  },
  "matchingRules": {
    "body": {
      "$.arr": {
        "matchers": [{
          "match": "type",
          "min": 1
        }],
        "combine": "AND"
      }
    }
  }
}
```
On the provider side, I expected for both occurrences of `flag` and `str` to be validated in the same way (= being equal to the provided values).
But actually for `arr.flag` and `arr.str` only the type is validated.

Bug logged: 
[Inconsistency in value validation behavior within and outside eachLike/minLike](https://github.com/DiUS/pact-jvm/issues/697)