# spring-kotlin-mongodb
poc: a simple playground for kotlin, spring-boot, spring-data-mongo, docker, docker-compose

## findings: spring-data

- as usual: convenient, kind of magic.
- Issue: repo.save() - what is it doing? -> UPSERT.
    - a developer needs to know what repo.save() is actually doing
    - there is no repo.update(), just repo.save() - which behaves as "UPSERT"

- Issue: doc._class 
    - some how spring-data save's the qualified class name into a mongo doc (attribute: _class)
    - so, no idea what happens if you rename your class or move it into a different package

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
    
    # start
    $ make -C rest-api db-local.up
    # stop
    $ make -C rest-api db-local.down
    # stop and remove volumes
    $ make -C rest-api db-local.down.v
            
    # db-ci (to be used for gradle test)
    
    # start
    $ make -C rest-api db-ci.up
    # stop db and remove volumes
    $ make -C rest-api db-ci.down.v

```


## inspired by

- spring-data: https://github.com/MarianoLopez/MySpringTutorial/blob/master/01-%20book-backend%20basic/src/main/kotlin/com/z/bookbackend/services/AuthorService.kt
- mongo-template: http://appsdeveloperblog.com/spring-boot-and-mongotemplate-tutorial-with-mongodb/

## tools

- mongo ui client: https://coderwall.com/p/fb5dgg/the-best-mongodb-gui-for-mac-os-x

![Alt text](docs/screenshot_mongo_example.png?raw=true "screenshot")
