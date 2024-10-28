import React from 'react';
import { Image, StyleSheet, Text, View } from 'react-native';

const Empty = ({ subject }) => {
    return (
        <View style={styles.container}>
            <Image style={styles.emptyImage} source={require('../../../assets/imgs/empty.png')} />
            <Text style={styles.emptyText}>No {subject} Yet</Text>
        </View>
    )
}


const styles = StyleSheet.create({
    container: {
        flex: 1,
        width: "100%",
        height: "100%",
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#FFFFFF80',
    },
    emptyImage: {
        width: 150,
        height: 150,
    },
    emptyText: {
        marginTop: 30,
        fontSize: 20,
        fontWeight: 'bold',
        color: '#333333',
    },
});

export default Empty;