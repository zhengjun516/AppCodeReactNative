# -*- coding:utf-8 -*-

import os
import json
import subprocess

from os import path
from pathlib import Path
from logger import logger
ROOT_PATH = os.path.normpath(os.path.dirname(os.path.abspath(os.path.join(__file__,'../'))))
PATH = os.path.normpath(os.path.dirname(os.path.abspath(__file__))) + '/'

class BundleConfig:
    def __init__(self,outputDir,outputFile,outputResDir,entryFile,configFile):
        self.outputDir=outputDir
        self.outputFile=outputFile
        self.outputResDir=outputResDir
        self.entryFile=entryFile
        self.configFile=configFile
def get_root_path(path):
    filePath = os.path.normpath(os.path.dirname(os.path.abspath(os.path.join(__file__,"../"))))+"/"+path
    logger.info("filePath:"+filePath)
    return filePath

def build_bundle(bundleConfig,dev):

    logger.info("buildBundle:dev:"+str(dev))

    if not path.exists(bundleConfig.outputDir):
        os.makedirs(bundleConfig.outputDir)
    if not path.exists(bundleConfig.outputResDir):
        os.makedirs(bundleConfig.outputResDir,0o777)

    subprocess.check_call(['yarn','react-native','bundle','--platform','android',\
                           '--dev','true'if dev else 'false',\
                           '--entry-file',get_root_path(bundleConfig.entryFile),\
                           '--bundle-output',get_root_path(bundleConfig.outputDir+'/'+bundleConfig.outputFile),\
                           '--assets-dest',get_root_path(bundleConfig.outputResDir),\
                           '--config',get_root_path(bundleConfig.configFile)])


def parse_config(configFile):
    logger.info("parseConfig:configFile:"+configFile)

    bundleConfigList=[]
    with open(configFile,'r', encoding='UTF-8') as configFile:
        configJson = json.load(configFile)
        baseConfig = configJson['base']
        baseBundle = BundleConfig(baseConfig['outputDir'],baseConfig['outputFile'],baseConfig['outputResDir'],baseConfig['entryFile'],baseConfig['configFile'])
        bundleConfigList.append(baseBundle)
        business = configJson['business']
        businessBundle = BundleConfig(business['outputDir'],business['outputFile'],business['outputResDir'],business['entryFile'],business['configFile'])
        bundleConfigList.append(businessBundle)
        business = configJson['business2']
        businessBundle = BundleConfig(business['outputDir'],business['outputFile'],business['outputResDir'],business['entryFile'],business['configFile'])
        bundleConfigList.append(businessBundle)

    return bundleConfigList

def cmd():
    logger.info("start build")
    print(PATH)
    bundleConfigList = parse_config(PATH+"config.json")
    for bundleConfig in bundleConfigList:
        build_bundle(bundleConfig,False)

if __name__ == '__main__':
    cmd()