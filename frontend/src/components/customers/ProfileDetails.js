import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { useUserStore } from '../../lib/userStore';

const ProfileDetails = ({ userData, navigation }) => {
  const { resetUser } = useUserStore()
  return (
    <View style={styles.container}>
      <View style={styles.topPortion}>
        <View style={styles.header}>
          <Text style={styles.headerTitle}>Profile</Text>
          {/* <Image source={require('../../../assets/imgs/DummyImage.jpg')} style={styles.peaIcon} /> */}
          <TouchableOpacity onPress={() => resetUser()}>
            <Image source={require('../../../assets/imgs/logout.png')} style={styles.logoutButton} />
          </TouchableOpacity>
        </View>
        <View style={styles.profileCard}>
          <View style={styles.profileCardLeft}>
            <Image source={require('../../../assets/imgs/DummyImage.jpg')} style={styles.profilePicture} />
            <Text style={styles.name}>{userData.name}</Text>
          </View>
          <View style={styles.profileCardRight}>
            <View style={{marginVertical: 10}}>
              <Text style={styles.infoTitle}>Date Joined:</Text>
              <Text style={styles.infoValue}>{userData.dateJoined}</Text>
            </View>
            <View style={{marginVertical: 10}}>
              <Text style={styles.infoTitle}>Location:</Text>
              <Text style={styles.infoValue}>{userData.city}</Text>
            </View>
          </View>
        </View>
      </View>


      <View style={styles.accountDetails}>
        <View style={styles.accountHeader}>
          <Text style={styles.accountTitle}>Account Details</Text>
          <TouchableOpacity onPress={() => navigation.navigate('ProfileEdit', { userData })} style={styles.editButton}>
            <Ionicons name="create-outline" size={40} color="#0C5E52" />
          </TouchableOpacity>
        </View>
        <Text style={styles.detailText}>Contact: {userData.contact}</Text>
        <Text style={styles.detailText}>Address: {`${userData.streetName} ${userData.unitNo}, ${userData.buildingName}, ${userData.postalCode}`}</Text>
        <View style={styles.cardInfo}>
          <Ionicons name="card" size={24} color="#0C5E52" />
          <Text style={styles.cardText}>Card *1234</Text>
        </View>
        <Text style={styles.detailText}>Email: {userData.email}</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f0f0f0',
    paddingTop: "15%",
  },
  topPortion: {
    backgroundColor: '#D6E8A4',
    paddingVertical: '5%',
    paddingHorizontal: '8%',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  headerTitle: {
    fontSize: 30,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  peaIcon: {
    width: 24,
    height: 24,
  },
  logoutButton: {
    width: 24,
    height: 24,
  },
  profileCard: {
    paddingTop: '8%',
    borderRadius: 10,
    marginBottom: 20,
    flexDirection: 'row',
  },
  profileCardLeft: {
    justifyContent: 'flex-start',
    alignItems: 'center',
    paddingLeft: '7%',
    paddingRight: '20%',
  },
  profilePicture: {
    width: 80,
    height: 80,
    borderRadius: 40,
    marginBottom: 10,
  },
  name: {
    fontSize: 22,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  profileCardRight: {
    borderRadius: 10,
    paddingHorizontal: '5%',
    justifyContent: 'center',
  },
  infoTitle: {
    fontSize: 16,
    color: '#666',
  },
  infoValue: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  accountDetails: {
    backgroundColor: 'white',
    borderRadius: 10,
    padding: '3%',
    margin: '5%',
  },
  accountHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 10,
  },
  accountTitle: {
    fontSize: 25,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginBottom: '5%',
  },
  detailText: {
    fontSize: 16,
    marginBottom: 10,
  },
  cardInfo: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 5,
  },
  cardText: {
    fontSize: 16,
    marginLeft: 10,
  },
});

export default ProfileDetails;