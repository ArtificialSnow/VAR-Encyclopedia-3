#!/bin/bash
# First input is the name of the audio chunk
# Second input is the name of the search term
# Third input is the voice to be used
# Fourth input is the text to be read

audioChunkDirectory="./VAR-Encyclopedia/AudioChunks"

if [ ! -e "${audioChunkDirectory}/${2}" ]; then
    mkdir -p "${audioChunkDirectory}/${2}"
    mkdir -p "${audioChunkDirectory}/${2}/RegularAudioChunks"
    mkdir -p "${audioChunkDirectory}/${2}/RedactedAudioChunks"
    touch "${audioChunkDirectory}/${2}/AudioChunks.txt"
fi

redactedText=$(sed -e "s/${2}/blank/gI" <<< "$4")

echo "(voice_${3}) (set! utt1 (Utterance Text \"${4}\")) (utt.synth utt1) (utt.save.wave utt1 \"${audioChunkDirectory}/${2}/RegularAudioChunks/${1}.wav\" \`riff)" | festival
echo "(voice_${3}) (set! utt1 (Utterance Text \"${redactedText}\")) (utt.synth utt1) (utt.save.wave utt1 \"${audioChunkDirectory}/${2}/RedactedAudioChunks/${1}.wav\" \`riff)" | festival

echo "${1}:${2}:${3}:${4}" >> "${audioChunkDirectory}/${2}/AudioChunks.txt"
