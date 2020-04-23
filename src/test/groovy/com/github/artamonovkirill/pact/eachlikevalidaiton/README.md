# eachLike DSL validation

A raw array "eachLike":
```groovy
withBody eachLike(1) {
    type regexp('.*', 'banana')
}
```
produces:
```json
[
  {
    "type": "banana"
  }
]
```

A named array "eachLike":
```groovy
withBody {
    fruits eachLike(1) {
        type regexp('.*', 'banana')
    }
}
```
produces:
```json
{
  "fruits": [
    {
      "type": "banana"
    }
  ]
}
```

A wrong DSL like:
```groovy
withBody {
    eachLike(1) {
        type regexp('.*', 'banana')
    }
}
```
produces:
```json
{}
```
but there is not warning or error regarding incorrect DSL usage.