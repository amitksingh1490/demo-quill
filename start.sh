#!/bin/bash

docker-compose down && docker-compose up -d db && docker ps
