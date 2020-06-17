import React, {Component} from 'react';
import {Platform, StyleSheet,Button,Text,View,TextInput} from 'react-native';

import StorageManager from './StorageManager'

export default class Db extends Component<Props> {

	constructor(props){
		super(props);
		this.states={
            userNameKey:"",
            userNameValue:"",
            saveSuccess:false,
            text:'',
		}
	}

	createData(){
       StorageManager.add(key,JSON.stringify(value))
       .then((result)=>{
            this.setState({
                saveSuccess:true
            })
            console.log(result);
       }).catch((error)=>{
               console.log(error);
       });
	}

    _getData(key){

    }

    _updateData(key,value){
        StorageManager.update(key,value)
        .then((result)=>{
            console.log(result);
        }).catch((error)=>{
            console.log(error);
        });
    }

    _removeData(key){
        StorageManager.remove(key)
        .then((result)=>{
           this.setState({
                        userNameKey:key,
                        userNameValue:result
           })
           console.log(result);
        }).catch((error)=>{
           console.log(error);
        });
    }

	render() {
	  	return(
	  	<View style={styles.container}>
	  	    <View style={styles.inputContainer}>
	  	        <Text>key:</Text>
                <TextInput
                      style={{height: 40}}
                      placeholder="Type here to translate!"
                      onChangeText={(text) => this.setState({text})}
                    />
	  	    </View>
	  	    <View style={styles.inputContainer}>
            	  	        <Text>value:</Text>
                            <TextInput
                                  style={{height: 40}}
                                  placeholder="Type here to translate!"
                                  onChangeText={(text) => this.setState({text})}
                                />
           </View>

           <View style={styles.buttonContainer}>
            <Button
              onPress={createData("userName1","haha")}
              title="添加数据"
            />
            <Button
              onPress={this._removeData("userName1").bind(this)}
              title="删除数据"
            />
            <Button
              onPress={this._updateData("userName1","haha1").bind(this)}
              title="修改数据"
            />
            <Button
              onPress={this._getData("userName1").bind(this)}
              title="查看数据"
            />
            </View>
        </View>
	  	);
	}
}


const styles = StyleSheet.create({
  container: {
    flexDirection:'column',
    paddingTop:20,
  },
  inputContainer:{
    flexDirection:'row',
    justifyContent:'center',
    alignItems:'center',
    marginBottom:10,
  },
  buttonContainer:{
    flexDirection:'row',
    justifyContent:'space-around',
  }
});