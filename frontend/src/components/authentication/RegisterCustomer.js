import React, { useState } from 'react';
import { Text, StyleSheet, View, TextInput, Button, Image, TouchableOpacity } from 'react-native';
import { createUserWithEmailAndPassword , getAuth } from 'firebase/auth';
import { FirebaseAuth, FirebaseDb } from '../../lib/firebase';
import { setDoc, doc } from 'firebase/firestore';
import { Ionicons } from '@expo/vector-icons';
import authService from '../../service/AuthService';

const RegisterCustomer = ({onBackPress}) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const auth = FirebaseAuth;
    const db = FirebaseDb;

    const handleRegister = async () => {
        setIsLoading(true)

        try {
            const res = await createUserWithEmailAndPassword(auth, email, password);

            // // TODO: Update these fields with the respective records
            const requestBody = {
              "first_name": firstName,
              "last_name": lastName,
              "email": email,
              "phone_number": `+65 ${phoneNumber}`,
              "name": `${firstName} ${lastName}`,
              "street_name": "10 Ang Mo Kio Road",
              "unit_no": "#10-19",
              "building_name": null,
              "city": "Singapore",
              "postal_code": "387458"
            }
            
            // API call to add user details into database collections
            authService.register(res.user.uid, "consumer", requestBody)
                .catch((err) => {
                    console.log(err); // TODO: replace with show error alert
                })

            // TODO: Navigate to login page
        } catch (err) {
            console.log(err);
            alert("registration failed: " + err.message);
        } finally {
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
        <View style={styles.headerContainer}>
          <Text style={styles.regText}>Register as a Consumer.</Text>
          <Image
            source={require('../../../assets/imgs/consumerIcon.png')}
            style={styles.image}
          />
        </View>
        <TextInput 
          style={styles.input} 
          value={firstName}
          placeholder="First Name" 
          placeholderTextColor="#0C5E52"
          autoCapitalize="none" 
          onChangeText={(text) => setFirstName(text)}
        />
        <TextInput 
          style={styles.input}
          value={lastName}
          placeholder="Last Name" 
          placeholderTextColor="#0C5E52"
          autoCapitalize="none" 
          onChangeText={(text) => setLastName(text)}
        />
        <TextInput 
          style={styles.input}
          value={email}
          placeholder="Email" 
          placeholderTextColor="#0C5E52"
          autoCapitalize="none" 
          onChangeText={(text) => setEmail(text)}
        />
        <TextInput 
          style={styles.input} 
          value={phoneNumber}
          placeholder="Phone Number" 
          placeholderTextColor="#0C5E52"
          autoCapitalize="none" 
          onChangeText={(text) => setPhoneNumber(text)}
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
    headerContainer: {
      flexDirection: 'row',
      width: '95%',
      backgroundColor: '#EBF3D1',
      padding: '6%', 
      borderRadius: '10%',
      marginBottom: '5%',
    },
    regText: {
      color: '#0C5E52',
      fontWeight: 'bold',
      fontSize: '25%',
      alignSelf: 'left',
    },
    image: {
      width: '40%',
      height: "100%",
      marginBottom: '5%',
    },
    input: {
      borderWidth: 1,
      borderColor: '#0C5E52',
      padding: 10,
      marginVertical: 5,
      borderRadius: 5,
      width: '80%',
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