#!/bin/sh

mongod --fork --syslog

echo 'Verificando os dbNames'
mongo --quiet --eval 'db.getMongo().getDBNames();'

echo 'Drop Previous Database'
mongo --quiet --eval 'db.getMongo().getDBNames().forEach(function(i){db.getSiblingDB(i).dropDatabase()})'

echo 'Verificando os dbNames depois do drop'
mongo --quiet --eval 'db.getMongo().getDBNames();'

echo 'Importing Clients'
mongoimport -d test -c clients --jsonArray < scripts/data/clients_withId.json

echo 'Importing Restaurants'
mongoimport -d test -c restaurants --jsonArray < scripts/data/restaurants_withId.json

echo 'Importing Orders'
mongoimport -d test -c orders --jsonArray < scripts/data/orders.json

echo 'Creating Index for Clients'
mongo --quiet --eval 'db.clients.createIndex({location : "2dsphere"})';

echo 'Creating Index for Restaurantes'
mongo --quiet --eval 'db.restaurants.createIndex({location : "2dsphere"})';

echo 'Creating Index for Orders'
mongo --quiet --eval 'db.orders.createIndex({"client.location": "2dsphere"})';

echo 'Running process'
ps -aux


echo 'Killing forked script'
pkill -f mongod

echo 'Send Sleep'
sleep 2 &&

echo 'Initialing mongod'
mongod --bind_ip_all