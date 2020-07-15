#! /bin/bash
 
function buildBundle()
{
      outputDir=$1;outputFile=$2; outputResDir=$3; dev=$4;entryFile=$5;configFile=$6
      if [ ! -d "$outputDir" ];then
        mkdir $outputDir
      fi

      if [ ! -d "$outputResDir" ];then
        mkdir "$outputResDir"
      fi

      yarn react-native bundle --platform android --dev $dev --entry-file $entryFile --bundle-output "$outputDir/$outputFile"  --assets-dest $outputResDir --config $configFile
}

 currentDir=$(pwd)
 
#基础包
 baseOutputDir='./android/app/src/main/assets/bundles/bundle03/'
 baseOutputFile='base.android.bundle'
 baseResDir='./android/app/src/main/assets/bundles/bundle03/res/'

 buildBundle $baseOutputDir $baseOutputFile $baseResDir false ./base.js ./base.config.js
#yarn react-native bundle --platform android --dev false --entry-file ./base.js --bundle-output $baseOutputDir$baseOutputFile  --assets-dest $baseResDir --config ./base.config.js

#/业务包1
 businessBundleFileDir='./android/app/src/main/assets/bundles/bundle03/'
 businessBundleFile='business.android.bundle'
 businessBundleResDir='./android/app/src/main/assets/bundles/bundle03/res/'
 buildBundle $businessBundleFileDir $businessBundleFile $businessBundleResDir false ./index.js ./business.config.js
#yarn react-native bundle --platform android --dev false --entry-file ./index.js --bundle-output  $businessBundleFileDir$businessBundleFile   --assets-dest $businessBundleResDir --config ./business.config.js

#/业务包2
 business02BundleFileDir='./android/app/src/main/assets/bundles/bundle03/'
 business02BundleFile='business2.android.bundle'
 business02BundleResDir='./android/app/src/main/assets/bundles/bundle03/res/'
 buildBundle $business02BundleFileDir $business02BundleFile $business02BundleResDir false ./index2.js ./business.config.js
 #yarn react-native bundle --platform android --dev false --entry-file ./index2.js --bundle-output $businessBundleFileDir$businessBundleFile --assets-dest $business02BundleResDir --config ./business.config.js

cd ./android/app/src/main/assets/bundles/

zip -r -m -o  "${currentDir}/bundles/bundle03.zip" ./bundle03

