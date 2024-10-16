import React from 'react';
import { View, Text, TextInput, StyleSheet } from 'react-native';

const WholesalerBank = ({ formData, handleInputChange }) => {
  return (
    <View>
      <Text style={styles.stepTitle}>Bank Account Details</Text>
      <TextInput
        style={styles.input}
        placeholder="Full Name in your Bank Account"
        value={formData.bank_account_name}
        onChangeText={(text) => handleInputChange('bankAccountName', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Account No."
        value={formData.account_no}
        onChangeText={(text) => handleInputChange('accountNo', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Select Bank"
        value={formData.bank}
        onChangeText={(text) => handleInputChange('bank', text)}
      />
    </View>
  );
};

export default WholesalerBank;

const styles = StyleSheet.create({
  stepTitle: {
      fontSize: '20%',
      alignSelf: 'left',
      color: '#0C5E52',
      fontWeight: 'bold',
      marginBottom: '3%',
  },
  input: {
      borderWidth: 1,
      borderColor: '#0C5E52',
      padding: 10,
      marginVertical: 5,
      borderRadius: 5,
      width:300,
  },
});