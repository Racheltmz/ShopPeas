import React, { useState } from 'react';
import { Text, StyleSheet, View, TextInput, Button, Image, TouchableOpacity, Alert } from 'react-native';
import { createUserWithEmailAndPassword , signInWithEmailAndPassword } from 'firebase/auth';
import { FirebaseAuth, FirebaseDb } from '../../lib/firebase';
import { setDoc, doc } from 'firebase/firestore';
import { Ionicons } from '@expo/vector-icons';

const RegisterCustomer = ({onBackPress}) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [username, setUsername] = useState('');
    const [number, setNumber] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const auth = FirebaseAuth;
    const db = FirebaseDb;

    const handleRegister = async () => {
        setIsLoading(true)

        try {
            const res = await createUserWithEmailAndPassword(auth, email, password);
            await setDoc(doc(db, "users", res.user.uid), {
                username: username,
                email: email,
                number: number,
                id: res.user.uid,
                created: new Date(),
                type: "customer",
            })
            await setDoc(doc(db, "cart", res.user.uid), {
                username: username,
                email: email,
                number: number,
                id: res.user.uid,
                created: new Date(),
                type: "customer",
            })
            Alert('SUCCESS!')
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
          source={require('../../../assets/imgs/CustReg.png')}
          style={styles.image}
        />
        <TextInput 
          style={styles.input} 
          placeholder="Username" 
          placeholderTextColor="#0C5E52"
          autoCapitalize="none" 
          onChangeText={(text) => setUsername(text)}
        />
        <TextInput 
          style={styles.input} 
          placeholder="Email" 
          placeholderTextColor="#0C5E52"
          autoCapitalize="none" 
          onChangeText={(text) => setEmail(text)}
        />
        <TextInput 
          style={styles.input} 
          placeholder="Phone Number" 
          placeholderTextColor="#0C5E52"
          autoCapitalize="none" 
          onChangeText={(text) => setNumber(text)}
        />
        <TextInput 
          secureTextEntry={true} 
          style={styles.input} 
          value={password} 
          placeholder="Password" 
          placeholderTextColor="#0C5E52"
          autoCapitalize="none" 
          onChangeText={(text) => setPassword(text)}
        />
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

export default RegisterCustomer;

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
    input: {
      borderWidth: 1,
      borderColor: '#0C5E52',
      padding: 10,
      marginVertical: 5,
      borderRadius: 5,
      width: '80%',
    },
    image: {
      width: 300,
      height: "15%",
      resizeMode: 'contain',
      marginBottom: '5%',
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
      left: '85%',
      color: '#EBF3D1',
    },
});