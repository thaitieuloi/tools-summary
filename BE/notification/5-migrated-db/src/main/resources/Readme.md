# File name rule

* Need change:
  + path = [YYMMDD]-[task id]_[description].sql
  + id = [YYMMDD]-[task id]
  + author = [your name]
  + labels = [sprint-xx]

## Example:

```
  <changeSet id="20250717-86eu6t8ma" author="henry" labels="sprint-27">
    <sqlFile path="20250717-86eu6t8ma-init.sql" relativeToChangelogFile="true"
             endDelimiter=";" splitStatements="true" stripComments="true"/>
  </changeSet>
```

# Run migration

```
cd BackEndSource
and then run 
./gradlew :workspace:notification:5-migrated-db:update -PenvFile="[path for env file]"

Ex: 
./gradlew :workspace:notification:5-migrated-db:update -PenvFile="/Users/thaitieuloi/notification-be/docker/notification/local.env"

```

# Rollback migration

```
cd BackEndSource
and then run 
./gradlew :workspace:notification:5-migrated-db:build
./gradlew :workspace:notification:5-migrated-db:rollback -PenvFile="[path for env file]" -PliquibaseCommandValue=[tag]

Ex:
./gradlew :workspace:notification:5-migrated-db:build 
./gradlew :workspace:notification:5-migrated-db:rollback -PenvFile="/Users/thaitieuloi/notification-be/docker/notification/local.env" -PliquibaseCommandValue=sprint-27

```
