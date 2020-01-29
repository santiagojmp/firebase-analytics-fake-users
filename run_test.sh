#!/usr/bin/env bash

# run_test.sh NUMBER_OF_TESTS_TO_RUN

for ((i=1; i<=$1; i++)); do
    echo "Running test number $i at $(date +"%T")"
    ./gradlew connectedAndroidTest --console plain | grep -e "InstrumentationResultParser: OK" -e "BUILD"
done
