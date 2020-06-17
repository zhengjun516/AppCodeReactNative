const pathSep = require('path').sep;
const useIndex = true;//是否使用递增的数字作为模块的id，如果为false则使用模块相对的路径名作为模块id
let curBaseModuleId = -100;//基础包ModuleId
let curBusinessModuleId = -100;//业务包ModuleId
let baseModuleIdMap = [];
let businessModuleIdMap = [];
const fs=require("fs");
var baseMappingPath;
var businessMappingPath;

/** 通过自增长的index来确定moduleID，优点是能使用rambundle且减小了bundle包的大小，隐藏了模块路径，提升安全性，缺点是需要保存和依赖已经打包进去的模块的路径和id的对应信息，需要注意打包顺序和重复依赖的打包模块*/
/**
 * projectRootPath:工程目录
 * path:js模块路径
 * entry:打包的入口
 * isBusiness:是否是业务包
 * */
function getModuleIdByIndex(projectRootPath,path,entry,isBusiness){


  if(curBaseModuleId==-100) {
    curBaseModuleId =  - 1;//基础包的Module都是从0开始的
  }
  if(baseMappingPath==null) {
    baseMappingPath = __dirname + pathSep + "platformMap.json";
  }
  if(baseModuleIdMap.length == 0){
    if(fs.existsSync(baseMappingPath)){
      baseModuleIdMap = require(baseMappingPath);
      if(baseModuleIdMap.length!=0) {
	      curBaseModuleId = baseModuleIdMap[baseModuleIdMap.length - 1].id;
      }
    }
  }

  const moduleIdConfig = require('./ModuleIdConfig.json');
  if(isBusiness){
    if(businessMappingPath==null) {
      businessMappingPath = __dirname + pathSep + entry.replace(".js", "") + "Map.json";
    }
    if(businessModuleIdMap.length==0){
      if(fs.existsSync(businessMappingPath)){
        businessModuleIdMap = require(businessMappingPath);
        curBusinessModuleId = businessModuleIdMap[businessModuleIdMap.length-1].id;
      }else if(curBusinessModuleId==-100){
        curBusinessModuleId = moduleIdConfig[entry]-1;//根据业务包moduleid的配置取初始modueId
      }
    }
  }
  let pathRelative = null;
  if (path.indexOf(projectRootPath) == 0) {
    pathRelative = path.substr(projectRootPath.length + 1);
  }

  const findPlatformItem = baseModuleIdMap.find((value)=>{return value.path==pathRelative});
  const findBuzItem = businessModuleIdMap.find((value)=>{return value.path==pathRelative});
  if(findPlatformItem){
       return findPlatformItem.id;
  }else if(findBuzItem){
       return findBuzItem.id;
  }else{
       if(!isBusiness){
          //基础包
          curBaseModuleId = ++curBaseModuleId;
          baseModuleIdMap.push({id: curBaseModuleId, path: pathRelative});
          fs.writeFileSync(baseMappingPath, JSON.stringify(baseModuleIdMap));
          return curBaseModuleId;
        }else{
           //业务包
           curBusinessModuleId = ++curBusinessModuleId;
           businessModuleIdMap.push({id: curBusinessModuleId, path: pathRelative});
           fs.writeFileSync(businessMappingPath, JSON.stringify(businessModuleIdMap));
           return curBusinessModuleId;
        }
  }
}

/** 根据模块路径返回moduleId，优点是简单且确保唯一，缺点是无法使用rambundle打包方式*/
function getModuleIdByName(projectRootPath,path){
  let name = '';
  if (path.indexOf('node_modules' + pathSep + 'react-native' + pathSep + 'Libraries' + pathSep) > 0) {
    name = path.substr(path.lastIndexOf(pathSep) + 1);
  } else if (path.indexOf(projectRootPath) == 0) {
    name = path.substr(projectRootPath.length + 1);
  }
  name = name.replace('.js', '');
  name = name.replace('.png', '');
  let regExp = pathSep == '\\' ? new RegExp('\\\\', "gm") : new RegExp(pathSep, "gm");
  name = name.replace(regExp, '_');//把path中的/换成下划线
  return name;
}

function getModuleId(projectRootPath,path,entry,isBusiness){
  if(useIndex){
    return getModuleIdByIndex(projectRootPath,path,entry,isBusiness);
  }
  return getModuleIdByName(projectRootPath,path);
}


module.exports={getModuleId,useIndex}
