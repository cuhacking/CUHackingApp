#!/bin/bash

read -s -p "Enter the private key (ask Jack): " private_key

ejson d config/secrets.ejson > config/secrets.json
