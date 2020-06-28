import React, {Component} from 'react';
import { Text, View,BackHandler } from 'react-native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';

import TabBarItem from './TabBarItem'

import Find from './Find'
import Home from './Home'
import Mine from './Mine'
import More from './More'

const Tab = createBottomTabNavigator();

export default class TabNavigation extends Component{

  componentDidMount() {
    this.backHandler = BackHandler.addEventListener('hardwareBackPress', this.handleBackPress);
  }

  componentWillUnmount() {
    this.backHandler.remove();
  }

    handleBackPress = () => {
      this.props.navigation.goBack();
      return true;
    }
  render() {
      return (
          <Tab.Navigator
            initialRouteName='Home'
            tabBarOptions={{
            activeTintColor: '#e91e63',
              showIcon: true,
            }}
          >
            <Tab.Screen
                name="Home"
                component={Home}
                options={{
                  tabBarLabel: 'Home',
                  tabBarIcon: ({focused,tintColor}) => (
                    <TabBarItem
                      tintColor={tintColor}
                      focused={focused}
                      normalImage={'res_images_icon_tabbar_homepage.png'}
                      selectedImage={'res_images_icon_tabbar_homepage_selected.png'} />
                  ),
                }}
                 />
            <Tab.Screen
                name="Find"
                component={Find}
                options={{
                  tabBarIcon: ({focused,tintColor}) => (
                    <TabBarItem
                      tintColor={tintColor}
                      focused={focused}
                      normalImage={'res_images_icon_tabbar_merchant_normal.png'}
                      selectedImage={'res_images_icon_tabbar_merchant_selected.png'} />
                  ),
                }}
                />
            <Tab.Screen
                name="More"
                component={More}
                options={{
                  tabBarIcon: ({focused,tintColor}) => (
                    <TabBarItem
                      tintColor={tintColor}
                      focused={focused}
                      normalImage={'res_images_icon_tabbar_misc.png'}
                      selectedImage={'res_images_icon_tabbar_misc_selected.png'}/>
                  ),
                }}
                />
              <Tab.Screen
                name="Mine"
                component={Mine}
                options={{
                  tabBarIcon: ({focused,tintColor}) => (
                    <TabBarItem
                      tintColor={tintColor}
                      focused={focused}
                      normalImage={'res_images_icon_tabbar_mine.png'}
                      selectedImage={'res_images_icon_tabbar_mine_selected.png'}
                       />
                  ),
                }}
                />
          </Tab.Navigator>);
   }
 }
