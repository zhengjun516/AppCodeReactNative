import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View,Image,ScrollView} from 'react-native';

export default class Mine extends Component<Props>{

	render(){
		return(
			<View>

			</View>
		)
	}
}

const styles = StyleSheet.create({
    navImageStyle:{
        position:'absolute',
        right:10,
        width: Platform.OS === 'ios' ? 30 : 25,
        height: Platform.OS === 'ios' ? 30 : 25
    },
    navViewStyle:{
        backgroundColor:'rgba(255,96,0,1.0)',
        height:50,
        flexDirection:'row',
        alignItems: 'center',
        justifyContent:'center',
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