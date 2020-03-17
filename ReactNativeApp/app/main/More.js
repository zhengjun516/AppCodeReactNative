import React, {Component} from 'react';
import {Platform, StyleSheet, Text,ScrollView,Image, View} from 'react-native';

export default class More extends Component<Props>{
	render(){
		return(
			<ScrollView 
				contentContainerStyle={styles.scrollViewStyle}
			>
           
			</ScrollView>
		);
	}
}

const styles = StyleSheet.create({
    scrollViewStyle:{
        backgroundColor:'#e8e8e8',
    }
    ,
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
})