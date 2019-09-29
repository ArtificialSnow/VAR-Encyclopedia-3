#!/bin/bash
# operand 1 is duration of vedio
# operand 2 is number of pictures

ffmpeg -framerate $((${1}/${2})) -pattern_type glob -i './downloads/*.jpg' -c:v libx264 -r 30 -pix_fmt yuv420 -vf scale=800:600 combinedImages.mp4"