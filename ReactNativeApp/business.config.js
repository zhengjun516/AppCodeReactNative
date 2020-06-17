const pathSep = require('path').sep;
const baseModulesMap = require('./config/platformNameMap.json');
const getModuleId = require('./config/getModuleId.js').getModuleId;
const useIndex = require('./config/getModuleId.js').useIndex;

let entryFile;

function createModuleIdFactory(){
    const projectRootPath = __dirname;

    return path => {
        let name = getModuleId(projectRootPath,path,entryFile,true);
        return name;
    }
}
function processModulesFilter(module){
      const projectRootPath = __dirname;
        if (baseModulesMap == null || baseModulesMap.length == 0) {
          console.log('请先打基础包');
          process.exit(1);
          return false;
        }
        const path = module['path']
        if (path.indexOf("__prelude__") >= 0 ||
          path.indexOf("/node_modules/react-native/Libraries/polyfills") >= 0 ||
          path.indexOf("source-map") >= 0 ||
          path.indexOf("/node_modules/metro/src/lib/polyfills/") >= 0) {
          return false;
        }
        if (module['path'].indexOf(pathSep + 'node_modules' + pathSep) > 0) {
          if ('js' + pathSep + 'script' + pathSep + 'virtual' == module['output'][0]['type']) {
            return true;
          }
          const name = getModuleId(projectRootPath,path);
          if (useIndex && name < 100000) {//这个模块在基础包已打好，过滤
            return false;
          }else if(useIndex!==true && baseModulesMap.includes(name)){//使用模块名作为模块id的逻辑
            return false;
          }
        }
        return true;
}

function getModulesRunBeforeMainModule(entryFilePath){
    console.log("entryFilePath=",entryFilePath)
    entryFile = entryFilePath;
    return [];
}



module.exports = {
     serializer: {
        createModuleIdFactory: createModuleIdFactory,
        processModuleFilter: processModulesFilter,
        getModulesRunBeforeMainModule:getModulesRunBeforeMainModule
        /* serializer options */
      }
};