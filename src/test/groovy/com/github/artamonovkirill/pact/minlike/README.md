A JSON body like:
```json
{
  "parent": [{
    "child": ["a"]
  },{
    "child": ["a"]
  }]
}
```
satisfies a body matcher like:
```groovy
parent minLike(2, 2) {
  child(['a'])
}
```
but the Provider validation fails with:
```
java.lang.AssertionError: 
0 - $.parent.0.child -> [{mismatch=Expected List(a) to have minimum 2, diff=}]
```

Bug logged: 
[Matching rule on array size gets inherited by all child arrays](https://github.com/DiUS/pact-jvm/issues/396)