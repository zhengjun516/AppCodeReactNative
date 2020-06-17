import React, {Component} from 'react';

import { AsyncStorage } from "react-native"

export default class StorageManager{

    static add(key, value) {
        return new Promise((resolve,reject)=>{
             AsyncStorage.setItem(key, JSON.stringify(value), (error,result)=>{
                if(!error){
                    try{
                        resolve(JSON.parse(result));
                    }catch(e){
                        reject(e);

                    }
                }else{
                    reject(error);
                }
             });
        })
    }

    static remove(key){
      return new Promise((resolve,reject)=>{
          AsyncStorage.removeItem(key,(error,resule)=>{
                if(!error){
                    try{
                        resolve(JSON.parse(result));
                    }catch(e){
                        reject(e);

                    }
                }else{
                    reject(error);
                }
          });
      })
    }

    static  update(key, value) {
        return new Promise((resolve,reject)=>{
             AsyncStorage.get(key).then(item => {
                            value = typeof value === 'string' ? value : merge({}, item, value);
                            return AsyncStorage.setItem(key, JSON.stringify(value));
             });
        })
    }




}