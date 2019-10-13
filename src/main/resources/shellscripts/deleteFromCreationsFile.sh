#!/bin/bash
#first operand is the creation name
#second operand is the search term

sed -i "/^${1} ${2}$/d" "./VAR-Encyclopedia/Creations/CurrentCreations.txt"

