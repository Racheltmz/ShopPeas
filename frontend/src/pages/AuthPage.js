import { useState } from "react";
import { View, StyleSheet, SafeAreaView, Image, TouchableOpacity, Text, ImageBackground } from "react-native";
import Login from "../components/authentication/Login";
import Register from "../components/authentication/Register";
import { Ionicons } from '@expo/vector-icons';

const AuthPage = () => {
    const [currentState, setCurrentState] = useState('default');

    const renderDefault = () => (
        <View style={styles.content}>
            <Image
                source={require('../../assets/imgs/loginImg.png')}
                style={styles.logo}
            />
            
            <View style={styles.buttonContainer}>
                <TouchableOpacity 
                    style={styles.loginButton}
                    onPress={() => setCurrentState('login')}
                >
                    <Text style={styles.loginButtonText}>LOG IN</Text>
                </TouchableOpacity>
                
                <TouchableOpacity 
                    style={styles.regButton}
                    onPress={() => setCurrentState('register')}
                >
                    <Text style={styles.regButtonText}>SIGN UP</Text>
                </TouchableOpacity>
            </View>
        </View>
    );

    const renderLogin = () => (
        <View style={styles.content}>
            <TouchableOpacity 
                style={styles.backButton}
                onPress={() => setCurrentState('default')}
            >
                <Ionicons 
                  color={'#EBF3D1'}
                  size={20}
                  name="arrow-back-outline"
                />
            </TouchableOpacity>
            <Login />
        </View>
    );

    const renderRegister = () => (
        <View style={styles.content}>
            <TouchableOpacity 
                style={styles.backButton}
                onPress={() => setCurrentState('default')}
            >
                <Ionicons
                  color={'#EBF3D1'}
                  size={20}
                  name="arrow-back-outline"
                />
            </TouchableOpacity>
            <Register />
        </View>
    );

    return (
        <ImageBackground 
            source={require('../../assets/imgs/loginBG.png')}
            style={styles.backgroundImage}
        >
            <SafeAreaView style={styles.container}> 
                {currentState === 'default' && renderDefault()}
                {currentState === 'login' && renderLogin()}
                {currentState === 'register' && renderRegister()}
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
        width: '100%',
    },
    content: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 20,
    },
    logo: {
        width: '90%',  
        height: '30%', 
        resizeMode: 'contain',
        marginBottom: 30,
    },
    buttonContainer: {
        justifyContent: 'center',
        marginBottom: 20,
    },
    loginButton: {
        backgroundColor: '#0C5E52',
        width:200,
        padding: '3%',
        borderRadius: '10%',
        marginBottom: '2%',
    },
    regButton: {
      backgroundColor: '#EBF3D1',
      width:200,
      padding: '3%',
      borderRadius: '10%',
      marginBottom: '2%',
    },
    loginButtonText: {
        color: '#EBF3D1',
        fontWeight: 'bold',
        textAlign: 'center',
    },
    regButtonText: {
      color: '#0C5E52',
      fontWeight: 'bold',
      textAlign: 'center',
    },
    backButton: {
      position: 'absolute',
      top: 40,
      left: 20,
      width: 40,
      height: 40,
      borderRadius: 20,
      backgroundColor: '#006400',
      justifyContent: 'center',
      alignItems: 'center',
    },
    backButtonText: {
      color: 'white',
      fontSize: 24,
    },
});

export default AuthPage;