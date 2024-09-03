import { StatusBar } from "expo-status-bar";
import { useState } from "react";
import { StyleSheet, Text, View, Button } from "react-native";
import RegisterCustomer from "./src/components/register/registerCustomer";
import RegisterWholesaler from "./src/components/register/registerWholesaler";

// const Stack = createNativeStackNavigator();

export default function App() {
  const [tempToggle, setTempToggle] = useState(false);

  return (
    <View style={styles.container}>
      {/* <Text>Open up App.js to start working on your app!</Text>
      <StatusBar style="auto" /> */}
      {tempToggle ? <RegisterCustomer /> : <RegisterWholesaler />}
      <Button
        title={tempToggle ? "Register as Wholesaler" : "Register as Customer"}
        onPress={() => setTempToggle((prev) => !prev)}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
});
