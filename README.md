# spring-kotlin-mongodb
poc: a simple playground for kotlin, spring-boot, spring-data-mongo, docker, docker-compose

## findings: spring-data

- as usual: convenient, kind of magic.
- a developer needs to know what repo.save() is actually doing
- there is no repo.update(), just repo.save() - which behaves as "UPSERT"

### repo.insert

works as expected

- repo.insert(id="a"): inserted
- repo.insert(id="a"): DuplicateKeyException

### repo.update

does not(!) work as expected
repo.save() -> does an UPSERT !

- repo.insert(id="a"): inserted
- repo.save(id="a"): updated
- repo.save(id="b"): inserted

## runbook

### playground

```
    # build db + app and start everything using docker-compose
       
    $ make -C rest-api playground.up
    $ open http://localhost:8080/swagger-ui.html
```


### build

```
    $ make -C rest-api help
    $ make -C rest-api app.build

```

### build + test

```
    $ make -C rest-api help
    $ make -C rest-api app.test
    
    # serve test reports ...
    $ make -C rest-api reports.serve.tests
    $ open http://127.0.0.1:20000
    

```

### run local db (docker)

```
    # db-local
    $ make -C rest-api db-local.up
    
    # db-ci (to be used for gradle test)
    $ make -C rest-api db-ci.up

```


