import { useState} from 'react';
import { FirebaseAuth, FirebaseDb } from '../../lib/firebase';
import { StyleSheet, View, TextInput, Button } from 'react-native';
import { signInWithEmailAndPassword } from 'firebase/auth';

const Login = () => {

    const auth = FirebaseAuth;
    const db = FirebaseDb;

    const [isLoading, setIsLoading] = useState(false);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = async(e) => {
        setIsLoading(true);
        try {
            const res = await signInWithEmailAndPassword(auth, email, password);
            console.log(res.user.uid);
            alert("SUCCESS")
        } catch(err) {
            console.log(err);
            alert("Login failed: " + err.message);
        }
        finally {
            setIsLoading(false);
        }
    }


    return (
        <View style={styles.container}>
            <TextInput style={styles.input} placeholder="Email" autoCapitalize="none" onChangeText={(text) => setEmail(text)}></TextInput>
            <TextInput secureTextEntry={true} style={styles.input} value={password} placeholder="Password" autoCapitalize="none" onChangeText={(text) => setPassword(text)}></TextInput>
            <Button title="Login" onPress={() => handleLogin()}></Button>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
      /*backgroundColor: '#fff',*/ //Container Colour for login portion, no need for this as we have image
      alignItems: 'center',
      justifyContent: 'center',
    },
  });

export default Login;
  