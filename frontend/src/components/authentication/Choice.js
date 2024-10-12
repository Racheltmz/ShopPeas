import React, { useState } from "react";
import { View, Text, StyleSheet, TouchableOpacity, Image, ImageBackground, SafeAreaView } from "react-native";
import Login from "../components/authentication/Login";
import Register from "../components/authentication/Register";

const AuthPage = () => {
    const [isLogin, setIsLogin] = useState(true);

    return (
        <ImageBackground 
            source={require('../../assets/imgs/loginBG.png')} // Ensure this path is correct
            style={styles.backgroundImage}
        >
            <SafeAreaView style={styles.container}>
                <View style={styles.content}>
                    <Image
                        source={require('../../assets/imgs/logo.png')} // Ensure this path is correct
                        style={styles.logo}
                    />
                    <Text style={styles.title}>Your one-stop</Text>
                    <Text style={styles.title}>wholesale grocer.</Text>
                    
                    <TouchableOpacity 
                        style={styles.loginButton}
                        onPress={() => setIsLogin(true)}
                    >
                        <Text style={styles.loginButtonText}>LOG IN</Text>
                    </TouchableOpacity>
                    
                    <TouchableOpacity 
                        style={styles.signupButton}
                        onPress={() => setIsLogin(false)}
                    >
                        <Text style={styles.signupButtonText}>SIGN UP</Text>
                    </TouchableOpacity>
                </View>

                {/* This will render either the Login or Register component based on state */}
                {isLogin ? <Login /> : <Register />}
            </SafeAreaView>
        </ImageBackground>
    );
}

const styles = StyleSheet.create({
    backgroundImage: {
        flex: 1,
        width: '100%',
        height: '100%',
    },
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    content: {
        backgroundColor: 'rgba(255, 255, 255, 0.9)', // Semi-transparent white background
        padding: 20,
        borderRadius: 10,
        alignItems: 'center',
        width: '90%',
    },
    logo: {
        width: 100,
        height: 100,
        marginBottom: 20,
    },
    title: {
        fontSize: 24,
        fontWeight: 'bold',
        color: '#006A4E', // Adjust this color to match your exact green
        textAlign: 'center',
    },
    loginButton: {
        backgroundColor: '#006A4E', // Adjust this color to match your exact green
        paddingVertical: 15,
        paddingHorizontal: 40,
        borderRadius: 5,
        marginTop: 30,
        width: '80%',
        alignItems: 'center',
    },
    loginButtonText: {
        color: '#ffffff',
        fontSize: 16,
        fontWeight: 'bold',
    },
    signupButton: {
        backgroundColor: '#E8F3E8', // Adjust this color to match your exact light green
        paddingVertical: 15,
        paddingHorizontal: 40,
        borderRadius: 5,
        marginTop: 10,
        width: '80%',
        alignItems: 'center',
    },
    signupButtonText: {
        color: '#006A4E', // Adjust this color to match your exact green
        fontSize: 16,
        fontWeight: 'bold',
    },
});

export default AuthPage;