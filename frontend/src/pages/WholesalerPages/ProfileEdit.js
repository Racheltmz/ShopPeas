import React, { useState } from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, ScrollView, TextInput } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { SafeAreaView } from 'react-native-safe-area-context';

const ProfileEdit = ({ navigation }) => {
  const [country, setCountry] = useState('');
  const [currency, setCurrency] = useState('');
  const [phone, setPhone] = useState('');
  const [email, setEmail] = useState('');
  const [street, setStreet] = useState('');
  const [unit, setUnit] = useState('');
  const [building, setBuilding] = useState('');
  const [postalCode, setPostalCode] = useState('');

  const handleSave = () => {
    // Implement save logic here
    // After saving, navigate back to the Profile screen
    navigation.goBack();
  };

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView>
        <View style={styles.header}>
          {/* Use the Ionicons arrow icon as the back button */}
          <TouchableOpacity onPress={() => navigation.goBack()} style={styles.backButton}>
            <Ionicons name="arrow-back" size={24} color="#0C5E52" />
          </TouchableOpacity>
          <Text style={styles.headerTitle}>Edit Account Details</Text>
        </View>

        <View style={styles.profileSummary}>
          <Image
            source={require('../../../assets/imgs/profile.png')}
            style={styles.profilePic}
          />
          <Text style={styles.name}>Happy Wholesaler</Text>
        </View>

        <View style={styles.infoSection}>
          <InfoRow label="Date Joined:" value="25-07-2024" />
          <InfoRow label="UEN:" value="123456789" />
        </View>

        <View style={styles.editSection}>
          <Text style={styles.sectionTitle}>Location:</Text>
          <View style={styles.selectInput}>
            <TextInput
              style={styles.input}
              placeholder="Select Country"
              value={country}
              onChangeText={setCountry}
            />
            <Ionicons name="chevron-down" size={24} color="#0C5E52" />
          </View>

          <Text style={styles.sectionTitle}>Currency:</Text>
          <View style={styles.selectInput}>
            <TextInput
              style={styles.input}
              placeholder="Select Currency"
              value={currency}
              onChangeText={setCurrency}
            />
            <Ionicons name="chevron-down" size={24} color="#0C5E52" />
          </View>

          <Text style={styles.sectionTitle}>Contact Details:</Text>
          <TextInput
            style={styles.input}
            placeholder="Phone No."
            value={phone}
            onChangeText={setPhone}
          />
          <TextInput
            style={styles.input}
            placeholder="Email"
            value={email}
            onChangeText={setEmail}
          />

          <Text style={styles.sectionTitle}>Address:</Text>
          <TextInput
            style={styles.input}
            placeholder="Street Name"
            value={street}
            onChangeText={setStreet}
          />
          <TextInput
            style={styles.input}
            placeholder="Unit No."
            value={unit}
            onChangeText={setUnit}
          />
          <TextInput
            style={styles.input}
            placeholder="Building Name"
            value={building}
            onChangeText={setBuilding}
          />
          <TextInput
            style={styles.input}
            placeholder="Postal Code"
            value={postalCode}
            onChangeText={setPostalCode}
          />
        </View>

        <View style={styles.buttonContainer}>
          <TouchableOpacity style={styles.saveButton} onPress={handleSave}>
            <Text style={styles.saveButtonText}>Save</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.cancelButton} onPress={() => navigation.goBack()}>
            <Text style={styles.cancelButtonText}>Cancel</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const InfoRow = ({ label, value }) => (
  <View style={styles.infoRow}>
    <Text style={styles.infoLabel}>{label}</Text>
    <Text style={styles.infoValue}>{value}</Text>
  </View>
);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5F5F5',
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 16,
    backgroundColor: '#EBF3D1',
  },
  backButton: {
    marginRight: 16,
  },
  headerTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  profileSummary: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 16,
    backgroundColor: '#EBF3D1',
  },
  profilePic: {
    width: 60,
    height: 60,
    borderRadius: 30,
    marginRight: 16,
  },
  name: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  infoSection: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    padding: 16,
    backgroundColor: '#EBF3D1',
  },
  infoRow: {
    alignItems: 'center',
  },
  infoLabel: {
    color: '#6B9080',
    marginBottom: 4,
  },
  infoValue: {
    color: '#0C5E52',
    fontWeight: 'bold',
  },
  editSection: {
    padding: 16,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginTop: 16,
    marginBottom: 8,
  },
  selectInput: {
    flexDirection: 'row',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: '#CCCCCC',
    borderRadius: 8,
    marginBottom: 12,
  },
  input: {
    flex: 1,
    padding: 12,
    fontSize: 16,
  },
  buttonContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    padding: 16,
  },
  saveButton: {
    backgroundColor: '#A8E0A4',
    paddingVertical: 12,
    paddingHorizontal: 24,
    borderRadius: 8,
  },
  saveButtonText: {
    color: '#0C5E52',
    fontWeight: 'bold',
    fontSize: 16,
  },
  cancelButton: {
    backgroundColor: '#F5F5F5',
    paddingVertical: 12,
    paddingHorizontal: 24,
    borderRadius: 8,
  },
  cancelButtonText: {
    color: '#0C5E52',
    fontSize: 16,
  },
});

export default ProfileEdit;
