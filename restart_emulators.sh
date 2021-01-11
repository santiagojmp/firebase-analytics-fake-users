echo "Restarting emulators at $(date +"%T")"
adb devices | grep emulator | cut -f1 | while read line; do adb -s $line emu kill; done
sleep 1m
emulator @Pixel_2_API_28 -wipe-data & emulator @Pixel_3_API_28 -wipe-data & emulator @Pixel_2_API_29 -wipe-data & emulator @Pixel_3_API_29 -wipe-data &
