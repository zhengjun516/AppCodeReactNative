'use strict';
import React, {Component} from 'react';
import {
    StyleSheet,
    View,
    Text,
    TouchableOpacity,
    ScrollView,
    Dimensions,
} from 'react-native';

import Toast, {DURATION} from '../component/ToastUtil'

const SECTIONHEIGHT = 30;
const ROWHEIGHT = 40;
const ROWHEIGHT_BOX = 40;
var totalheight = []; //每个字母对应的城市和字母的总高度

const {width, height} = Dimensions.get('window');

var that;

const key_now = '当前';
const key_last_visit = '最近';
const key_hot = '热门';

export default class SortListView extends Component {

    constructor(props) {
        super(props);

       /* var getSectionData = (cityData, sectionID) => {
            return sectionID;
        };
        var getRowData = (cityData, sectionID, rowID) => {
            return cityData[sectionID][rowID];
        };*/

        let sections = []

        let ALL_CITY_LIST = this.props.allCityList;
        let CURRENT_CITY_LIST = this.props.nowCityList;
        let LAST_VISIT_CITY_LIST = this.props.lastVisitCityList;
        let HOT_CITY_LIST = this.props.hotCityList;

        let letterList = this._getSortLetters(ALL_CITY_LIST);

        let cityData = {};
        cityData[key_now] = CURRENT_CITY_LIST;
        cityData[key_last_visit] = LAST_VISIT_CITY_LIST;
        cityData[key_hot] = HOT_CITY_LIST;


        sections.push({key:key_now,data:CURRENT_CITY_LIST})
        sections.push({key:key_last_visit,data:LAST_VISIT_CITY_LIST})
        sections.push({key:key_hot,data:HOT_CITY_LIST})

        ALL_CITY_LIST.map(cityJson => {
            let key = cityJson.sortLetters.toUpperCase();
            if (cityData[key]) {
                let subList = cityData[key];
                subList.push(cityJson);
            } else {
                let subList = [];
                subList.push(cityJson);
                cityData[key] = subList;
                sections.push({key:key,data:subList})
            }
        });


        let sectionIDs = Object.keys(cityData);
        let rowIDs = sectionIDs.map(sectionID => {
            let thisRow = [];

            let count = cityData[sectionID].length;
            for (let ii = 0; ii < count; ii++) {
                thisRow.push(ii);
            }

            let eachheight = SECTIONHEIGHT + ROWHEIGHT * thisRow.length;
            if (sectionID === key_hot || sectionID === key_now || sectionID === key_last_visit) {
                let rowNum = (thisRow.length % 3 === 0)
                    ? (thisRow.length / 3)
                    : parseInt(thisRow.length / 3) + 1;

                console.log('sectionIDs===>' + sectionIDs + ", rowNum=====>" + rowNum);
                eachheight = SECTIONHEIGHT + ROWHEIGHT_BOX * rowNum;
            }else{
                console.log('sectionIDs===>' + sectionIDs + ", rowNum=====>" + thisRow.length);
            }
            totalheight.push(eachheight);

            return thisRow;
        });

        this.state = {
            dataSource: sections,
            letters: sectionIDs
        };

        that = this;
    }

    componentDidMount() {

    }

    _getSortLetters(dataList) {
        let list = [];
        for (let j = 0; j < dataList.length; j++) {
            let sortLetters = dataList[j].sortLetters.toUpperCase();

            let exist = false;
            for (let xx = 0; xx < list.length; xx++) {
                if (list[xx] === sortLetters) {
                    exist = true;
                }
                if (exist) {
                    break;
                }
            }
            if (!exist) {
                list.push(sortLetters);
            }
        }

        return list;
    }

    _cityNameClick(cityJson) {
        this.props.onSelectCity(cityJson);
    }

     // 点击右侧字母滑动到相应位置
     _scrollTo(index, letter) {
            let position = 0;
            for (let i = 0; i < index; i++) {
                position += totalheight[i]
            }
             console.log('_scrollTo=>index:'+index);
            this.refs.ScrollView.scrollTo({y: position})
     }

    _isGridLayoutLetters(letter){
        if(letter === key_hot || letter === key_now || letter === key_last_visit){
            return true;
        }else{
            return false;
        }
    }

    _renderRightLetters(letter, index) {
        return (
            <TouchableOpacity key={'letter_idx_' + index} activeOpacity={0.6} onPress={() => {
                this._scrollTo(index, letter)
            }}>
                <View style={this._isGridLayoutLetters(letter)?styles.gridLetter:styles.letter}>
                    <Text style={styles.letterText}>{letter}</Text>
                </View>
            </TouchableOpacity>
        );
    }

