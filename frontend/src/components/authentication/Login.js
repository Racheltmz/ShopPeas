import { useState } from 'react';
import { FirebaseAuth, FirebaseDb } from '../../lib/firebase';
import { StyleSheet, View, TextInput, Button, ActivityIndicator } from 'react-native';
import { signInWithEmailAndPassword } from 'firebase/auth';
import { useUserStore } from '../../lib/userStore';

const Login = () => {
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
            alert("Login successful");

        } catch(err) {
            console.log(err);
            alert("Login failed: " + err.message);
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <View style={styles.container}>
            <TextInput style={styles.input} placeholder="Email" autoCapitalize="none" onChangeText={setEmail} />
            <TextInput secureTextEntry={true} style={styles.input} value={password} placeholder="Password" autoCapitalize="none" onChangeText={setPassword} />
            {isLoading ? (
                <ActivityIndicator size="large" color="#0000ff" />
            ) : (
                <Button title="Login" onPress={handleLogin} />
            )}
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
    input: {
      width: '80%',
      padding: 10,
      marginBottom: 10,
      borderWidth: 1,
      borderColor: '#ccc',
    },
});

export default Login;