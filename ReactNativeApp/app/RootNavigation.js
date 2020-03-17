import * as React from 'react';

import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

import TabNavigation from './TabNavigation'


const Stack = createStackNavigator();

export default function StackNavigation(){

	return(
        <NavigationContainer>
        	<Stack.Navigator 
        		initialRouteName="Home"
        	    >
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