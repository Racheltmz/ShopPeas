import { useState } from "react";
import { View , StyleSheet, Button } from "react-native";
import Login from "../components/authentication/Login";
import Register from "../components/authentication/Register";

const AuthPage = () => {
    const [isLogin, setIsLogin] = useState(true);

    return (
        <View>  
            {isLogin ? <Login></Login> : <Register></Register>}
            <Button
            title={isLogin ? "Click here to Sign Up" : "Click here to login"}
            onPress={() => setIsLogin((prev) => !prev)}
            />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#fff',
      alignItems: 'center',
      justifyContent: 'center',
    },
  });

export default AuthPage;