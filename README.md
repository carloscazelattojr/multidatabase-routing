Example project multidatabase

### Docker
```
docker run -p3306:3306 --name mysql_1 -e MYSQL_ROOT_PASSWORD=root -d mysql

docker run -p3307:3306 --name mysql_2 -e MYSQL_ROOT_PASSWORD=root -d mysql
```

### Curl

```

curl --location --request GET 'localhost:8080/user/read/1'

curl --location --request GET 'localhost:8080/user/write/1'

```

