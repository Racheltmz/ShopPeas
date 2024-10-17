import React from 'react';
import { View, Text, TextInput, StyleSheet } from 'react-native';

const WholesalerDetails = ({ formData, handleInputChange }) => {
  const handlePhoneChange = (text) => {
    const digitsOnly = text.replace(/^\+65|\D/g, '');
    const formattedNumber = digitsOnly.slice(0, 8);
    const fullNumber = '+65 ' + formattedNumber;
    handleInputChange('phone_number', fullNumber);
  };

  return (
    <View style={styles.container}>
      <Text style={styles.stepTitle}>Company Name: </Text>
      <TextInput
        style={styles.input}
        value={formData.name}
        onChangeText={(text) => handleInputChange('name', text)}
      />
      <Text style={styles.stepTitle}>UEN (Unique Entity Number): </Text>
      <TextInput
        style={styles.input}
        value={formData.uen}
        onChangeText={(text) => handleInputChange('uen', text)}
      />
      <Text style={styles.stepTitle}>Email: </Text>
      <TextInput
        style={styles.input}
        value={formData.email}
        onChangeText={(text) => handleInputChange('email', text)}
      />
      <Text style={styles.stepTitle}>Phone Number: </Text>
      <TextInput
        style={styles.input}
        value={formData.phone_number}
        onChangeText={handlePhoneChange}
      />
    </View>
  );
};

export default WholesalerDetails;

const styles = StyleSheet.create({
  stepTitle: {
      fontSize: '15%',
      alignSelf: 'left',
      color: '#0C5E52',
      fontWeight: 'bold',
      marginTop: '3%'
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