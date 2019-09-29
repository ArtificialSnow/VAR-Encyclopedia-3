#!/bin/bash
# operand 1 is the name of creation

ffmpeg -i combinedVideo.mp4 -i "./VAR-Encyclopedia/.temp/tempCombinedChunks.wav" -c:v copy -c:a aac -strict experimental "./VAR-Encyclopedia/Creations/${1}.mp4"
#ffmpeg -y -i combine.mkv -b:v 2000k VAR-Encyclopedia/Creations/${1}.mp4

rm combinedVideo.mp4
rm combinedImages.mp4