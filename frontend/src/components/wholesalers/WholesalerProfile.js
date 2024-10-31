import React from 'react';
import { View, Text, TouchableOpacity } from 'react-native';
import { Ionicons } from '@expo/vector-icons';

const Profile = ({ navigation }) => {
  return (
    <View>
      {/* Other profile content */}
      <TouchableOpacity 
        style={styles.settingsButton}
        onPress={() => navigation.navigate('ProfileStack', { screen: 'ProfileEdit' })}
      >
        <Ionicons name="settings-outline" size={24} color="#0C5E52" />
      </TouchableOpacity>
    </View>
  );
};

export default Profile;