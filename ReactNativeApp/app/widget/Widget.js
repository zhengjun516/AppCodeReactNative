import React, {Component} from 'react';
import {Platform, StyleSheet,Text, TouchableOpacity,View} from 'react-native';
import ViewPager from '@react-native-community/viewpager';

export default class Widget extends Component<Props> {

	constructor(props){
		super(props);

	}

	render() {
	  	return(
        	  <ViewPager
                  style={styles.viewPager}
                  initialPage={0}>
                  <View style={styles.pageStyle} key="1">
                    <Text>First page</Text>
                  </View>
                  <View style={styles.pageStyle} key="2">
                    <Text>Second page</Text>
                  </View>
             </ViewPager>
	  	);
	}
}

const styles = StyleSheet.create({
 viewPager: {
    flex: 1
  },
  pageStyle: {
    alignItems: 'center',
    padding: 20,
  }
});