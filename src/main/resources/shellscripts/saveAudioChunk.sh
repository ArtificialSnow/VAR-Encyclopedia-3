#!/bin/bash
# First input is the name of the audio chunk
# Second input is the name of the search term
# Third input is the voice to be used
# Fourth input is the text to be read

audioChunkDirectory="./VAR-Encyclopedia/AudioChunks"

if [ ! -e "${audioChunkDirectory}/${2}" ]; then
    mkdir -p "${audioChunkDirectory}/${2}"
fi

echo "(voice_${3}) (set! utt1 (Utterance Text \"${4}\")) (utt.synth utt1) (utt.save.wave utt1 \"${audioChunkDirectory}/${2}/${1}\" \`riff)" | festival 
