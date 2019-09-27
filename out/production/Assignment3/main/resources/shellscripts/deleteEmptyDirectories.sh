#!/bin/bash
#First input is the directory to perform this on

for directory in "${1}"/*; do
  if [ -d "$directory" ]; then
    if [ "$(ls -h "$directory")" == "" ]; then
      rmdir "$directory"
    fi
  fi
done