/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * 个人订单
 */

import React, {Component} from 'react';
import {
    Text,
    StyleSheet,
    Dimensions,
    View
} from 'react-native';
import ScrollableTabView, {DefaultTabBar} from 'react-native-scrollable-tab-view';
import NavigationBar from '../component/NavigationBar'
import ViewUtils from '../util/ViewUtils'
import OrderItemView from "./OrderItemView";
import Separator from "../component/Separator";

const { width } = Dimensions.get('window');

export default class MineOrderPage extends Component {

    constructor(props) {
        super(props);
    }

    onBackPress(e) {
        this.props.navigation.goBack();
    }
    render() {
        const  params  = this.props.route.params;
        return (
            <View style={styles.flexStyle}>
                <NavigationBar
                    navigation={this.props.navigation}
                    popEnabled={false}
                    leftButton={ViewUtils.getLeftButton(()=>this.onBackPress())}
                    title='我的订单'
                    titleColor='#000000'
                />
                <Separator/>
                <View style={styles.container}>
                    <ScrollableTabView
                        renderTabBar={() => <DefaultTabBar />}
                        initialPage={params.index}
                        tabBarUnderlineStyle={styles.lineStyle}
                        tabBarActiveTextColor='#06C1AE'>
                        <OrderItemView style={styles.textStyle} tabLabel='全部' {...this.props}/>
                        <OrderItemView style={styles.textStyle} tabLabel='待付款' {...this.props}/>
                        <OrderItemView style={styles.textStyle} tabLabel='待使用' {...this.props}/>
                        <OrderItemView style={styles.textStyle} tabLabel='待评价' {...this.props}/>
                        <OrderItemView style={styles.textStyle} tabLabel='退款/售后' {...this.props}/>
                    </ScrollableTabView>
                </View>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    flexStyle: {
        flex: 1,
    },
    container: {
        flex: 1,
    },
    lineStyle: {
        width: width / 5,
        height: 2,
        backgroundColor: '#06C1AE',
    },
    textStyle: {
        flex: 1,
        fontSize: 20,
        textAlign: 'center',
    },

});

