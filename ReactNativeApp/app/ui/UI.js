import React, {Component} from 'react';
import {Platform,StyleSheet,View} from 'react-native';

export default class UI extends Component<Props> {


	render() {
	  	return(
	  	<View style={styles.container}>

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