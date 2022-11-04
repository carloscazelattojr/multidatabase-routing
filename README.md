Example project multidatabase

### Docker
```
docker run -p3306:3306 --name mysql_1 -e MYSQL_ROOT_PASSWORD=root -d mysql

docker run -p3307:3306 --name mysql_2 -e MYSQL_ROOT_PASSWORD=root -d mysql
```


