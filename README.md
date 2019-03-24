# spring-kotlin-mongodb
poc

## findings: spring-data

- as usual, convenient magic.
- a developer needs to know what repo.save() is actually doing
- there is no repo.update(), just repo.save() - which behaves as "UPSERT"

### Insert

works as expected

- repo.insert(id="a"): inserted
- repo.insert(id="a"): DuplicateKeyException

### Update

does not(!) work as expected
repo.save() -> does an UPSERT !

- repo.insert(id="a"): inserted
- repo.save(id="a"): updated
- repo.save(id="b"): inserted


