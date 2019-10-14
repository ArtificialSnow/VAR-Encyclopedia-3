#!/bin/bash
#The first operand is the search term the audio chunks were created for
#The second operand is the list of names of audio chunks

for audioChunk in ${2}; do
  listOfChunkNames="${listOfChunkNames}./VAR-Encyclopedia/AudioChunks/${1}/RegularAudioChunks/${audioChunk}.wav "
  listOfRedactedChunkNames="${listOfRedactedChunkNames}./VAR-Encyclopedia/AudioChunks/${1}/RedactedAudioChunks/${audioChunk}.wav "
done

sox ${listOfChunkNames} "./VAR-Encyclopedia/.temp/tempCombinedChunks.wav"
sox ${listOfRedactedChunkNames} "./VAR-Encyclopedia/.temp/tempRedactedCombinedChunks.wav"