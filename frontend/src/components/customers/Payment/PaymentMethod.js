import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';
import { useUserStore } from '../../../lib/userStore';
import { Divider } from 'react-native-paper';

const PaymentMethod = () => {
  const navigation = useNavigation();
  const { paymentDetails } = useUserStore();

  const handleAddCard = () => {
    navigation.navigate('AddCard');
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Ionicons name="arrow-back" size={24} color="#0C5E52" />
        </TouchableOpacity>
        <View style={styles.headerTitleContainer}>
          <Text style={styles.headerTitle}>Payment Method</Text>
        </View>
      </View>
      <ScrollView>
        <Divider />
        <Text style={styles.sectionTitle}>Existing payment methods</Text>
        {paymentDetails && (
          <TouchableOpacity style={styles.cardItem}>
            <View style={styles.cardIcon}>
              <Ionicons name="card" size={24} color="#0C5E52" />
            </View>
            <View style={styles.cardDetails}>
              <Text style={styles.cardType}>
                {paymentDetails.card_no.toString().startsWith('4') ? 'Visa' : 'Mastercard'}
              </Text>
              <Text style={styles.cardNumber}>Card *{paymentDetails.card_no.toString().slice(-4)}</Text>
            </View>
            <Ionicons name="radio-button-on" size={24} color="#0C5E52" />
          </TouchableOpacity>
        )}
        <Text style={styles.sectionTitle}>Add a new credit/debit card</Text>
        <TouchableOpacity style={styles.addCardButton} onPress={handleAddCard}>
          <Ionicons name="add-circle-outline" size={24} color="#0C5E52" />
          <Text style={styles.addCardText}>Add Card</Text>
        </TouchableOpacity>
      </ScrollView>
      <TouchableOpacity style={styles.addButton} onPress={handleAddCard}>
        <Text style={styles.addButtonText}>Add</Text>
        <Ionicons name="arrow-forward" size={24} color="white" />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    paddingTop: "5%",
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 15,
    backgroundColor: 'white',
    marginBottom: 10,
    marginTop: 60,
    marginHorizontal: 10,
    borderRadius: 10,
  },
  headerTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginLeft: 10,
  },
  backButton: {
    margin: 16,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginLeft: 16,
    marginTop: 16,
    marginBottom: 8,
  },
  cardItem: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'white',
    padding: 16,
    marginHorizontal: 16,
    borderRadius: 8,
  },
  cardIcon: {
    marginRight: 16,
  },
  cardDetails: {
    flex: 1,
  },
  cardType: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  cardNumber: {
    fontSize: 14,
    color: '#666',
  },
  addCardButton: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'white',
    padding: 16,
    marginHorizontal: 16,
    borderRadius: 8,
  },
  addCardText: {
    marginLeft: 16,
    fontSize: 16,
    color: '#0C5E52',
  },
  addButton: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#0C5E52',
    padding: 16,
    margin: 16,
    borderRadius: 10,
  },
  addButtonText: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'white',
    marginRight: 8,
  },
});

export default PaymentMethod;