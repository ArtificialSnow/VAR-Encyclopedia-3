#!/bin/bash
#First operand is the search term associated with the audio chunk
#Second operand is the audio chunk to delete


rm -f "VAR-Encyclopedia/AudioChunks/${1}/${2}.wav"