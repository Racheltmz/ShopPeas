import React from 'react';
import { View, Text, TextInput, StyleSheet } from 'react-native';

const WholesalerDetails = ({ formData, handleInputChange }) => {
  return (
    <View style={styles.container}>
        <View style={styles.namesContainer}>
            <View style={styles.nameContainer}>
                <Text style={styles.stepTitle}>First Name: </Text>
                <TextInput
                    style={styles.nameInput}
                    value={formData.first_name}
                    onChangeText={(text) => handleInputChange('first_name', text)}
                />
            </View>
            <View style={styles.nameContainer}>
                <Text style={styles.stepTitle}>Last Name: </Text>
                <TextInput
                    style={styles.nameInput}
                    value={formData.last_name}
                    onChangeText={(text) => handleInputChange('last_name', text)}
                />
            </View>
        </View>
        
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
            onChangeText={(text) => handleInputChange('phone_number', text)}
      />
    </View>
  );
};

export default WholesalerDetails;

const styles = StyleSheet.create({
    namesContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
    },
    stepTitle: {
        fontSize: '15%',
        alignSelf: 'left',
        color: '#0C5E52',
        fontWeight: 'bold',
        marginTop: '3%'
    },
    nameInput:{
        borderWidth: 1,
        borderColor: '#0C5E52',
        padding: 10,
        marginVertical: 5,
        borderRadius: 5,
        width:130,
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