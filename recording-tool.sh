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

#if [ `which sox` == 'sox not found' ] ###fixme: not work in different shell
  #then
    #echo 'Please install sox before recording'
    #echo 'To install sox in ubuntu:'
    #echo 'apt-get install sox'
    #exit
#fi
if [ ! -d db/bkup ]
  then mkdir db/bkup -p
fi
if [ -f .rec-progress ]
then
  read i < .rec-progress
else
  i=1
fi
max=`cat rhyme-list.txt|wc -l`


function get_rhyme () {
  if [ ${1} -eq 0 ]
  then
    echo 'BOF'
  elif [ ${1} -eq $max ]
  then
    echo 'EOF'
  else
    echo `sed -n "${1}p" rhyme-list.txt`
  fi
  }

function rhyme_to_read () {
  if [ $i -le $max ]
  then
  {  
    left=`get_rhyme $((i-1))`
    center=`get_rhyme $i`
    right=`get_rhyme $((i+1))`
    echo -e "Please read: \033[30;47m $left \033[33;44;1m $center \033[37;42m $right \033[0m"
  }
  else
    echo "Warning: end of rhyme list reached!!!"
  fi
}
# record and "filter" silence which is lower than 45dB
# each recording timeouts after 2 seconds
function clean_record () {
  rhyme=`get_rhyme $i`
  if [ -f db/${rhyme}.wav ]
  then
    timestamp=`date +%F_%T`
    echo "file exist, making a backup at db/bkup/${rhyme}_${timestamp}.wav"
    mv db/${rhyme}.wav db/bkup/${rhyme}-${timestamp}.wav
  fi
  # 45 dB maybe too strict. You can try a lower silence filter, or
  #timeout -s INT 2 rec -p | sox -p db/${rhyme}.wav silence -l 1 00:00:00.5 -45d -1 00:00:00.5 -45d
  timeout -s INT 1 rec -p | sox -p db/${rhyme}.wav
}
function do_record () {
  echo "recording `get_rhyme $i`..."
  clean_record
}
#start/resume before loop
rhyme_to_read
#a loop to interact with user's keyboard input

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
      # Up -> (Re)start recording
      do_record
      
    elif [ "$char" == $'\x1b[D' ]
    then
      # Left
      i=$((i-1))
      rhyme_to_read
      do_record

    elif [ "$char" == $'\x1b[C' ]
    then
      # Right
      i=$((i+1))
      rhyme_to_read
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
