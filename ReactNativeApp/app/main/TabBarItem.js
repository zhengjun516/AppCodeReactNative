import React,{Component} from 'react';
import {Image} from 'react-native';

export default class TabBarItem extends Component {

    render() {
    console.log( {uri:'file:///sdcard/ReactNativeApp/bundles/bundle03/drawable-mdpi/'+this.props.selectedImage})
        return(
            <Image source={ this.props.focused ? {uri:'file:///sdcard/ReactNativeApp/bundles/bundle03/drawable-mdpi/'+this.props.selectedImage} : {uri:'file:///sdcard/ReactNativeApp/bundles/bundle03/drawable-mdpi/'+this.props.normalImage} }
                style={{ tintColor:this.props.tintColor,width:25,height:25 } }
            />
        )
    }
    
}