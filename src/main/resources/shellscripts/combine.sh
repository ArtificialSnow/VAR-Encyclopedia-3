#!/bin/bash
# operand 1 is the name of creation

ffmpeg -i combinedVideo.mp4 -i combinedAudio.wav -c copy combine.mkv
ffmpeg -y -i combine.mkv -b:v 2000k VAR-Encyclopedia/Creations/${1}.mp4

rm combinedAudio.mp4
rm combinedVideo.mp4
rm combine.mkv
rm combinedImages.mp4