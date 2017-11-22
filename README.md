# PlayWall Server

The PlayWall Server is the server-side application for the PlayWall Desktop App.

## Installation / Requirements

- PlayWall Server requires Java 8 to run
- TO build PlayWall Server you need maven
- Create a java keystore fot https connections
- MySQL Database
- To run the server use: ```java -jar PlayWallServer.jar```

## Java Keystore

```
openssl pkcs12 -export -in /etc/letsencrypt/live/$1/fullchain.pem -inkey /etc/letsencrypt/live/$1/privkey.pem -out pkcs.p12 -name ALIAS -passout pass:PASSWORD
keytool -importkeystore -deststorepass PASSWORD -destkeypass PASSWORD -destkeystore keystore.jks -srckeystore pkcs.p12 -srcstoretype PKCS12 -srcstorepass PASSWORD -alias ALIAS
```

## Config File
```
download_folder=/PATH/TO/RECOUESES
db_host=localhost
keystorePassword=password
db_database=CookNow
db_username=user
db_password=password
db_port=3306
```

The ```download_folder``` contains the resources for updates and plugins.

## Libraries
- [Spark](https://github.com/perwendel/spark)
- [gson](https://github.com/google/gson)
- [unirest-java](https://github.com/Kong/unirest-java)
- [ormlite](https://github.com/j256/ormlite-core)
- mysql-connector-java