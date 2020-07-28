import * as React from 'react';

import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

import TabNavigation from './main/TabNavigation'
import WebViewPage from './component/WebViewPage'
import SplashView from './navigation/SplashView'
import SelectCityPage from './home/SelectCityPage'
import QRScanPage from './home/QRScanPage'
import FoodPage from './home/FoodPage'
import DetailPage from './detail/DetailPage'
import MineOrderPage from './order/MineOrderPage'
import UserinfoPage from './mine/UserinfoPage'
import AddressPage from './mine/AddressPage'
import LoginPage from './mine/LoginPage'
import AboutPage from './mine/AboutPage'
import SettingPage from './mine/SettingPage'

const Stack = createStackNavigator();

export default function StackNavigation(){

	return(
        <NavigationContainer>
        	<Stack.Navigator
        		initialRouteName="TabNav"
        	    >
        	    <Stack.Screen
        	        name="SplashView"
        	        component={SplashView}
        	        options={{ headerShown: false }}
                    />
                <Stack.Screen
                    name="SelectCityPage"
                    component={SelectCityPage}
                    options={{ headerShown: false }}
                     />
                 <Stack.Screen
                     name="FoodPage"
                     component={FoodPage}
                     options={{ headerShown: false }}
                      />
                <Stack.Screen
                    name="DetailPage"
                    component={DetailPage}
                    options={{ headerShown: false }}
                     />
                <Stack.Screen
                    name="MineOrderPage"
                    component={MineOrderPage}
                    options={{ headerShown: false }}
                     />
                <Stack.Screen
                    name="UserinfoPage"
                    component={UserinfoPage}
                    options={{ headerShown: false }}
                     />
                <Stack.Screen
                    name="AddressPage"
                    component={AddressPage}
                    options={{ headerShown: false }}
                     />
                <Stack.Screen
                    name="LoginPage"
                    component={LoginPage}
                    options={{ headerShown: false }}
                     />
                <Stack.Screen
                       name="SettingPage"
                       component={SettingPage}
                       options={{ headerShown: false }}
                        />
                <Stack.Screen
                    name="AboutPage"
                    component={AboutPage}
                    options={{ headerShown: false }}
                     />
                <Stack.Screen
                    name="WebViewPage"
                    component={WebViewPage}
                    options={{ headerShown: false }}
                     />
                <Stack.Screen
                    name="QRScanPage"
                    component={QRScanPage}
                    options={{ headerShown: false }}
                     />

        		<Stack.Screen
        			name="TabNav"
        			component={TabNavigation}
        			options={{headerShown: false }}
        			/>
        		{props => <TabNavigation {...props} navigation='navigator' />}
        	</Stack.Navigator>
        </NavigationContainer>
	)
}