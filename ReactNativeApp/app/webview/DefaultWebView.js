import React, {Component} from 'react';
import {Platform, StyleSheet,ToastAndroid, DeviceEventEmitter,Text, TouchableOpacity,View,NativeModules} from 'react-native';
import { WebView } from 'react-native-webview';

export default class DefaultWebView extends Component<Props> {

	constructor(props){
		super(props);
	}

	componentDidMount():void{

	}



	render() {
	  	return(
	  	<WebView
            source={{ uri: 'https://www.baidu.com' }}
            style={{ marginTop: 20 }}
            />
	  	);
	}
}


const styles = StyleSheet.create({
  hello: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
});