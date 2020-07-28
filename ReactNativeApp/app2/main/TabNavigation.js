import React, {Component} from 'react';
import { Text, View,BackHandler } from 'react-native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';

import TabBarItem from './TabBarItem'

import HomePage from './HomePage'
import NearbyPage from './NearbyPage'
import DiscoverPage from './DiscoverPage'
import OrderPage from './OrderPage'
import MinePage from './MinePage'

const Tab = createBottomTabNavigator();

export default class TabNavigation extends Component{

  /*  componentDidMount() {
       this.backHandler = BackHandler.addEventListener('hardwareBackPress', this.handleBackPress);
    }

    componentWillUnmount() {
       this.backHandler.remove();
    }

    handleBackPress = () => {
        console.log('TabNavigation',this.props.navigation);

        BackHandler.exitApp();
    }*/

  render() {
      return (
          <Tab.Navigator
            initialRouteName='HomePage'
            backBehavior='none'
            tabBarOptions={{
            activeTintColor: '#e91e63',
              showIcon: true,
            }}
          >
            <Tab.Screen
                name="HomePage"
                component={HomePage}
                options={{
                  tabBarLabel: 'HomePage',
                  tabBarIcon: ({focused,tintColor}) => (
                    <TabBarItem
                      tintColor={tintColor}
                      focused={focused}
                      normalImage={require('../../res/icon_tabbar_homepage.png')}
                      selectedImage={require('../../res/icon_tabbar_homepage_selected.png')} />

                  ),
                }}
                 />
            <Tab.Screen
                name="DiscoverPage"
                component={DiscoverPage}
                options={{
                  tabBarIcon: ({focused,tintColor}) => (
                    <TabBarItem
                      tintColor={tintColor}
                      focused={focused}
                      normalImage={require('../../res/icon_tabbar_merchant_normal.png')}
                      selectedImage={require('../../res/icon_tabbar_merchant_selected.png')} />
                  ),
                }}
                />
            <Tab.Screen
                name="OrderPage"
                component={OrderPage}
                options={{
                  tabBarIcon: ({focused,tintColor}) => (
                    <TabBarItem
                      tintColor={tintColor}
                      focused={focused}
                      normalImage={require('../../res/icon_tabbar_misc.png')}
                      selectedImage={require('../../res/icon_tabbar_misc_selected.png')}/>
                  ),
                }}
                />
              <Tab.Screen
                name="MinePage"
                component={MinePage}
                options={{
                  tabBarIcon: ({focused,tintColor}) => (
                    <TabBarItem
                      tintColor={tintColor}
                      focused={focused}
                       normalImage={require('../../res/icon_tabbar_mine.png')}
                       selectedImage={require('../../res/icon_tabbar_mine_selected.png')}
                       />
                  ),
                }}
                />
          </Tab.Navigator>);
   }
 }
