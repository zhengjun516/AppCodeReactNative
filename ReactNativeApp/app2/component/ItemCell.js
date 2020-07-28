/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import { View, Text, StyleSheet, Image, TouchableOpacity } from 'react-native';
import { Text16, Text14, Paragraph } from '../component/Text'

export default class ItemCell extends Component {

    render() {
        let icon = null;
        if (this.props.image) {
            icon = <Image style={styles.icon} source={this.props.image} />
        }

        return (
            <View style={styles.container}>

                    <View style={[styles.content, this.props.style]}>
                        {icon}
                        <Text14>{this.props.title}</Text14>
                        <View style={{ flex: 1, backgroundColor: 'blue' }} />
                        <Paragraph style={{ color: '#999999' }}>{this.props.subtitle}</Paragraph>
                        <Image style={styles.arrow} source={require('../images/icon_arrow.png')} />
                    </View>

            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        backgroundColor: 'white',
    },
    content: {
        height: 44,
        flexDirection: 'row',
        alignItems: 'center',
        paddingLeft: 15,
        paddingRight: 10,
    },
    icon: {
        width: 25,
        height: 25,
        marginRight: 10,
    },
    subtitleContainer: {
        flexDirection: 'row',
        justifyContent: 'flex-end',
        alignItems: 'center',
    },
    arrow: {
        width: 14,
        height: 14,
        marginLeft: 5,
    }
});

