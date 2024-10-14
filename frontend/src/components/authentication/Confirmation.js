import React from 'react';
import { View, Text, TouchableOpacity, Image, StyleSheet } from 'react-native';
import { Ionicons } from '@expo/vector-icons';

const Confirmation = ({ onLoginPress, userType }) => {
  return (
    <View style={styles.container}>
      <Image
        source={require('../../../assets/imgs/Confirmation.png')}
        style={styles.image}
      />
      <Text style={styles.regComp}>Registration Complete!</Text>
      <Text style={styles.regSub}>
        {userType === 'business' 
          ? 'Businesses may only start operations on ShopPeas once verification is complete, which may take 5-7 business days. Your patience is most appreciated.'
          : 'Welcome to ShopPeas!'}
      </Text>
      <TouchableOpacity 
        style={styles.loginButton}
        onPress={onLoginPress}
      >
        <Text style={styles.loginButtonText}>Login</Text>
        <Ionicons name="arrow-forward" size={24} color="#EBF3D1" style={styles.arrowIcon} />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
  },
  image: {
    width: 300,
    height: '35%',
    resizeMode: 'contain',
    marginBottom: '5%',
  },
  regComp: {
    fontSize: 25,
    fontWeight: 'bold',
    color: '#0C5E52',
    textAlign: 'center',
  },
  regSub: {
    fontSize: 16,
    color: '#0C5E52',
    textAlign: 'center',
    marginTop: 10,
    marginBottom: 20,
  },
  loginButton: {
    flexDirection: 'row',
    backgroundColor: '#0C5E52',
    width: '100%',
    height: 50,
    borderRadius: 25,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 20,
  },
  loginButtonText: {
    fontSize: 18,
    color: '#EBF3D1',
    fontWeight: 'bold',
    marginRight: 30,
  },
  arrowIcon: {
    position: 'absolute',
    right: 20,
  },
});

export default Confirmation;