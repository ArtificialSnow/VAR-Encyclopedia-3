#!/bin/bash
#First operand is the search term associated with the audio chunk
#Second operand is the audio chunk to delete


rm -f "./VAR-Encyclopedia/AudioChunks/${1}/RegularAudioChunks/${2}.wav"
rm -f "./VAR-Encyclopedia/AudioChunks/${1}/RedactedAudioChunks/${2}.wav"

sed -i "/^${2}:/d" "./VAR-Encyclopedia/AudioChunks/${1}/AudioChunks.txt"