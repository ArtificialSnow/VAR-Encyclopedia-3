#!/bin/bash

voicesString=$(echo "(voice.list)" | festival -i)
echo $(cut -d ")" -f1 <<< $(cut -d "(" -f10 <<< $voicesString))

