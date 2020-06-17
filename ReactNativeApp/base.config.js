const pathSep = require('path').sep;
const fs = require("fs");
const moduleMapDir = "config";
const getModuleId = require('./config/getModuleId.js').getModuleId;
const useIndex = require('./config/getModuleId.js').useIndex;
let entryFile = "./base.js";

function createModuleIdFactory(){
    const projectRootPath = __dirname;
    return (path)=>{
         let name = getModuleId(projectRootPath,path,entryFile,false);
        if(useIndex !== true) {//存储基础包的js模块名，只在使用模块名打包时有用，使用递增序列时直接判断数字是否小于100000来判断是否时基础包
	      const platformNameArray = require('./config/platformNameMap.json');
	      if(!platformNameArray.includes(name)) {
		      platformNameArray.push(name);
		      const platformMapDir = __dirname + pathSep + moduleMapDir;
		      const platformMapPath = platformMapDir + pathSep + "platformNameMap.json";
		      fs.writeFileSync(platformMapPath, JSON.stringify(platformNameArray));
	      }
      }

      return name;
    }
}

module.exports = {
    serializer: {
      createModuleIdFactory:createModuleIdFactory,
        /* serializer options */
    }
};
