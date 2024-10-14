import React, { useState } from 'react';
import { Text, StyleSheet, View, TextInput, TouchableOpacity, Image } from 'react-native';
import { createUserWithEmailAndPassword } from 'firebase/auth';
import { FirebaseAuth, FirebaseDb } from '../../lib/firebase';
import { setDoc, doc } from 'firebase/firestore';
import { Ionicons } from '@expo/vector-icons';


const RegisterWholesaler = ({onBackPress, onRegComplete}) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [username, setUsername] = useState('');
    const [number, setNumber] = useState('');
    const [address, setAddress] = useState('')
    const [uen, setUen] = useState('')
    // const [streetName, setStreetName] = useState('');
    // const [unitNo, setUnitNo] = useState('');
    // const [buildingName, setBuildingName] = useState('');
    // const [city, setCity] = useState('');
    // const [postalCode, setPostalCode] = useState('')
    // const [isLoading, setIsLoading] = useState(false);

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
            Alert('Success!')

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

            <View style={styles.headerContainer}>
                <Text style={styles.regText}>Register as a Business Owner.</Text>
                <Image
                    source={require('../../../assets/imgs/businessIcon.png')}
                    style={styles.image}
                />
            </View>

            <Text style={styles.formLabel}>Company Details</Text>
            <TextInput style={styles.formInput} placeholder="Company Name" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setUsername(text)}></TextInput>
            <TextInput style={styles.formInput} placeholder="Email" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setEmail(text)}></TextInput>
            <TextInput style={styles.formInput} placeholder="UEN" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setUen(text)}></TextInput>
            <TextInput style={styles.formInput} placeholder="Phone Number" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setNumber(text)}></TextInput>
            <TextInput style={styles.formInput} placeholder="Address" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setAddress(text)}></TextInput>
            <TextInput secureTextEntry={true} style={styles.formInput} placeholderTextColor="#0C5E52" value={password} placeholder="Password" autoCapitalize="none" onChangeText={(text) => setPassword(text)}></TextInput>

            {/* <Text style={styles.formLabel}>Registered Address</Text>
            <TextInput style={styles.formInput} placeholder="Street Name" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setUsername(text)}></TextInput>
            <TextInput style={styles.formInput} placeholder="Unit No." placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setEmail(text)}></TextInput>
            <TextInput style={styles.formInput} placeholder="Building Name" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setUen(text)}></TextInput>
            <TextInput style={styles.formInput} placeholder="City, Country" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setNumber(text)}></TextInput>
            <TextInput style={styles.formInput} placeholder="Postal Code" placeholderTextColor="#0C5E52" autoCapitalize="none" onChangeText={(text) => setAddress(text)}></TextInput>
            <TextInput secureTextEntry={true} style={styles.formInput} placeholderTextColor="#0C5E52" value={password} placeholder="Password" autoCapitalize="none" onChangeText={(text) => setPassword(text)}></TextInput> */}

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
        left: '-7%',
        width: '12%',
        height: '5.2%',
        borderRadius: '100%',
        backgroundColor: '#0C5E52',
        justifyContent: 'center',
        alignItems: 'center',
    },
    headerContainer: {
        flexDirection: 'row',
        width: '100%',
        backgroundColor: '#0C5E5230',
        padding: '6%', 
        borderRadius: '10%',
        marginBottom: '5%',
    },
    regText: {
        color: '#0C5E52',
        fontWeight: 'bold',
        fontSize: '26%',
        alignSelf: 'left',
    },
    image: {
        width: '28%',
        height: "100%",
        marginBottom: '5%',
    },
    formLabel: {
        color: '#0C5E52',
        fontWeight: 'bold',
        fontSize: 18,
        alignSelf: 'left',
        marginBottom: 2,
    },
    formInput: {
        borderWidth: 1,
        borderColor: '#0C5E52',
        backgroundColor: '#fff',
        padding: 10,
        marginVertical: 5,
        borderRadius: 5,
        width: '100%',
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
