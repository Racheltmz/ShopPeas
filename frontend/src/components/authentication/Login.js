import React, { useState } from 'react';
import { StyleSheet, View, TextInput, TouchableOpacity, Text, Image, SafeAreaView } from 'react-native';
import { FirebaseAuth } from '../../lib/firebase';
import { signInWithEmailAndPassword } from 'firebase/auth';
import { useUserStore } from '../../lib/userStore';
import { Ionicons } from '@expo/vector-icons';

const Login = ({ onBackPress, onRegisterPress }) => {
    const auth = FirebaseAuth;
    const { updateUserType, fetchUserInfo } = useUserStore();
    const [isLoading, setIsLoading] = useState(false);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = async () => {
        setIsLoading(true);
        try {
            const userCredential = await signInWithEmailAndPassword(auth, email, password);
            const user = userCredential.user;
            
            const idTokenResult = await user.getIdTokenResult();

            let userType = 'unknown';
            if (idTokenResult.claims.consumer) {
                userType = 'consumer';
            } else if (idTokenResult.claims.wholesaler) {                                           
                userType = 'wholesaler';
            }

            updateUserType(userType);
            await fetchUserInfo(user.uid);

            console.log("Login successful. User type:", userType);
        } catch(err) {
            console.log(err);
            alert("Login failed: " + err.message);
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <SafeAreaView style={styles.container}>
            <TouchableOpacity 
                style={styles.backButton}
                onPress={onBackPress}
            >
                <Ionicons 
                  color={'#EBF3D1'}
                  size={20}
                  name="arrow-back-outline"
                />
            </TouchableOpacity>
            
            <Image
                source={require('../../../assets/imgs/welcome.png')}
                style={styles.welcome}
            />
            
            <View style={styles.inputContainer}>
                <Ionicons name="mail-outline" size={24} color="#0C5E52" style={styles.inputIcon} />
                <TextInput 
                    style={styles.input} 
                    placeholder="Email" 
                    placeholderTextColor="#0C5E52"
                    autoCapitalize="none" 
                    onChangeText={setEmail} 
                />
            </View>
            
            <View style={styles.inputContainer}>
                <Ionicons name="lock-closed-outline" size={24} color="#0C5E52" style={styles.inputIcon} />
                <TextInput 
                    style={styles.input} 
                    placeholder="Password" 
                    placeholderTextColor="#0C5E52"
                    secureTextEntry={true} 
                    autoCapitalize="none" 
                    onChangeText={setPassword} 
                />
            </View>
            
            <TouchableOpacity onPress={{/* rmb to do forget password popup */}}>
                <Text style={styles.forgotPassword}>Forgot Password?</Text>
            </TouchableOpacity>
            
            <TouchableOpacity 
                style={styles.loginButton} 
                onPress={handleLogin}
                disabled={isLoading}
            >
                <Text style={styles.loginButtonText}>
                    {isLoading ? 'Logging in...' : 'LOG IN'}
                </Text>
                <Ionicons name="arrow-forward" size={24} color="#EBF3D1" style={styles.arrowIcon} />
            </TouchableOpacity>
            
            <View style={styles.signupContainer}>
                <Text style={styles.signupText}>No Account? </Text>
                <TouchableOpacity onPress={onRegisterPress}>
                    <Text style={styles.signupLink}>Create One!</Text>
                </TouchableOpacity>
            </View>
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        paddingHorizontal: 20,
    },
    backButton: {
        position: 'absolute',
        top: '3%',
        left: '-2%',
        width: '13%',
        height: '5.2%',
        borderRadius: '100%',
        backgroundColor: '#0C5E52',
        justifyContent: 'center',
        alignItems: 'center',
    },
    welcome: {
        width: 300,
        height: "15%",
        resizeMode: 'contain',
        marginBottom: '5%',
    },
    inputContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        width: '100%',
        height: '6%',
        borderWidth: '1%',
        borderColor: '#0C5E52',
        borderRadius: '20%',
        paddingHorizontal: '5%',
        marginBottom: '5%',
    },
    inputIcon: {
        marginRight: '5%',
    },
    input: {
        flex: 1,
        color: '#0C5E52',
    },
    forgotPassword: {
        color: '#0C5E52',
        marginBottom: '5%',
    },
    loginButton: {
        flexDirection: 'row',
        backgroundColor: '#0C5E52',
        width: '100%',
        height: '5%',
        borderRadius: '20%',
        justifyContent: 'center',
        alignItems: 'center',
        marginBottom: '7%',
    },
    loginButtonText: {
        color: '#EBF3D1',
        fontWeight: 'bold',
        marginRight: "30%",
    },
    arrowIcon: {
        position: 'absolute',
        right: '5%',
    },
    signupContainer: {
        flexDirection: 'row',
    },
    signupText: {
        color: '#0C5E52',
    },
    signupLink: {
        color: '#0C5E52',
        fontWeight: 'bold',
    },
});

export default Login;