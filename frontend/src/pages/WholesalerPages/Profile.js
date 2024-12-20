import React, { useEffect, useState } from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, ScrollView } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { SafeAreaView } from 'react-native-safe-area-context';
import { useUserStore } from '../../lib/userStore';
import { useNavigation } from '@react-navigation/native';
import wholesalerService from '../../service/WholesalerService';

const Profile = () => {
  const { resetUser, currentUser, userUid, userAddress } = useUserStore();
  const navigation = useNavigation();
  const [rating, setRatings] = useState(0);
  const [numRatings, setNumRatings] = useState([]);
  const [totalRatings, setTotalRatings] = useState(0);

  useEffect(() => {
    wholesalerService.getRating(userUid, currentUser.uen)
      .then((res) => {
        setRatings(res.rating);
        setNumRatings(res.num_ratings);
        setTotalRatings(res.num_ratings.reduce((a, b) => a + b, 0));
      })
      .catch((err) => {
        console.error(err);
      })
  }, [userUid]);


  return (
    <SafeAreaView style={styles.container}>
      <ScrollView>
        <View style={styles.header}>
          <View style={styles.headerLeft}>
            <Text style={styles.headerTitle}>Profile</Text>
            <Image source={require('../../../assets/imgs/pea.png')} style={styles.peaIcon} />
          </View>
          <TouchableOpacity onPress={() => resetUser()}>
            <Ionicons name="log-out-outline" size={24} color="#0C5E52" />
          </TouchableOpacity>
        </View>

        <View style={styles.profileCard}>
          <View style={styles.profileLeft}>
            <Image
              source={require('../../../assets/imgs/profile.png')}
              style={styles.profilePic}
            />
            <View style={styles.nameContainer}>
              <Text style={styles.wholesalerName}>{currentUser.name}</Text>
            </View>
          </View>
          <View style={styles.profileRight}>
            <InfoRow label="Date Joined:" value={currentUser.signupDate} />
            <InfoRow label="Location:" value={userAddress.city} />
            <InfoRow label="Currency:" value={currentUser.currency} />
            <InfoRow label="UEN:" value={currentUser.uen} />
            <View style={styles.verifiedBadge}>
              <Text style={styles.verifiedText}>Verified</Text>
            </View>
          </View>
        </View>

        {/* Updated Ratings Section */}
        <View style={styles.ratingsSection}>
          <View style={styles.ratingsHeader}>
            <Text style={styles.ratingsTitle}>Ratings</Text>
            <Image source={require('../../../assets/imgs/pea.png')} style={styles.peaIcon} />
          </View>
          <View style={styles.ratingsDetails}>
            <Text style={styles.ratingsAverage}>{rating.toFixed(2)} Average Rating</Text>
          </View>
          <View style={styles.ratingBreakdown}>
            <RatingBar label="5" value={`${(numRatings[4]/totalRatings)*100}%`} />
            <RatingBar label="4" value={`${(numRatings[3]/totalRatings)*100}%`} />
            <RatingBar label="3" value={`${(numRatings[2]/totalRatings)*100}%`} />
            <RatingBar label="2" value={`${(numRatings[1]/totalRatings)*100}%`} />
            <RatingBar label="1" value={`${(numRatings[0]/totalRatings)*100}%`} />
          </View>
        </View>
        {/* Account Details */}
        <View style={styles.accountDetails}>
          <View style={styles.accountHeader}>
            <Text style={styles.accountTitle}>Account Details</Text>
            <TouchableOpacity onPress={() => navigation.navigate('ProfileEdit')}>
              <Ionicons name="create-outline" size={24} color="#0C5E52" />
            </TouchableOpacity>
          </View>
          <View style={styles.accountInfo}>
            <View style={styles.accountColumn}>
              <InfoRow label="Email:" value={currentUser.email} />
              <InfoRow label="Contact:" value={currentUser.phone_number} />
            </View>
            <View style={styles.accountColumn}>
              <InfoRow 
                label="Address:" 
                value={`${userAddress.street_name}\n${userAddress.unit_no}\n${userAddress.building_name}\n${userAddress.postal_code}`}
                multiline
              />
            </View>
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const RatingBar = ({ label, value }) => (
  <View style={styles.ratingRow}>
    <Text style={styles.ratingLabel}>{label}</Text>
    <View style={styles.ratingBarContainer}>
      <View style={[styles.ratingBar, { width: value }]} />
    </View>
  </View>
);

const InfoRow = ({ label, value, multiline }) => (
  <View style={styles.infoRow}>
    <Text style={styles.infoLabel}>{label}</Text>
    <Text style={[styles.infoValue, multiline && styles.multilineValue]}>{value}</Text>
  </View>
);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FFFFFF',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 16,
    backgroundColor: '#0C5E5240',
  },
  headerLeft: {
    flexDirection: 'row',
    alignItems: 'center',
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
  logoutButton: {
    padding: 8,
  },
  profileCard: {
    flexDirection: 'row',
    backgroundColor: '#0C5E5240',
    padding: 16,
    paddingBottom: 23,
  },
  profileLeft: {
    flex: 1,
    alignItems: 'center',
    marginLeft: "10%",
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
  wholesalerName: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  profileRight: {
    flex: 1,
    justifyContent: 'center',
    marginLeft: "15%",
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
  ratingsSection: {
    backgroundColor: '#0C5E5220',
    padding: 16,
    margin: 16,
    borderRadius: 8,
  },
  ratingsHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 8,
  },
  ratingsTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginRight: 8,
  },
  ratingsDetails: {
    alignItems: 'center',
  },
  ratingsAverage: {
    fontWeight: 'bold',
    fontSize: 18,
    color: '#0C5E52',
  },
  reviewsCount: {
    fontSize: 14,
    color: '#333',
  },
  ratingBreakdown: {
    marginTop: 8,
  },
  ratingRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 4,
  },
  ratingLabel: {
    width: 24,
    textAlign: 'center',
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  ratingBarContainer: {
    height: 10,
    backgroundColor: '#94BEB8',
    flex: 1,
    borderRadius: 5,
    marginLeft: 8,
    marginRight: 8,
  },
  ratingBar: {
    height: 10,
    backgroundColor: '#0C5E52',
    borderRadius: 5,
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
    gap: "50%",
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
  logOutButton: {
    backgroundColor: '#EBF3D1',
    margin: 16,
    marginTop: 8,
    borderRadius: 8,
    padding: 16,
    alignItems: 'center',
  },
  logOutButtonText: {
    fontWeight: 'bold',
    fontSize: 18,
    color: '#0C5E52',
  },
});

export default Profile;