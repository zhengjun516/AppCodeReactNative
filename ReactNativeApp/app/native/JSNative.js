import React, {Component} from 'react';
import {Platform, StyleSheet,ToastAndroid, DeviceEventEmitter,Text, TouchableOpacity,View,NativeModules} from 'react-native';

export default class JSNative extends Component<Props> {
	
	constructor(props){
		super(props);
		this.state={
			text:"river",
			text2:"默认",
		}
	}

	componentDidMount():void{
		DeviceEventEmitter.addListener("EventName",function(msg){
			let rest = NativeModules.CommModule.MESSAGE;
			ToastAndroid.show("DeviceEventEmitter收到消息："+ "\n" + rest+"\n"+msg, ToastAndroid.SHORT)
		})
	}

	_callPhone(){
		NativeModules.CommModule.rnCallNative("18910389825");
	}

	_nativeCallJs(){
		NativeModules.CommModule.nativeCallRn();
	}



	render() {
	  	return(
	  	<View style={styles.container}>
        	<TouchableOpacity onPress={this._callPhone.bind(this)}>
          		<Text style={styles.hello}>调用Native拨打电话</Text>
        	</TouchableOpacity>

        	<TouchableOpacity onPress={this._nativeCallJs.bind(this)}>
          		<Text style={styles.hello}>Native调用JS</Text>
        	</TouchableOpacity>

        	<TouchableOpacity>
          		<Text style={styles.hello}>{this.state.text2}</Text>
        	</TouchableOpacity>

        	<TouchableOpacity>
          		<Text style={styles.hello}>{this.state.text2}</Text>
        	</TouchableOpacity>
      </View>
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
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});