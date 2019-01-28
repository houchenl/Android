#!/bin/bash
function read_dir() {
    for file in `ls $1`
    do
        if [ -d $1"/"$file ]
        then
            if [ $file = "build" ]
            then
                echo $1"/"$file is directory remove it
                rm -rf $1"/"$file
            fi
            read_dir $1"/"$file
        fi
    done
}

read_dir $1
