#!/bin/bash
# first operand is the search term

ffmpeg -i combinedImages.mp4 -vf drawtext="fontfile=/path/to/font.ttf: text='${1}': fontcolor=white: fontsize=24: box=1: boxcolor=black@0.5: boxborderw=5: x=(w-text_w)/2: y=(h-text_h)/2" -codec:a copy combinedVideo.mp4
