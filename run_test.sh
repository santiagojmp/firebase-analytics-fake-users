#!/usr/bin/env bash

# run_test.sh NUMBER_OF_TESTS_TO_RUN
# remember to correctly set you ANDROID_HOME path!


# disabling animation on every connected device
adb devices | while read line
do
    if [ ! "$line" = "" ] && [ `echo $line | awk '{print $2}'` = "device" ]
    then
        device=`echo $line | awk '{print $1}'`
        echo "disabling animation on device: $device..."
        adb -s $device shell -n settings put global window_animation_scale 0
        adb -s $device shell -n settings put global transition_animation_scale 0 &&
        adb -s $device shell -n settings put global animator_duration_scale 0
    fi
done

# running tests
for ((i=1; i<=$1; i++)); do
    echo "Running test number $i at $(date +"%T")"
#    ./gradlew connectedAndroidTest --console plain | grep -e "InstrumentationResultParser: OK" -e "BUILD"
    java -jar spoon-runner-1.7.1-jar-with-dependencies.jar \
        --apk app/build/outputs/apk/debug/app-debug.apk \
        --test-apk app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
done
