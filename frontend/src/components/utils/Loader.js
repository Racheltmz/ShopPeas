import React from 'react';
import { StyleSheet, Text } from 'react-native';
import AnimatedLoader from "react-native-animated-loader";

const Loader = ({loading}) => {
    return (
        <AnimatedLoader
            visible={loading}
            overlayColor="rgba(255,255,255,0.75)"
            source={require("../../../assets/loader.json")}
            animationStyle={styles.loader}
            speed={1}
        >
            <Text style={styles.loaderText}>Loading...</Text>
        </AnimatedLoader>
    )
}


const styles = StyleSheet.create({
    loader: {
        width: 200,
        height: 200,
    },
    loaderText: {
        fontSize: 24,
        fontWeight: 'bold',
        color: '#0C5E52',
    },
});

export default Loader;