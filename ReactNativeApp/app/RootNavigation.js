import * as React from 'react';

import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

import TabNavigation from './main/TabNavigation'
import JSNative from './native/JSNative'
import Index from './Index'
import UI from './ui/UI'
import Widget from './widget/Widget'
import Db from './db/Db'
import NetWork from './network/NetWork'
import WebView from './webview/WebView'


const Stack = createStackNavigator();

export default function StackNavigation(){

	return(
        <NavigationContainer>
        	<Stack.Navigator 
        		initialRouteName="Index"
        	    >
        	    <Stack.Screen
        	        name="Index"
        	        component={Index}
        	        options={{ headerShown: false }}
                    />
                <Stack.Screen
                    name="UI"
                    component={UI}
                    options={{ headerShown: false }}
                    />
                <Stack.Screen
                    name="Widget"
                    component={Widget}
                    options={{ headerShown: false }}
                    />
                <Stack.Screen
                    name="JSNative"
                    component={JSNative}
                    options={{ headerShown: false }}
                    />
        	    <Stack.Screen
                    name="Db"
                    component={Db}
                    options={{ headerShown: false }}
                    />
        	    <Stack.Screen
                    name="NetWork"
                    component={NetWork}
                    options={{ headerShown: false }}
                    />
        	    <Stack.Screen
                    name="WebView"
                    component={WebView}
                    options={{ headerShown: false }}
                    />
        		<Stack.Screen
        			name="Home" 
        			component={TabNavigation} 
        			options={{ headerShown: false }}
        			/>
        		{props => <Home {...props} navigator='navigator' />}
        	</Stack.Navigator>
        </NavigationContainer>
	)
}