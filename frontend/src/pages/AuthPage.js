import { useState } from "react";
import { View , StyleSheet, Button, SafeAreaView} from "react-native"; //SafeAreaView so that all content is within the correct areas
import Login from "../components/authentication/Login";
import Register from "../components/authentication/Register";

const AuthPage = () => {
    const [isLogin, setIsLogin] = useState(true);

    return (
      <SafeAreaView style = {styles.container}> 
        <View>  
            {isLogin ? <Login></Login> : <Register></Register>}
            <Button
            title={isLogin ? "Click here to Sign Up" : "Click here to login"}
            onPress={() => setIsLogin((prev) => !prev)}
            />
        </View>
      </SafeAreaView>
    )
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
      width: '100%',
      alignItems: 'center',
      justifyContent: 'center',
    },
  });

export default AuthPage;