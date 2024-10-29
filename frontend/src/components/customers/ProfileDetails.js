import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { useUserStore } from '../../lib/userStore';

const ProfileDetails = ({ userData, navigation }) => {
  const { resetUser, paymentDetails } = useUserStore()

  return (
    <View style={styles.container}>
      <View style={styles.topPortion}>
        <View style={styles.header}>
          <Text style={styles.headerTitle}>Profile</Text>
          <TouchableOpacity onPress={() => resetUser()}>
            <Image source={require('../../../assets/imgs/logout.png')} style={styles.logoutButton} />
          </TouchableOpacity>
        </View>
        <View style={styles.profileCard}>
          <View style={styles.profileCardLeft}>
            <Image source={require('../../../assets/imgs/profile.png')} style={styles.profilePicture} />
            <Text style={styles.name}>{userData.firstName + " " + userData.lastName}</Text>
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
        <View style={styles.row}>
          <Text style={styles.detailText}>Email: </Text>
          <Text style={styles.detailTextVal}>{userData.email}</Text>
        </View>
        <View style={styles.row}>
          <Text style={styles.detailText}>Contact: </Text>
          <Text style={styles.detailTextVal}>{userData.contact}</Text>
        </View>
        <Text style={styles.detailText}>Address: </Text>
        <Text style={styles.detailTextVal}>{`${userData.streetName} ${userData.unitNo}, ${userData.buildingName}, ${userData.postalCode}`}</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f0f0f0',
  },
  topPortion: {
    backgroundColor: '#D6E8A4',
    paddingTop: "15%",
    paddingBottom: '5%',
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
  row: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
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
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginBottom: 5,
  },
  infoValue: {
    fontSize: 16,
    color: '#0C5E52',
  },
  accountDetails: {
    backgroundColor: 'white',
    borderRadius: 10,
    padding: '4%',
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
    fontWeight: 'bold',
    marginBottom: "5%",
  },
  detailTextVal: {
    fontSize: 16,
    marginBottom: "5%",
  },
  cardInfo: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: "5%",
  },
  cardText: {
    fontSize: 16,
    marginLeft: 10,
  },
});

export default ProfileDetails;