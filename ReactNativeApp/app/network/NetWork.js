import React, {Component} from 'react';
import { FlatList, ActivityIndicator, Text, View  } from 'react-native';

import BaseRequest from './BaseRequest'

export default class NetWork extends Component<Props> {

	constructor(props){
		super(props);
		this.state ={ isLoading: true}
	}

	componentDidMount():void{
	   BaseRequest.getData('https://facebook.github.io/react-native/movies.json')
        .then((data)=>{
             this.setState({
                      isLoading: false,
                      dataSource: data.movies,
                    }, function(){

                    });

        })
        .catch(error=>{
          console.log(error);
        })
	}





	render() {
	  	    if(this.state.isLoading){
              return(
                <View style={{flex: 1, padding: 20}}>
                  <ActivityIndicator/>
                </View>
              )
            }

            return(
                  <View style={{flex: 1, paddingTop:20}}>
                    <FlatList
                      data={this.state.dataSource}
                      renderItem={({item}) => <Text>{item.title}, {item.releaseYear}</Text>}
                      keyExtractor={(item, index) => item.id}
                    />
                  </View>
            );

	}
}


