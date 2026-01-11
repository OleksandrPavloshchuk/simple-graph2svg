#!/usr/bin/env bash

SOURCE=$(cat <../../samples/graph1.xml)

curl -v \
  -X POST \
  -H "Content-Type: text/xml" \
  -d "${SOURCE}" \
  http://localhost:8080/