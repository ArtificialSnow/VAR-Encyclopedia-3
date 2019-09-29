#!/bin/bash
# operand 1 is duration of vedio
# operand 2 is number of pictures

ffmpeg -y -framerate $((${1}/${2})) -pattern_type glob -i './downloads/*.jpg' -c:v libx264 -vf fps=25 -pix_fmt yuv420p combinedImages.mp4
