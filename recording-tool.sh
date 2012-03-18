#!/bin/sh
# a shell script to make our recordings easier
# (c) Hoang Minh Thang
# Licence: GPL-v3
# ---------------
# Remove silence
# rec -r 44100 -p | sox -p "audio_name-$(date '+%Y-%m-%d').ogg" silence -l 1 00:00:00.5 -45d -1 00:00:00.5 -45d
# to get key code, run this in terminal
# read -r -n 1 -s char
# How it work?
# it read the rhyme-list file and get index
# var i
# Usage
# * Run it
# * i start from 1
# * the rhyme to record is printed out (Something like "Please read this loud: ang")
# * press Up-Arrow to start/restart recording
#     * in case of retry,  a back-up should be created in ./back-ups
# * press Rigt-Arrow to go to next rhyme (increase i by 1) C
# * press Left-Arrow to go to previous rhyme (decrease i by 1) D
# * press Enter to exit. Var i (aka current progress) will be saved to a file name .rec-progress
# * if you want to restart all, remove .rec-progress
if [ -f .rec-progress ]
then
  read i < .rec-progress
else
  i=1
fi
max=`cat rhyme-list.txt|wc -l`

function rhyme_to_read () {
  if [ $i -le $max ]
  then
    rhyme=`sed -n "${i}p" rhyme-list.txt`
    echo "Please read: ${rhyme}"
  else
    echo "Warning: end of rhyme list reached!!!"
  fi
}
function do_record () {
  rhyme_to_read
  echo "recording..."
  # incomplte. Mocking `sox`
  touch ${rhyme}.wav # need a better file structure!
}
while IFS= read -r -n 1 -s char
  do
    if [ "$char" == $'\x1b' ] # \x1b is the start of an escape sequence
    then
      # Get the rest of the escape sequence (3 characters total)
      while IFS= read -r -n 2 -s rest
      do
          char+="$rest"
          break
      done
    fi

    if [ "$char" == $'\x1b[A' ]
    then
      # Up -> start recording
      do_record
      
    elif [ "$char" == $'\x1b[D' ]
    then
      # Left
      i=$((i-1))
      do_record

    elif [ "$char" == $'\x1b[C' ]
    then
      # Right
      i=$((i+1))
      do_record
      
    elif [ -z "$char" ]
    then
      # Newline
      echo "If you really want to quit, I'd let you go"
      echo "Current progress is ${i}"
      if [ ${i} -gt 1 ]
      then
        echo "Saving progress to .rec-progress"
        echo "..."
        echo $i > .rec-progress
      else
        echo "Nothing to save!"
      fi
    exit
    fi
  done
