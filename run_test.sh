#!/usr/bin/env bash

# run_test.sh NUMBER_OF_TESTS_TO_RUN
# remember to correctly set you ANDROID_HOME path!

for ((i=1; i<=$1; i++)); do
    echo "Running test number $i at $(date +"%T")"
#    ./gradlew connectedAndroidTest --console plain | grep -e "InstrumentationResultParser: OK" -e "BUILD"
    java -jar spoon-runner-1.7.1-jar-with-dependencies.jar \
        --apk app/build/outputs/apk/debug/app-debug.apk \
        --test-apk app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
done
