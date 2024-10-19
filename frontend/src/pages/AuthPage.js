import { useState } from "react";
import { View, StyleSheet, SafeAreaView, Image, TouchableOpacity, Text, ImageBackground } from "react-native";
import Login from "../components/authentication/Login";
import Register from "../components/authentication/Register";
const AuthPage = () => {
    const [currentState, setCurrentState] = useState('default');

    const renderDefault = () => (
        <View style={styles.default}>
            <Image
                source={require('../../assets/imgs/shoppeas.png')}
                style={styles.logo}
            />
            <Text style={styles.title}>Your one-stop wholesale grocer.</Text>
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
      <View style={styles.default}>
        <Login 
            onBackPress={() => setCurrentState('default')}
            onRegisterPress={() => setCurrentState('register')}
        />
      </View>   
    );

    const renderRegister = () => (
        <View style={styles.default}>
          <Register 
            onBackPress={() => setCurrentState('default')}
            onLoginPress={() => setCurrentState('login')}
            onRegComplete={() => setCurrentState('login')}
          />
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
    default: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: '10%',
    },
    logo: {
        width: '40%',  
        height: '20%',
        marginBottom: '3%',
    },
    title: {
      fontFamily: 'Roboto, sans-serif', 
      fontSize: '30%',
      color: '#0C5E52',
      fontWeight: 'bold',
      textAlign: 'left',
      marginBottom: '10%',
    },
    buttonContainer: {
        justifyContent: 'center',
        marginBottom: '8%',
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
});

export default AuthPage;