import React, { useState } from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, ScrollView, TextInput } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { SafeAreaView } from 'react-native-safe-area-context';
import { createStackNavigator } from '@react-navigation/stack';



const Profile = () => {
  return (
    <SafeAreaView style={styles.container}>
      <ScrollView>
        <View style={styles.header}>
          <View style={styles.headerLeft}>
            <TouchableOpacity style={styles.backButton}>
              <Ionicons name="arrow-back" size={24} color="#0C5E52" />
            </TouchableOpacity>
            <Text style={styles.headerTitle}>Profile</Text>
            <Image source={require('../../../assets/imgs/pea.png')} style={styles.peaIcon} />
          </View>
          <TouchableOpacity style={styles.settingsButton}>
            <Ionicons name="settings-outline" size={24} color="#0C5E52" />
          </TouchableOpacity>
        </View>

        <View style={styles.profileCard}>
          <View style={styles.profileLeft}>
            <Image
              source={require('../../../assets/imgs/ProfileWholesaler.png')}
              style={styles.profilePic}
            />
            <View style={styles.nameContainer}>
              <Text style={styles.name}>Happy</Text>
              <Text style={styles.name}>Wholesaler</Text>
            </View>
          </View>
          <View style={styles.profileRight}>
            <InfoRow label="Date Joined:" value="25-07-2024" />
            <InfoRow label="Location:" value="Singapore" />
            <InfoRow label="Currency:" value="SGD" />
            <InfoRow label="UEN:" value="123456789" />
            <View style={styles.verifiedBadge}>
              <Text style={styles.verifiedText}>Verified</Text>
            </View>
          </View>
        </View>

        <View style={styles.accountDetails}>
          <View style={styles.accountHeader}>
            <Text style={styles.accountTitle}>Account Details</Text>
            <TouchableOpacity>
              <Ionicons name="create-outline" size={24} color="#0C5E52" />
            </TouchableOpacity>
          </View>
          <View style={styles.accountInfo}>
            <View style={styles.accountColumn}>
              <InfoRow label="Contact:" value="+65 9863 3472" />
              <View style={styles.cardInfo}>
                <Image source={require('../../../assets/imgs/MasterCardIcon.png')} style={styles.cardIcon} />
                <Text style={styles.cardText}>Card *1234</Text>
              </View>
            </View>
            <View style={styles.accountColumn}>
              <InfoRow 
                label="Address:" 
                value="123 Bishan Street 10&#x0A;#01-45&#x0A;Happy Building&#x0A;S23491"
                multiline
              />
            </View>
          </View>
          <InfoRow label="Email:" value="contact@happywholesaler.com" />
        </View>

        <TouchableOpacity style={styles.shopReviewsButton}>
          <Text style={styles.shopReviewsText}>Shop Reviews</Text>
          <Ionicons name="chevron-forward" size={24} color="white" />
        </TouchableOpacity>

        <TouchableOpacity style={styles.myProductsButton}>
          <Text style={styles.myProductsText}>My Products</Text>
          <Ionicons name="chevron-forward" size={24} color="#0C5E52" />
        </TouchableOpacity>
      </ScrollView>
    </SafeAreaView>
  );
};

const InfoRow = ({ label, value, multiline }) => (
  <View style={styles.infoRow}>
    <Text style={styles.infoLabel}>{label}</Text>
    <Text style={[styles.infoValue, multiline && styles.multilineValue]}>{value}</Text>
  </View>
);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5F5F5',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 16,
    backgroundColor: '#EBF3D1',
  },
  headerLeft: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  backButton: {
    marginRight: 16,
  },
  headerTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginRight: 8,
  },
  peaIcon: {
    width: 24,
    height: 24,
  },
  settingsButton: {
    padding: 8,
  },
  profileCard: {
    flexDirection: 'row',
    backgroundColor: '#EBF3D1',
    padding: 16,
  },
  profileLeft: {
    flex: 1,
    alignItems: 'center',
  },
  profilePic: {
    width: 100,
    height: 100,
    borderRadius: 50,
    marginBottom: 8,
  },
  nameContainer: {
    alignItems: 'center',
  },
  name: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  profileRight: {
    flex: 1,
    justifyContent: 'center',
  },
  infoRow: {
    marginBottom: 8,
  },
  infoLabel: {
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  infoValue: {
    color: '#333',
  },
  multilineValue: {
    marginTop: 4,
  },
  verifiedBadge: {
    backgroundColor: '#A8E0A4',
    paddingHorizontal: 12,
    paddingVertical: 4,
    borderRadius: 16,
    alignSelf: 'flex-start',
    marginTop: 8,
  },
  verifiedText: {
    color: '#0C5E52',
    fontWeight: 'bold',
  },
  accountDetails: {
    backgroundColor: 'white',
    margin: 16,
    padding: 16,
    borderRadius: 8,
  },
  accountHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 16,
  },
  accountTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  accountInfo: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  accountColumn: {
    flex: 1,
  },
  cardInfo: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 8,
  },
  cardIcon: {
    width: 40,
    height: 24,
    marginRight: 8,
  },
  cardText: {
    color: '#0C5E52',
    fontWeight: 'bold',
  },
  shopReviewsButton: {
    backgroundColor: '#0C5E52',
    margin: 16,
    marginBottom: 8,
    borderRadius: 8,
    padding: 16,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  shopReviewsText: {
    fontWeight: 'bold',
    fontSize: 18,
    color: 'white',
  },
  myProductsButton: {
    backgroundColor: '#EBF3D1',
    margin: 16,
    marginTop: 8,
    borderRadius: 8,
    padding: 16,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  myProductsText: {
    fontWeight: 'bold',
    fontSize: 18,
    color: '#0C5E52',
  },
});




const Stack = createStackNavigator();


const EditAccount = ({ navigation }) => {
  const [country, setCountry] = useState('');
  const [currency, setCurrency] = useState('');
  const [phone, setPhone] = useState('');
  const [email, setEmail] = useState('');
  const [street, setStreet] = useState('');
  const [unit, setUnit] = useState('');
  const [building, setBuilding] = useState('');
  const [postalCode, setPostalCode] = useState('');

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView>
        <View style={styles.header}>
          <TouchableOpacity onPress={() => navigation.goBack()} style={styles.backButton}>
            <Ionicons name="arrow-back" size={24} color="#0C5E52" />
          </TouchableOpacity>
          <Text style={styles.headerTitle}>Edit Account Details</Text>
        </View>

        <View style={styles.profileSummary}>
          <Image
            source={require('../../../assets/imgs/ProfileWholesaler.png')}
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
          <TextInput
            style={styles.input}
            placeholder="Select Country"
            value={country}
            onChangeText={setCountry}
          />

          <Text style={styles.sectionTitle}>Currency:</Text>
          <TextInput
            style={styles.input}
            placeholder="Select Currency"
            value={currency}
            onChangeText={setCurrency}
          />

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
          <TouchableOpacity style={styles.saveButton} onPress={() => navigation.goBack()}>
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



const ProfileStack = () => {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="ProfileMain" component={Profile} />
      <Stack.Screen name="EditAccount" component={EditAccount} />
    </Stack.Navigator>
  );
};

export default ProfileStack; 

