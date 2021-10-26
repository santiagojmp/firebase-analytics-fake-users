sleep_interval=60*60*24 # 24 hours
while true; 
do 
  source ./restart_emulators.sh &
  echo "Going to sleep...";
  sleep $((sleep_interval)); 
  echo "Waking up!";
done
