#!/bin/bash

read -s -p "Enter the private key (ask Jack): " private_key

mkdir -p ~/Keys/ejson

echo $private_key > ~/Keys/ejson/568522fd0ae128fa80f46f3baba18b414be5a7c2fec95743a006771008f4ee19

EJSON_KEYDIR="$HOME/Keys/ejson" ejson d config/secrets.ejson > config/secrets.json
