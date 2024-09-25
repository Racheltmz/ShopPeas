import React, { useState } from 'react';
import { Text, StyleSheet, View, TextInput, Button } from 'react-native';
import { createUserWithEmailAndPassword , signInWithEmailAndPassword } from 'firebase/auth';
import { FirebaseAuth, FirebaseDb } from '../../lib/firebase';
import { setDoc, doc } from 'firebase/firestore';


const RegisterCustomer = () => {
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
            <Text>Customer Registration</Text>
            <TextInput style={styles.input} placeholder="Username" autoCapitalize="none" onChangeText={(text) => setUsername(text)}></TextInput>
            <TextInput style={styles.input} placeholder="Email" autoCapitalize="none" onChangeText={(text) => setEmail(text)}></TextInput>
            <TextInput style={styles.input} placeholder="Phone Number" autoCapitalize="none" onChangeText={(text) => setNumber(text)}></TextInput>
            <TextInput secureTextEntry={true} style={styles.input} value={password} placeholder="Password" autoCapitalize="none" onChangeText={(text) => setPassword(text)}></TextInput>

            <Button title="Register" onPress={() => handleRegister()}></Button>
        </View>
    );
};

export default RegisterCustomer;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
    input: {
        borderWidth: 1,
        borderColor: '#ccc',
        padding: 10,
        marginVertical: 5,
        borderRadius: 5,
        width: 200,
    },
});
