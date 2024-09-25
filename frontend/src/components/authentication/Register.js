import { useState } from "react";
import { View, Text, Button } from "react-native";
import RegisterCustomer from "./RegisterCustomer";
import RegisterWholesaler from "./RegisterWholesaler";

const Register = () => {
  const [selectedRegister, setSelectedRegister] = useState(false);
  const [isConsumer, setIsConsumer] = useState(false);

  const selectOptions = (
    <View>
      <Text>Kick-start your food journey with us. </Text>
      <Text>I am a... </Text>
      <Button title="Consumer" onPress={() => {
        setSelectedRegister(true);
        setIsConsumer(true);
      }}></Button>
      <Button title="Business Owner" onPress={() => {
        setSelectedRegister(true);
      }}></Button>
    </View>
  );


  return (
    <View>
      {selectedRegister ? (
        isConsumer ? (
          <RegisterCustomer />
        ) : (
          <RegisterWholesaler />
        )
      ) : (
        selectOptions
      )}
    </View>
  );
};

export default Register;
