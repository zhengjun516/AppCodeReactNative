/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import {name2 as appName2} from './app2.json';
import RootNavigation from './app/RootNavigation'

AppRegistry.registerComponent(appName, () => RootNavigation);
AppRegistry.registerComponent(appName2, () => App);
