#!/usr/bin/env bash


curl -v \
  -X POST \
  -H "Content-Type: text/xml" \
  -d "Bad content" \
  http://localhost:8080/