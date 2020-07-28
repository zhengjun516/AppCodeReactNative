/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * 二维码扫描
 */

import React, {Component} from 'react';
import {
    Text,
    StyleSheet,
    Dimensions,
    Image,
    View
} from 'react-native';
import NavigationBar from '../component/NavigationBar'
import ViewUtils from '../util/ViewUtils'
import Separator from "../component/Separator";

const { width } = Dimensions.get('window');

export default class AboutPage extends Component {

    constructor(props) {
        super(props);
    }

    onBackPress(e) {
        this.props.navigation.goBack();
    }

    render() {
        return (
                <View style={styles.container}>
                    <NavigationBar
                        navigator={this.props.navigation}
                        popEnabled={false}
                        leftButton={ViewUtils.getLeftButton(()=>this.onBackPress())}
                        title='扫一扫'
                        titleColor='#ffffff'/>
                    <Separator/>
                    <View style={styles.flexStyle}>

                    </View>
                </View>
        );
    }
}

const styles = StyleSheet.create({
    flexStyle: {
        flex: 1
    },
    container: {
          flex: 1,
          flexDirection: 'column',
          backgroundColor: '#ffffff',
    },
    image: {
        width:80,
        height:80,
        marginTop:50
    },
    text: {
        fontSize: 16,
        alignItems: 'center',
        margin: 10,
    },

});

