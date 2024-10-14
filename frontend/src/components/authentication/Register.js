import React, { useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet, Image, SafeAreaView } from "react-native";
import { Ionicons } from '@expo/vector-icons';
import RegisterCustomer from "./RegisterCustomer";
import RegisterWholesaler from "./RegisterWholesaler";
import Login from "./Login";
import Confirmation from "./Confirmation"; 

const Register = ({ onBackPress, onLoginPress }) => {
  const [currentState, setCurrentState] = useState('choice');

  const renderChoice = () => (
    <View style={styles.container}>
      <TouchableOpacity 
          style={styles.backButton}
          onPress={onBackPress}
      >
          <Ionicons 
            color={'#EBF3D1'}
            size={20}
            name="arrow-back-outline"
          />
      </TouchableOpacity>
      <Text style={styles.title}>Kick-start your food journey with us.</Text>
      <Text style={styles.subtitle}>I am a...</Text>
      
      <View style={styles.optionOutline}>
        <TouchableOpacity 
          style={styles.option} 
          onPress={() => setCurrentState('consumer')}
        >
          <Text style={styles.optionText}>Consumer</Text>
        </TouchableOpacity>
        <View style={styles.iconContainer}>
          <Image 
            source={require('../../../assets/imgs/consumerIcon.png')} 
            style={styles.icon}
          />
        </View>
      </View>
      
      <View style={styles.optionOutline}>
        <View style={styles.iconContainer}>
          <Image 
            source={require('../../../assets/imgs/businessIcon.png')} 
            style={styles.icon}
          />
        </View>
        <TouchableOpacity 
          style={styles.option} 
          onPress={() => setCurrentState('business')}
        >
          <Text style={styles.optionText}>Business Owner</Text>
        </TouchableOpacity>
      </View>
      
      <TouchableOpacity 
        style={styles.loginLink}
        onPress={onLoginPress}
      >
        <Text style={styles.loginText}>Already have an account? Log in here</Text>
      </TouchableOpacity>
    </View>
  );

  const renderConsumerForm = () => (
    <View style={styles.container}>
      <RegisterCustomer 
          onBackPress={() => setCurrentState('choice')}
      />
    </View>
  );

  const renderBusinessForm = () => (
    <View style={styles.container}>
      <RegisterWholesaler
          onBackPress={() => setCurrentState('choice')}
      />
    </View>
  );

  const renderLogin = () => (
    <View style={styles.default}>
      <Login 
        onBackPress={() => setCurrentState('choice')}
        onRegisterPress={() => setCurrentState('choice')}
      />
    </View>
  );

  const renderCustomerConfirmation = () => (
    <View style={styles.container}>
      <Image
          source={require('../../../assets/imgs/Confirmation.png')}
          style={styles.image}
        />
      <Text style={styles.regComp}>Registration Complete!</Text>
      <Text style={styles.regSub}>Welcome to Shoppeas!</Text>
      <TouchableOpacity 
        style={styles.loginButton}
        onPress={() => setCurrentState('login')}
      >
        <Text style={styles.loginButtonText}>Login</Text>
        <Ionicons name="arrow-forward" size={24} color="#EBF3D1" style={styles.arrowIcon} />
      </TouchableOpacity>
    </View>
  );

  const renderWholesalerConfirmation = () => (
    <View style={styles.contianer}>
      <Image
          source={require('../../../assets/imgs/Confirmation.png')}
          style={styles.image}
        />
      <Text style={styles.regComp}>Registration Complete!</Text>
      <Text style={styles.regSub}>Welcome to Shoppeas!</Text>
      <TouchableOpacity 
        style={styles.loginButton}
        onPress={() => setCurrentState('login')}
      >
        <Text style={styles.loginButtonText}>Login</Text>
        <Ionicons name="arrow-forward" size={24} color="#EBF3D1" style={styles.arrowIcon} />
      </TouchableOpacity>
    </View>
  );

  return (
    <SafeAreaView style={styles.safeArea}>
      {currentState === 'choice' && renderChoice()}
      {currentState === 'consumer' && renderConsumerForm()}
      {currentState === 'business' && renderBusinessForm()}
      {currentState === 'login' && renderLogin()}
      {currentState === 'cus_confirm' && renderCustomerConfirmation()}
      {currentState === 'biz_confirm' && renderWholesalerConfirmation()}
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
  },
  container: {
    flex: 1,
    padding: 20,
    width: '100%',
    justifyContent: 'center',
  },
  backButton: {
    position: 'absolute',
    top: '3%',
    left: '-11%',
    width: '13%',
    height: '5.2%',
    borderRadius: '100%',
    backgroundColor: '#0C5E52',
    justifyContent: 'center',
    alignItems: 'center',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginBottom: 10,
  },
  subtitle: {
    fontSize: 18,
    color: '#0C5E52',
    marginBottom: 20,
  },
  optionsContainer: {
    marginBottom: '3%',
  },
  optionOutline: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 20,
    borderRadius: 15,
    padding: '3%',
    backgroundColor: '#EBF3D1',
  },
  iconContainer: {
    width: 60,
    height: 60,
    justifyContent: 'center',
    alignItems: 'center',
  },
  icon: {
    width: 70,
    height: 90,
    resizeMode: 'contain',
  },
  option: {
    flex: 1,
    borderRadius: 10,
    padding: 15,
    alignItems: 'center',
    justifyContent: 'center',
  },
  optionText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  loginLink: {
    marginTop: 20,
  },
  loginText: {
    color: '#0C5E52',
    textAlign: 'center',
  },
  image: {
    width: 300,
    height: "35%",
    resizeMode: 'contain',
    marginBottom: '5%',
  },
  regComp: {
    fontSize: '25%',
    fontWeight: 'bold',
    color: '#0C5E52',
    alignSelf: 'center',
  },
  regSub: {
    fontSize: '20%',
    color: '#0C5E52',
    alignSelf: 'center',
    marginTop: '2%',
  },
  loginButton: {
    flexDirection: 'row',
    backgroundColor: '#0C5E52',
    width: '100%',
    height: '5%',
    borderRadius: '20%',
    justifyContent: 'center',
    alignSelf: 'center',
    marginTop: '7%',
  },
  loginButtonText: {
      fontSize: '20%',
      color: '#EBF3D1',
      fontWeight: 'bold',
      marginRight: "30%",
      alignSelf: 'center',
  },
  arrowIcon: {
      position: 'absolute',
      right: '5%',
      alignSelf: 'center',
  },
});

export default Register;