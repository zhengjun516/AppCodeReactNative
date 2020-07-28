import {StackNavigator} from 'react-navigation'

import TabNavigation from '../main/TabNavigation'
import WebViewPage from '../component/WebViewPage'


const Navigation = StackNavigator({
    TabNavigation: {screen: TabNavigation},
    WebViewPage: {screen: WebViewPage},

},{
    headerMode: 'none',
});

export default Navigation
