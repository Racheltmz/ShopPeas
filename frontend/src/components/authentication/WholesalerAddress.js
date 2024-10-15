import React from 'react';
import { View, Text, TextInput, StyleSheet } from 'react-native';

const WholesalerAddress = ({ formData, handleInputChange }) => {
  return (
    <View>
      <Text style={styles.stepTitle}>Registered Address</Text>
      <TextInput
        style={styles.input}
        placeholder="Street Name"
        value={formData.street_name}
        onChangeText={(text) => handleInputChange('streetName', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Unit No."
        value={formData.unit_no}
        onChangeText={(text) => handleInputChange('unitNo', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Building Name"
        value={formData.building_name}
        onChangeText={(text) => handleInputChange('buildingName', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="City, Country"
        value={formData.city}
        onChangeText={(text) => handleInputChange('city', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Postal Code"
        value={formData.postal_code}
        onChangeText={(text) => handleInputChange('postalCode', text)}
      />
    </View>
  );
};

export default WholesalerAddress;

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