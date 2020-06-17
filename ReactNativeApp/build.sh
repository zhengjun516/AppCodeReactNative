#! /bin/bash

#基础包
#yarn react-native bundle --platform android --dev false --entry-file ./base.js --bundle-output ./android/app/src/main/assets/base.android.bundle --assets-dest ./android/app/src/main/res/ --config ./base.config.js

#/业务包1
#yarn react-native bundle --platform android --dev false --entry-file ./index.js --bundle-output ./android/app/src/main/assets/business.android.bundle --assets-dest ./android/app/src/main/res/ --config ./business.config.js

#/业务包2
yarn react-native bundle --platform android --dev false --entry-file ./index2.js --bundle-output ./android/app/src/main/assets/business2.android.bundle --assets-dest ./android/app/src/main/res/ --config ./business.config.js
