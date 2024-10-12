import React, { useState } from 'react';
import { Text, StyleSheet, View, TextInput, TouchableOpacity, Image } from 'react-native';
import { createUserWithEmailAndPassword , signInWithEmailAndPassword } from 'firebase/auth';
import { FirebaseAuth, FirebaseDb } from '../../lib/firebase';
import { setDoc, doc } from 'firebase/firestore';
import { Ionicons } from '@expo/vector-icons';


const RegisterWholesaler = ({onBackPress}) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [username, setUsername] = useState('');
    const [number, setNumber] = useState('');
    const [address, setAddress] = useState('')
    const [uen, setUen] = useState('')
    const [isLoading, setIsLoading] = useState(false);

    const auth = FirebaseAuth;
    const db = FirebaseDb;

    const handleRegister = async () => {
        setIsLoading(true);
        try {
            const res = await createUserWithEmailAndPassword(auth, email, password);
            await setDoc(doc(db, "users", res.user.uid), {
                username: username,
                email: email,
                number: number,
                uen: uen,
                address: address,
                created: new Date(),
                id: res.user.uid,
                role: "wholesaler",
            })
            alert("SUCCESS")

        } catch (err) {
            console.log(err);
            alert("registration failed: " + err.message);
        }
        finally {
            setIsLoading(false);
        }
    };

    return (
        <View style={styles.container}>
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
                source={require('../../../assets/imgs/BizReg.png')}
                style={styles.image}
            />
            <TextInput style={styles.input} placeholder="Username" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setUsername(text)}></TextInput>
            <TextInput style={styles.input} placeholder="Email" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setEmail(text)}></TextInput>
            <TextInput style={styles.input} placeholder="UEN" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setUen(text)}></TextInput>
            <TextInput style={styles.input} placeholder="Phone Number" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setNumber(text)}></TextInput>
            <TextInput style={styles.input} placeholder="Address" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setAddress(text)}></TextInput>
            <TextInput secureTextEntry={true} style={styles.input} placeholderTextColor="#0C5E52" value={password} placeholder="Password" autoCapitalize="none" onChangeText={(text) => setPassword(text)}></TextInput>

            <TouchableOpacity 
                style={styles.regButton}
                onPress={handleRegister}
                >
                <Text style={styles.regButtonText}>Register</Text>
                <Ionicons name="arrow-forward" size={20} style={styles.arrowIcon} />
            </TouchableOpacity>
        </View>
    );
};

export default RegisterWholesaler;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    backButton: {
        position: 'absolute',
        top: '0%',
        left: '-8%',
        width: '13%',
        height: '5.2%',
        borderRadius: '100%',
        backgroundColor: '#0C5E52',
        justifyContent: 'center',
        alignItems: 'center',
      },
    image: {
        width: 300,
        height: "15%",
        resizeMode: 'contain',
    },
    input: {
        borderWidth: 1,
        borderColor: '#0C5E52',
        backgroundColor: '#fff',
        padding: 10,
        marginVertical: 5,
        borderRadius: 5,
        width: 200,
    },
    regButton: {
        flexDirection: 'row',
        backgroundColor: '#0C5E52',
        width: '70%',
        height: '6%',
        borderRadius: 25,
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: 20,
      },
      regButtonText: {
        color: '#EBF3D1',
        fontWeight: 'bold',
        fontSize: 17,
        alignSelf: 'center',
      },
      arrowIcon: {
        left: '40%',
        color: '#EBF3D1',
      },
});
