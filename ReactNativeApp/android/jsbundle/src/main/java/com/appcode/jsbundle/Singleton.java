package com.appcode.jsbundle;

import java.util.concurrent.ConcurrentHashMap;

public abstract class Singleton {

	private static ConcurrentHashMap<String,Object> mInstanceMap = new ConcurrentHashMap<>();

	public static final <T> T get(Class<T> clazz) {
		try {
			if(mInstanceMap.containsKey(clazz.hashCode())){
				return (T)mInstanceMap.get(String.valueOf(clazz.hashCode()));
			}else{
				T t = clazz.newInstance();
				mInstanceMap.put(String.valueOf(clazz.hashCode()),t);
				return t;
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
