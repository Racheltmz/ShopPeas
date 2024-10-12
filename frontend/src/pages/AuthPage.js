import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, SafeAreaView, ImageBackground } from 'react-native';

const AuthPage = ({ navigation }) => {
  const navigateToLogin = () => {
    navigation.navigate('Login'); // Navigate to the login page
  };

  const navigateToRegister = (isConsumer) => {
    navigation.navigate('Register', { isConsumer }); // Navigate to the register page, passing whether the user is a consumer or business owner
  };

  return (
    <SafeAreaView style={styles.container}>
      {/* Background Image */}
      <ImageBackground
        source={require('../../assets/imgs/backGroundImage.png')} // Corrected path
        style={styles.backgroundImage}
      >
        {/* Overlay for blank middle section */}
        <View style={styles.overlay}>
          <View style={styles.header}>
            {/* Back button */}
            <TouchableOpacity>
              <Image source={require('../../assets/imgs/pea.png')} style={styles.backIcon} />
            </TouchableOpacity>
          </View>

          {/* Main Content */}
          <View style={styles.mainContent}>
            <Text style={styles.mainTitle}>Kick-start your food journey with us.</Text>
            <Text style={styles.subTitle}>I am a...</Text>

            {/* Consumer button */}
            <TouchableOpacity style={styles.optionButton} onPress={() => navigateToRegister(true)}>
              <View style={styles.optionContent}>
                <Text style={styles.optionText}>Consumer</Text>
                <Image source={require('../../assets/imgs/consumerIcon.png')} style={styles.optionIcon} />
              </View>
            </TouchableOpacity>

            {/* Business Owner button */}
            <TouchableOpacity style={styles.optionButton} onPress={() => navigateToRegister(false)}>
              <View style={styles.optionContent}>
                <Text style={styles.optionText}>Business Owner</Text>
                <Image source={require('../../assets/imgs/businessIcon.png')} style={styles.optionIcon} />
              </View>
            </TouchableOpacity>

            {/* Log In Link */}
            <Text style={styles.loginPrompt}>
              Already have an account?{' '}
              <Text style={styles.loginLink} onPress={navigateToLogin}>
                Log In here
              </Text>
            </Text>
          </View>
        </View>
      </ImageBackground>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  backgroundImage: {
    flex: 1,
    resizeMode: 'cover',
    justifyContent: 'center',
  },
  overlay: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(255, 255, 255, 0.8)',
    paddingHorizontal: 20,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 16,
    alignSelf: 'flex-start',
  },
  backIcon: {
    width: 32,
    height: 32,
  },
  pageTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    marginLeft: 8,
    color: '#333',
  },
  mainContent: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  mainTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    textAlign: 'center',
    color: '#0C5E52',
    marginVertical: 16,
  },
  subTitle: {
    fontSize: 18,
    color: '#333',
    marginBottom: 16,
  },
  optionButton: {
    backgroundColor: '#EBF3D1',
    padding: 16,
    borderRadius: 8,
    marginBottom: 16,
    width: '80%',
    alignItems: 'center',
  },
  optionContent: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  optionText: {
    fontSize: 18,
    color: '#0C5E52',
    fontWeight: 'bold',
  },
  optionIcon: {
    width: 50,
    height: 50,
  },
  loginPrompt: {
    fontSize: 14,
    color: '#333',
  },
  loginLink: {
    fontWeight: 'bold',
    color: '#0C5E52',
  },
});

export default AuthPage;






