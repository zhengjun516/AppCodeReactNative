# -*- coding:utf-8 -*-

import os
import json
import subprocess
import getopt
import sys
import hashlib

from logger import logger
ROOT_PATH = os.path.normpath(os.path.dirname(os.path.abspath(os.path.join(__file__,'../'))))
PATH = os.path.normpath(os.path.dirname(os.path.abspath(__file__))) + '/'
BUNDLES_PATH=ROOT_PATH+"/"+"bundles"

class BundleConfig:
    def __init__(self,bundleDir,version,outputFile,entryFile,configFile,isBase,isPreload):
        self.bundleDir=bundleDir
        self.version=version
        self.outputFile=outputFile
        self.entryFile=entryFile
        self.configFile=configFile
        self.isBase=isBase
        self.isPreload = isPreload
        self.md5=""
def get_root_path(path):
    filePath = os.path.normpath(os.path.dirname(os.path.abspath(os.path.join(__file__,"../"))))+"/"+path
    logger.info("filePath:"+filePath)
    return filePath

def get_file_md5(file_path):
    logger.info("file_path:"+file_path)
    if os.path.isfile(file_path):
        file = open(file_path,'rb')
        md5 = hashlib.md5()
        md5.update(file.read())
        md5_value = md5.hexdigest()
        logger.info("md5_value:"+str(md5_value))
        file.close()
    else:
        return ""
    return str(md5_value).lower()

def get_suffix_file(prefix,file_folder):
    if not os.path.exists(file_folder):
        return None

    files = os.listdir(file_folder)
    for index in range(len(files)):
        if prefix == os.path.splitext(files[index])[1]:
            logger.info("get_suffix_file:"+files[index])
            return files[index]

    return None

def create_manifest(module_path,bundleConfig):
    manifest = {'bundleDir':'','version':1,'mainComponent':'','md5':'',"isBase":False,'isPreload':False}
    manifest['bundleDir'] = bundleConfig.bundleDir;
    manifest['version'] = bundleConfig.version;

    bundle_file_folder = module_path+"/"+bundleConfig.bundleDir+"/"
    bundle_file = get_suffix_file(".bundle",bundle_file_folder)
    manifest['bundleFile'] = bundle_file
    manifest['isBase'] = bundleConfig.isBase;
    manifest['isPreload']=bundleConfig.isPreload
    manifest['md5'] = get_file_md5(bundle_file_folder+bundle_file)

    app_json_file_folder = module_path+"/"+bundleConfig.bundleDir+"/raw/"
    app_json_file = get_suffix_file(".json",app_json_file_folder)
    if app_json_file is not None:
        with open(app_json_file_folder+app_json_file,'r', encoding='UTF-8') as app_file:
            app_json = json.load(app_file)
            manifest['mainComponent'] = app_json["name"]

    manifest_json_file =  module_path+"/"+bundleConfig.bundleDir+"/"+"manifest.json"
    with open(manifest_json_file,'w', encoding='UTF-8') as manifest_file:
        json.dump(manifest,manifest_file)

def zip(module_path,zipFile,bundleDir):
    logger.info("module_path:"+module_path+"  zipFile:"+zipFile+" modelPath:"+bundleDir)

    #subprocess.check_call(['rm ','-r',path+"/"+zipFile])

    os.system('cd '+module_path+'&& zip -r -o '+module_path+"/"+zipFile+" ./"+bundleDir)
    #subprocess.check_call(['cd',path,'&& zip','-r','-m','-o',path+"/"+zipFile,modelPath])


def build_bundle(path,bundleConfig,dev):
    logger.info("buildBundle:dev:"+str(dev))
    if len(bundleConfig.bundleDir)>0:
        bundle_path=path+"/"+bundleConfig.bundleDir+'/'
        bundle_res_path=path+"/"+bundleConfig.bundleDir+'/'
    else:
        bundle_path=path
        bundle_res_path=path+"/"

    if not os.path.exists(bundle_path):
        os.makedirs(bundle_path,0o777)

    if not os.path.exists(bundle_res_path):
        os.makedirs(bundle_res_path,0o777)

    subprocess.check_call(['yarn','react-native','bundle','--platform','android',\
                           '--dev','true'if dev else 'false',\
                           '--entry-file',get_root_path(bundleConfig.entryFile),\
                           '--bundle-output',bundle_path+bundleConfig.outputFile,\
                           '--assets-dest',bundle_res_path,\
                           '--config',get_root_path(bundleConfig.configFile)])

    create_manifest(path,bundleConfig)
    zip(path,bundleConfig.bundleDir+".zip",bundleConfig.bundleDir)


def parse_config(configFile):
    logger.info("parseConfig:configFile:"+configFile)

    bundleConfigList=[]
    with open(configFile,'r', encoding='UTF-8') as configFile:
        config = json.load(configFile)
        baseBundle = BundleConfig(config['bundleDir'],config['version'],config['outputFile'],config['entryFile'],config['configFile'],config['isBase'],config['isPreload'])
        bundleConfigList.append(baseBundle)

    return bundleConfigList

#开始执行
def cmd():
    logger.info("start build")
    try:
        opts, args = getopt.getopt(sys.argv[1:], 'hc:d:o', ['help', 'config=', 'dev=','output='])
        config_file=None
        dev=None
        output=None
        for opt, arg in opts:
            if opt in ('-h', '--help'):
                sys.exit(1)
            elif opt in ('-c', '--config'):
                config_file=arg
            elif opt in ('-d', '--debug'):
                dev=arg.lower() in ["true", "1"]
            elif opt in ('-o', '--output'):
                output=arg

        bundleConfigList = parse_config(config_file)
        for bundleConfig in bundleConfigList:
            build_bundle(BUNDLES_PATH,bundleConfig,dev)

    except getopt.GetoptError as e:
        logger.info('GetoptError', e)
        sys.exit()

    logger.info("***** End Building  *****")
    logger.close()

'''
主程序入口
'''
if __name__ == '__main__':
    cmd()