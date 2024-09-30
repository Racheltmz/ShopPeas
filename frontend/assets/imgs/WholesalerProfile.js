import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { SafeAreaView } from 'react-native-safe-area-context';

const WholesalerProfile = () => {
  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity style={styles.backButton}>
          <Ionicons name="arrow-back" size={24} color="#0C5E52" />
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Profile</Text>
        <Image source={require('./assets/pea-icon.png')} style={styles.peaIcon} />
      </View>

      <View style={styles.profileCard}>
        <Image
          source={require('./assets/profile-pic.png')}
          style={styles.profilePic}
        />
        <Text style={styles.name}>Happy Wholesaler</Text>
        <View style={styles.infoSection}>
          <View style={styles.infoRow}>
            <Ionicons name="calendar-outline" size={20} color="#0C5E52" />
            <Text style={styles.infoText}>Date Joined: 25-07-2024</Text>
          </View>
          <View style={styles.infoRow}>
            <Ionicons name="location-outline" size={20} color="#0C5E52" />
            <Text style={styles.infoText}>Location: Singapore</Text>
          </View>
          <View style={styles.infoRow}>
            <Ionicons name="cash-outline" size={20} color="#0C5E52" />
            <Text style={styles.infoText}>Currency: SGD</Text>
          </View>
          <View style={styles.infoRow}>
            <Ionicons name="business-outline" size={20} color="#0C5E52" />
            <Text style={styles.infoText}>UEN: 123456789</Text>
          </View>
        </View>
        <View style={styles.verifiedBadge}>
          <Text style={styles.verifiedText}>Verified</Text>
        </View>
      </View>

      <View style={styles.accountDetails}>
        <View style={styles.accountHeader}>
          <Text style={styles.accountTitle}>Account Details</Text>
          <TouchableOpacity>
            <Ionicons name="create-outline" size={24} color="#0C5E52" />
          </TouchableOpacity>
        </View>
        <View style={styles.contactInfo}>
          <Text style={styles.contactLabel}>Contact:</Text>
          <Text style={styles.contactValue}>+65 9863 3472</Text>
        </View>
        <View style={styles.cardInfo}>
          <Image source={require('./assets/mastercard-icon.png')} style={styles.cardIcon} />
          <Text style={styles.cardNumber}>*1234</Text>
        </View>
        <View style={styles.addressInfo}>
          <Text style={styles.addressLabel}>Address:</Text>
          <Text style={styles.addressValue}>
            123 Bishan Street 10{'\n'}
            #01-45{'\n'}
            Happy Building{'\n'}
            S23491
          </Text>
        </View>
        <View style={styles.emailInfo}>
          <Text style={styles.emailLabel}>Email:</Text>
          <Text style={styles.emailValue}>contact@happywholesaler.com</Text>
        </View>
      </View>

      <TouchableOpacity style={styles.reviewsButton}>
        <Text style={styles.reviewsButtonText}>Shop Reviews</Text>
        <Ionicons name="chevron-forward" size={24} color="white" />
      </TouchableOpacity>

      <TouchableOpacity style={styles.productsButton}>
        <Text style={styles.productsButtonText}>My Products</Text>
        <Ionicons name="chevron-forward" size={24} color="#0C5E52" />
      </TouchableOpacity>

      <View style={styles.bottomNav}>
        <TouchableOpacity style={styles.navItem}>
          <Ionicons name="home-outline" size={24} color="#0C5E52" />
          <Text style={styles.navText}>Home</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.navItem}>
          <Ionicons name="notifications-outline" size={24} color="#0C5E52" />
          <Text style={styles.navText}>Updates</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.navItem}>
          <View style={styles.sellButton}>
            <Ionicons name="add" size={24} color="white" />
          </View>
          <Text style={styles.navText}>Sell</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.navItem}>
          <Ionicons name="receipt-outline" size={24} color="#0C5E52" />
          <Text style={styles.navText}>Transactions</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.navItem}>
          <Ionicons name="person" size={24} color="#0C5E52" />
          <Text style={[styles.navText, styles.activeNavText]}>Profile</Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
};

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
    flex: 1,
  },
  peaIcon: {
    width: 24,
    height: 24,
  },
  profileCard: {
    backgroundColor: 'white',
    margin: 16,
    borderRadius: 8,
    padding: 16,
    alignItems: 'center',
  },
  profilePic: {
    width: 80,
    height: 80,
    borderRadius: 40,
    marginBottom: 8,
  },
  name: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginBottom: 16,
  },
  infoSection: {
    width: '100%',
  },
  infoRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 8,
  },
  infoText: {
    marginLeft: 8,
    color: '#333',
  },
  verifiedBadge: {
    backgroundColor: '#A8E0A4',
    paddingHorizontal: 12,
    paddingVertical: 4,
    borderRadius: 16,
    marginTop: 8,
  },
  verifiedText: {
    color: '#0C5E52',
    fontWeight: 'bold',
  },
  accountDetails: {
    backgroundColor: 'white',
    margin: 16,
    borderRadius: 8,
    padding: 16,
  },
  accountHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 16,
  },
  accountTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  contactInfo: {
    flexDirection: 'row',
    marginBottom: 8,
  },
  contactLabel: {
    fontWeight: 'bold',
    color: '#333',
    marginRight: 8,
  },
  contactValue: {
    color: '#333',
  },
  cardInfo: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 8,
  },
  cardIcon: {
    width: 32,
    height: 20,
    marginRight: 8,
  },
  cardNumber: {
    color: '#333',
  },
  addressInfo: {
    marginBottom: 8,
  },
  addressLabel: {
    fontWeight: 'bold',
    color: '#333',
    marginBottom: 4,
  },
  addressValue: {
    color: '#333',
  },
  emailInfo: {
    flexDirection: 'row',
  },
  emailLabel: {
    fontWeight: 'bold',
    color: '#333',
    marginRight: 8,
  },
  emailValue: {
    color: '#333',
  },
  reviewsButton: {
    backgroundColor: '#0C5E52',
    margin: 16,
    borderRadius: 8,
    padding: 16,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  reviewsButtonText: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 16,
  },
  productsButton: {
    backgroundColor: '#EBF3D1',
    margin: 16,
    borderRadius: 8,
    padding: 16,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  productsButtonText: {
    color: '#0C5E52',
    fontWeight: 'bold',
    fontSize: 16,
  },
  bottomNav: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center',
    backgroundColor: 'white',
    paddingVertical: 8,
    borderTopWidth: 1,
    borderTopColor: '#E0E0E0',
  },
  navItem: {
    alignItems: 'center',
  },
  navText: {
    fontSize: 12,
    color: '#0C5E52',
    marginTop: 4,
  },
  activeNavText: {
    fontWeight: 'bold',
  },
  sellButton: {
    backgroundColor: '#FF9F67',
    width: 48,
    height: 48,
    borderRadius: 24,
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default WholesalerProfile;