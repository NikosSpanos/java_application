#!/bin/sh
exec java org.springframework.boot.loader.JarLauncher "--my_sql.host=$SQL_SERVER" "--my_sql.port=$SQL_PORT" "--my_sql.username=$SQL_USERNAME" "--my_sql.password=$SQL_PASSWORD" "$@"