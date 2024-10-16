import React from 'react';
import { View, Text, TextInput, StyleSheet } from 'react-native';

const WholesalerPassword = ({ formData, handleInputChange }) => {
  return (
    <View>
        <View style={styles.textContainer}>
            <Text style={styles.stepTitle}>Choose Password</Text>
            <Text style={styles.passwordCriteria}>Password must meet the following criteria:</Text>
            <Text style={styles.passwordCriteria}>• Minimum 12 characters long</Text>
            <Text style={styles.passwordCriteria}>• Contain at least one number</Text>
            <Text style={styles.passwordCriteria}>• Contain at least one special character</Text>
        </View>
        <TextInput
            style={styles.input}
            placeholder="Password"
            secureTextEntry
            value={formData.password}
            onChangeText={(text) => handleInputChange('password', text)}
        />
        <TextInput
            style={styles.input}
            placeholder="Confirm Password"
            secureTextEntry
            value={formData.confirm_password}
            onChangeText={(text) => handleInputChange('confirm_password', text)}
        />
    </View>
  );
};

export default WholesalerPassword;

const styles = StyleSheet.create({
    textContainer:{
        marginBottom: '3%',
    },
    stepTitle: {
        fontSize: '20%',
        alignSelf: 'left',
        color: '#0C5E52',
        fontWeight: 'bold',
        marginBottom: '3%',
    },
    passwordCriteria:{
        fontSize: '13%',
        alignSelf: 'left',
        color: '#0C5E52',
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