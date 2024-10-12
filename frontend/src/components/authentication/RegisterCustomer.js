import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, ScrollView, StyleSheet, Image, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';

// Import the image correctly
import RegistrationCompleteImage from '../../../assets/imgs/registration_complete.png';

const RegisterCustomer = () => {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    password: '',
    confirmPassword: '',
    streetName: '',
    unitNo: '',
    buildingName: '',
    cityCountry: '',
    postalCode: '',
  });
  const [errors, setErrors] = useState({});
  const navigation = useNavigation();

  const handleChange = (name, value) => {
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const validateStep1 = () => {
    let stepErrors = {};
    if (!formData.firstName) stepErrors.firstName = 'First name is required';
    if (!formData.lastName) stepErrors.lastName = 'Last name is required';
    if (!formData.email) stepErrors.email = 'Email is required';
    else if (!/\S+@\S+\.\S+/.test(formData.email)) stepErrors.email = 'Email is invalid';
    if (!formData.phoneNumber) stepErrors.phoneNumber = 'Phone number is required';
    if (!formData.password) stepErrors.password = 'Password is required';
    else if (formData.password.length < 12) stepErrors.password = 'Password must be at least 12 characters long';
    else if (!/\d/.test(formData.password)) stepErrors.password = 'Password must contain at least one number';
    else if (!/[!@#$%^&*]/.test(formData.password)) stepErrors.password = 'Password must contain at least one special character';
    if (formData.password !== formData.confirmPassword) stepErrors.confirmPassword = 'Passwords do not match';
    return stepErrors;
  };

  const validateStep2 = () => {
    let stepErrors = {};
    if (!formData.streetName) stepErrors.streetName = 'Street name is required';
    if (!formData.cityCountry) stepErrors.cityCountry = 'City and Country are required';
    if (!formData.postalCode) stepErrors.postalCode = 'Postal code is required';
    return stepErrors;
  };

  const handleNext = () => {
    const stepErrors = validateStep1();
    if (Object.keys(stepErrors).length === 0) {
      setStep(2);
    } else {
      setErrors(stepErrors);
    }
  };

  const handleRegister = () => {
    const stepErrors = validateStep2();
    if (Object.keys(stepErrors).length === 0) {
      // Implement registration logic here
      console.log('Registration data:', formData);
      // You would typically make an API call here to register the user
      // For now, we'll just move to the next step
      setStep(3);
    } else {
      setErrors(stepErrors);
    }
  };

  const handleLogin = () => {
    navigation.navigate('Login');
  };

  const renderStep1 = () => (
    <View>
      <Text style={styles.title}>Register as a Consumer</Text>
      <Text style={styles.subtitle}>Step 1 of 2: Personal Information</Text>
      <TextInput
        style={styles.input}
        placeholder="First Name"
        value={formData.firstName}
        onChangeText={(text) => handleChange('firstName', text)}
      />
      {errors.firstName && <Text style={styles.error}>{errors.firstName}</Text>}
      <TextInput
        style={styles.input}
        placeholder="Last Name"
        value={formData.lastName}
        onChangeText={(text) => handleChange('lastName', text)}
      />
      {errors.lastName && <Text style={styles.error}>{errors.lastName}</Text>}
      <TextInput
        style={styles.input}
        placeholder="Email"
        keyboardType="email-address"
        value={formData.email}
        onChangeText={(text) => handleChange('email', text)}
      />
      {errors.email && <Text style={styles.error}>{errors.email}</Text>}
      <TextInput
        style={styles.input}
        placeholder="Phone Number"
        keyboardType="phone-pad"
        value={formData.phoneNumber}
        onChangeText={(text) => handleChange('phoneNumber', text)}
      />
      {errors.phoneNumber && <Text style={styles.error}>{errors.phoneNumber}</Text>}
      <TextInput
        style={styles.input}
        placeholder="Choose Password"
        secureTextEntry
        value={formData.password}
        onChangeText={(text) => handleChange('password', text)}
      />
      {errors.password && <Text style={styles.error}>{errors.password}</Text>}
      <TextInput
        style={styles.input}
        placeholder="Confirm Password"
        secureTextEntry
        value={formData.confirmPassword}
        onChangeText={(text) => handleChange('confirmPassword', text)}
      />
      {errors.confirmPassword && <Text style={styles.error}>{errors.confirmPassword}</Text>}
      <TouchableOpacity style={styles.button} onPress={handleNext}>
        <Text style={styles.buttonText}>Next</Text>
      </TouchableOpacity>
    </View>
  );

  const renderStep2 = () => (
    <View>
      <Text style={styles.title}>Registered Address</Text>
      <Text style={styles.subtitle}>Step 2 of 2: Address Information</Text>
      <TextInput
        style={styles.input}
        placeholder="Street Name"
        value={formData.streetName}
        onChangeText={(text) => handleChange('streetName', text)}
      />
      {errors.streetName && <Text style={styles.error}>{errors.streetName}</Text>}
      <TextInput
        style={styles.input}
        placeholder="Unit No."
        value={formData.unitNo}
        onChangeText={(text) => handleChange('unitNo', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Building Name"
        value={formData.buildingName}
        onChangeText={(text) => handleChange('buildingName', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="City, Country"
        value={formData.cityCountry}
        onChangeText={(text) => handleChange('cityCountry', text)}
      />
      {errors.cityCountry && <Text style={styles.error}>{errors.cityCountry}</Text>}
      <TextInput
        style={styles.input}
        placeholder="Postal Code"
        value={formData.postalCode}
        onChangeText={(text) => handleChange('postalCode', text)}
      />
      {errors.postalCode && <Text style={styles.error}>{errors.postalCode}</Text>}
      <View style={styles.buttonContainer}>
        <TouchableOpacity style={styles.button} onPress={() => setStep(1)}>
          <Text style={styles.buttonText}>Back</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={handleRegister}>
          <Text style={styles.buttonText}>Register</Text>
        </TouchableOpacity>
      </View>
    </View>
  );

  const renderStep3 = () => (
    <View style={styles.completionContainer}>
      <Image
        source={RegistrationCompleteImage}
        style={styles.completionImage}
      />
      <Text style={styles.completionTitle}>Registration Complete!</Text>
      <Text style={styles.completionSubtitle}>Welcome to ShopPeas!</Text>
      <TouchableOpacity style={styles.loginButton} onPress={handleLogin}>
        <Text style={styles.loginButtonText}>LOGIN</Text>
      </TouchableOpacity>
    </View>
  );

  return (
    <ScrollView contentContainerStyle={styles.container}>
      {step === 1 ? renderStep1() : step === 2 ? renderStep2() : renderStep3()}
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    justifyContent: 'center',
    padding: 20,
    backgroundColor: '#f0f0f0',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 10,
    textAlign: 'center',
    color: '#333',
  },
  subtitle: {
    fontSize: 16,
    marginBottom: 20,
    textAlign: 'center',
    color: '#666',
  },
  input: {
    backgroundColor: '#fff',
    borderWidth: 1,
    borderColor: '#ddd',
    padding: 15,
    marginBottom: 15,
    borderRadius: 5,
    fontSize: 16,
  },
  button: {
    backgroundColor: '#4CAF50',
    padding: 15,
    borderRadius: 5,
    alignItems: 'center',
    marginTop: 10,
  },
  buttonText: {
    color: '#fff',
    fontWeight: 'bold',
    fontSize: 16,
  },
  error: {
    color: 'red',
    marginBottom: 10,
  },
  buttonContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  completionContainer: {
    alignItems: 'center',
  },
  completionImage: {
    width: 200,
    height: 200,
    resizeMode: 'contain',
    marginBottom: 20,
  },
  completionTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 10,
    color: '#4CAF50',
  },
  completionSubtitle: {
    fontSize: 18,
    marginBottom: 30,
    color: '#666',
  },
  loginButton: {
    backgroundColor: '#2E7D32',
    paddingVertical: 15,
    paddingHorizontal: 30,
    borderRadius: 25,
  },
  loginButtonText: {
    color: '#fff',
    fontWeight: 'bold',
    fontSize: 16,
  },
});

export default RegisterCustomer;