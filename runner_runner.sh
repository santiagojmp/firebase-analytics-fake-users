TESTS_COUNT=$((1 + RANDOM % 300))
echo "Running $TESTS_COUNT tests"
cd $HOME/projects/firebase-analytics-fake-users/ && ./run_test.sh $TESTS_COUNT >> $HOME/logz.txt 2>$HOME/logz-error.txt