     _renderItemView(city, rowId) {
             return (
                        <TouchableOpacity key={'list_item_' + city.id} style={styles.rowView} onPress={() => {
                            that._cityNameClick(city)
                        }}>
                            <View style={styles.rowdata}>
                                <Text style={styles.rowdatatext}>{city.name}</Text>
                            </View>
                        </TouchableOpacity>
                    )
     }

     _renderGridLayout(cities, rowId) {
         let grids =[]
         for(let i=0; i < cities.length; i++){
            let city = cities[i];
            let gridItem = <TouchableOpacity key={'list_item_' + city.id} style={styles.rowViewBox} onPress={() => {
                                            that._cityNameClick(city)
                                        }}>
                                <View style={styles.rowdataBox}>
                                    <Text style={styles.rowDataTextBox}>{city.name}</Text>
                                </View>
                            </TouchableOpacity>
            grids.push(gridItem);
         }

         return (<View style={styles.gridContainer}>{grids}</View>);
     }

    _renderSectionItem(section,lists){
        let header = <View key={section.key} style={{height:SECTIONHEIGHT, backgroundColor:'#F4F4F4', justifyContent: 'center'}} >
                           <Text style={{color: "#999", fontSize: 14, marginLeft: 20, }}>{section.key}</Text>
                     </View>
        lists.push(header)

       if (section.key === key_now || section.key === key_hot || section.key === key_last_visit) {
            lists.push(that._renderGridLayout(section.data,lists.length-1))
        }else{
            for(let i = 0; i < section.data.length; i++){
                let city = section.data[i];
                lists.push(that._renderItemView(city,lists.length-1))
            }
        }
    }

    _renderListSectionHeader = (item)=>{
        let sectionObj = item.section;
        return (
            <View style={styles.sectionView}>
                <Text style={styles.sectionText}>
                    {sectionObj.key}
                </Text>
            </View>
        );
    }

     // 渲染城市列表
    _renderCityList() {
        var sessions = this.state.dataSource
        let lists = []
        for (let i = 0; i < sessions.length; i++) {
            let section = sessions[i];
            this._renderSectionItem(section,lists);
        }
        return lists
    }

    render() {
        return (
         <View style={styles.container}>
              <View style={styles.listContainner}>
                       <ScrollView ref="ScrollView" style={{backgroundColor:'#FFFFFF'}}>
                            {this._renderCityList()}
                       </ScrollView>
                     <View style={styles.letters}>
                          {this.state.letters.map((letter, index) => this._renderRightLetters(letter, index))}
                     </View>
               </View>
              <Toast ref="toast" position='top' positionValue={200} fadeInDuration={750} fadeOutDuration={1000}
                     opacity={0.8}/>
          </View>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: '#F4F4F4',
    },
    listContainner: {
        height: Dimensions.get('window').height,
        marginBottom: 10
    },
    contentContainer: {
        flexDirection: 'row',
        width: width,
        backgroundColor: 'white',
        justifyContent: 'flex-start',
        flexWrap: 'wrap'
    },
    letters: {
        position: 'absolute',
        height: height,
        top: 0,
        bottom: 0,
        right: 10,
        backgroundColor: 'transparent',
        alignItems: 'center',
        justifyContent: 'center'
    },
    letter: {
        height: height * 4 / 100,
        width: width * 4 / 50,
        justifyContent: 'center',
        alignItems: 'center',
    },
    gridLetter: {
        height: height * 4 / 100,
        width: width * 4 / 50,
        justifyContent: 'center',
        alignItems: 'center',
        marginBottom:10
    },
    letterText: {
        textAlign: 'center',
        fontSize: height * 1.1 / 50,
        color: '#06C1AE'
    },
    sectionView: {
        paddingTop: 5,
        paddingBottom: 5,
        height: 30,
        paddingLeft: 10,
        width: width,
        backgroundColor: '#F4F4F4'
    },
    sectionText: {
        color: '#06C1AE',
        fontWeight: 'bold'
    },
    rowView: {
        height: ROWHEIGHT,
        paddingLeft: 10,
        paddingRight: 10,
        borderBottomColor: '#F4F4F4',
        borderBottomWidth: 0.5
    },
    rowdata: {
        paddingTop: 10,
        paddingBottom: 2
    },

    rowdatatext: {
        color: 'gray',
        width: width
    },

    rowViewBox: {
        height: ROWHEIGHT_BOX,
        width: (width - 30) / 3,
        flexDirection: 'row',
        backgroundColor: '#ffffff'
    },
    rowdataBox: {
        borderWidth: 1,
        borderColor: '#DBDBDB',
        marginTop: 5,
        marginBottom: 5,
        paddingBottom: 2,
        marginLeft: 10,
        marginRight: 10,
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    rowDataTextBox: {
        marginTop: 5,
        flex: 1,
        height: 20
    },
    gridContainer:{
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'flex-start',
    }
});
