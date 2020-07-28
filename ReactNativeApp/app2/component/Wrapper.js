
import React, { Component } from 'react'
import { Navigator, View } from 'react-native'

import SplashView from "../navigation/SplashView";

export default class Wrapper extends Component{
    constructor(props){
      super(props)
    }
    render(){
        return(
          <View style={{flex: 1, justifyContent: 'flex-end'}}>
              <SplashView />
          </View>
        )
    }
}
