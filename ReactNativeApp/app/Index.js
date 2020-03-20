import React, {Component} from 'react';
import {Platform,StatusBar, StyleSheet,ToastAndroid, DeviceEventEmitter,Text, TouchableOpacity,View,NativeModules} from 'react-native';

export default class Index extends Component<Props> {

   _jumpToUI(){
        const {navigation} = this.props;
        if(navigation){
            navigation.navigate('UI')
        }
   }
   _jumpToWidget(){
       const {navigation} = this.props;
       if(navigation){
           navigation.navigate('Widget')
       }
   }

   _jumpToJSNative(){
        const {navigation} = this.props;
        if(navigation){
            navigation.navigate('JSNative')
        }
   }


    _jumpToWebView(){
            const {navigation} = this.props;
            if(navigation){
                navigation.navigate('WebView')
            }
    }

     _jumpToDb(){
                const {navigation} = this.props;
                if(navigation){
                    navigation.navigate('Db')
                }
     }

     _jumpToNetWork(){
                const {navigation} = this.props;
                if(navigation){
                    navigation.navigate('NetWork')
                }
     }

   	_jumpToHome(){
        const {navigation} = this.props;
        if(navigation){
            navigation.push('Home')
        }
    }

    render() {
        return(
        <View style={styles.container}>
            <TouchableOpacity onPress={this._jumpToUI.bind(this)}>
                <Text style={styles.hello}>基础控件</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={this._jumpToWidget.bind(this)}>
                <Text style={styles.hello}>自定义组件</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={this._jumpToJSNative.bind(this)}>
                <Text style={styles.hello}>Js与Java互相调用</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={this._jumpToWebView.bind(this)}>
                 <Text style={styles.hello}>WebView界面</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={this._jumpToDb.bind(this)}>
                  <Text style={styles.hello}>数据库</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={this._jumpToNetWork.bind(this)}>
                  <Text style={styles.hello}>网络</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={this._jumpToWebView.bind(this)}>
                   <Text style={styles.hello}>缓存</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={this._jumpToHome.bind(this)}>
                             <Text style={styles.hello}>Tab界面</Text>
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