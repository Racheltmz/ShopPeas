import React from "react";
import { View } from "react-native";
import RegisterCustomer from "./RegisterCustomer";
import RegisterWholesaler from "./RegisterWholesaler";

const Register = ({ route }) => {
  const { isConsumer } = route.params; // Retrieve the isConsumer flag

  return (
    <View>
      {isConsumer ? <RegisterCustomer /> : <RegisterWholesaler />}
    </View>
  );
};

export default Register;
