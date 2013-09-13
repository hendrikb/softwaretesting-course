#!/bin/bash
echo "Usage: render.sh <source filename> <destination filename>"
echo "       Where the source file must be in markdown syntax"
pandoc -f markdown $1 -o $2
