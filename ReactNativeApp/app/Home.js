import React, {Component} from 'react';
import {Platform, StyleSheet,Dimensions,Button, Text,Image,TextInput, View,StatusBar,ScrollView,TouchableOpacity } from 'react-native';

var {width, height} = Dimensions.get('window');

export default class Home extends Component<Props>{
	
	
	render(){
		return(
			 <View>
      			<StatusBar
                backgroundColor='rgba(255,96,0,1.0)'
                barStyle="light-content"
                />
            
           
        
    		</View>
		);
	}

}

const styles = StyleSheet.create({
    
});
