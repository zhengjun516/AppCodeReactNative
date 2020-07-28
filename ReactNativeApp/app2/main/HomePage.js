/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * 首页模块
 */

import React, {Component} from 'react';
import {
    Platform,
    StyleSheet,
    Dimensions,
    Text,
    BackHandler,
    FlatList,
    View
} from 'react-native';

import RefreshListView , { RefreshState } from '../component/RefreshListView'
import HomeActionBar from '../home/HomeActionBar'
import ProductItemCell from '../home/ProductItemCell'
import MenuView from '../home/MenuView'
import ActiveView from '../home/ActiveView'
import LimitTimeView from '../home/LimitTimeView'
import WebViewPage from '../component/WebViewPage'
import SpacingView from '../component/SpacingView'

import DetailPage from '../detail/DetailPage'
import FoodPage from '../home/FoodPage'

import api from '../config/api'
const { width } = Dimensions.get('window');

export default class HomeScreen extends Component {

    state: {
        actives: Array<Object>,
        dataSource: []
    };

    constructor(props: Object) {
        super(props)
        this.state = {
            actives: [],
            dataSource: []
        }
    }


    componentDidMount() {
         //this.backHandler = BackHandler.addEventListener('hardwareBackPress', this.handleBackPress);
          this.refs.listView.startHeaderRefreshing();
      }

  /*  componentWillUnmount() {
        this.backHandler.remove();
    }

    handleBackPress = () => {
        BackHandler.exitApp();
    }*/



    //获取打折数据和推荐商品
    requestData() {
        this.requestActives();
        this.requestRecommend()
    }

    requestActives() {
        fetch(api.actives)
            .then((response) => response.json())
            .then((json) => {
                console.log(JSON.stringify(json));
                this.setState({ actives: json.data })
            })
            .catch((error) => {
               console.log('fetch error:'+error);
            })
    }

    //请求推荐列表
    requestRecommend() {
        fetch(api.recommend)
            .then((response) => response.json())
            .then((json) => {
                console.log(JSON.stringify(json));

                let dataList = json.data.map((info) => {
                    return {
                        id: info.id,
                        imageUrl: info.squareimgurl,
                        title: info.mname,
                        subtitle: `[${info.range}]${info.title}`,
                        price: info.price
                    }
                })

                this.setState({
                    dataSource: dataList
                })
                setTimeout(() => {
                    this.refs.listView.endRefreshing(RefreshState.NoMoreData)
                }, 500);
            })
            .catch((error) => {
                this.refs.listView.endRefreshing(RefreshState.Failure)
            })
    }

    loadMenuInfos() {
        return api.menuInfo;
    }

    onMenuSelected(index: number) {
        if(index==0){
            this.props.navigation.navigate('FoodPage')
        }else {
            alert('你点击了：'+index)
        }
    }

    onGridSelected(index: number) {
        let discount = this.state.actives[index]
        if (discount.type == 1) {
            let url='http://evt.dianping.com/synthesislink/5651.html';
            // let location = discount.tplurl.indexOf('https')
            // let url = discount.tplurl.slice(location)
            let title=discount.title;
            if(url!=null){
                this.props.navigation.navigate('WebViewPage',{
                        url: url,
                        title: title,
                    }
                )
            }
        }
    }

    //详情页面
    toDetail() {
         const {navigation} = this.props;
         if(navigation){
             navigation.navigate('DetailPage')
         }
    }

    renderHeader() {
        return (
            <View style={styles.flexStyle}>
                <MenuView menuInfos={this.loadMenuInfos()} onMenuSelected={(index) => this.onMenuSelected(index)} />
                <SpacingView/>
                <ActiveView infos={this.state.actives} onGridSelected={(index) => this.onGridSelected(index)} />
                <SpacingView/>
                <LimitTimeView {...this.props}/>
                <SpacingView/>
                <View style={styles.recommendHeader}>
                    <Text style={styles.text}>猜你喜欢</Text>
                </View>
            </View>
        )
    }

    _renderItem =(item)=>{
      return (<ProductItemCell info={item.item} onPress={() => { this.toDetail()}}
             />)
    }

    render() {
        return (
            <View style={styles.flexStyle}>
                <HomeActionBar {...this.props}/>
                <RefreshListView
                    ref='listView'
                    data={this.state.dataSource}
                    ListHeaderComponent={() => this.renderHeader()}
                    renderItem={this._renderItem}
                    keyExtractor={(item) => item.id}
                    onHeaderRefresh={() => this.requestData()}
                />

            </View>
        );
    }
}

const styles = StyleSheet.create({
    flexStyle: {
        flex: 1,
        width:width
    },
    container: {
        flex: 1,
        backgroundColor: '#f3f3f3',
        marginTop:Platform.OS === 'android'?0:20
    },
    text: {
        fontSize: 14,
        color: '#222222',
    },
    textStyle: {
        fontSize: 20,
        alignItems: 'center'
    },
    recommendHeader: {
        flex:1,
        height: 35,
        justifyContent: 'center',
        borderWidth: 1,
        borderColor: '#e9e9e9',
        paddingVertical: 8,
        paddingLeft: 20,
        backgroundColor: 'white'
    },

});

