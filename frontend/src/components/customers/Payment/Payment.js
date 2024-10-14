import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView, Image } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';
import { useUserStore } from '../../../lib/userStore';
import { useCart } from '../../../lib/userCart';

const Payment = () => {
  const navigation = useNavigation();
  const { currentUser, paymentDetails } = useUserStore();
  const { cart, getTotal } = useCart();

  const handlePaymentMethodPress = () => {
    navigation.navigate('PaymentMethod');
  };

  const handleMakePayment = () => {
    // Implement payment logic here
    console.log('Processing payment...');
  };

  return (
    <View style={styles.container}>
      <ScrollView>
        <TouchableOpacity style={styles.backButton} onPress={() => navigation.goBack()}>
          <Ionicons name="arrow-back" size={24} color="#0C5E52" />
        </TouchableOpacity>
        <Text style={styles.title}>Payment</Text>
        <View style={styles.userInfo}>
          <Text style={styles.userName}>{currentUser?.name}</Text>
          <Text style={styles.userEmail}>{currentUser?.email}</Text>
          <Text style={styles.userPhone}>{currentUser?.phone}</Text>
        </View>
        <TouchableOpacity style={styles.paymentMethod} onPress={handlePaymentMethodPress}>
          <Text style={styles.paymentMethodText}>Payment Method</Text>
          <View style={styles.cardInfo}>
            <Ionicons name="card" size={24} color="#0C5E52" />
            <Text style={styles.cardNumber}>Card *{paymentDetails?.card_no.toString().slice(-4)}</Text>
          </View>
          <Ionicons name="chevron-forward" size={24} color="#0C5E52" />
        </TouchableOpacity>
        {cart.map((wholesaler, index) => (
          <View key={index} style={styles.wholesalerContainer}>
            <Text style={styles.wholesalerName}>{wholesaler.wholesaler}</Text>
            <Text style={styles.wholesalerLocation}>{wholesaler.location}, {wholesaler.distance} Minutes away</Text>
            {wholesaler.items.map((item, itemIndex) => (
              <View key={itemIndex} style={styles.itemContainer}>
                <Image source={require('../../../../assets/imgs/DummyImage.jpg')} style={styles.itemImage} />
                <View style={styles.itemDetails}>
                  <Text style={styles.itemName}>{item.name}</Text>
                  <Text style={styles.itemQuantity}>{item.quantity} {item.Measurement}</Text>
                </View>
                <View style={styles.itemPriceContainer}>
                  <Text style={styles.itemPrice}>${item.price.toFixed(2)}</Text>
                  <Text style={styles.itemQuantityPrice}>x{item.quantity}</Text>
                </View>
              </View>
            ))}
          </View>
        ))}
      </ScrollView>
      <View style={styles.footer}>
        <View style={styles.totalContainer}>
          <Text style={styles.totalText}>Total</Text>
          <Text style={styles.totalAmount}>${getTotal().toFixed(2)}</Text>
        </View>
        <TouchableOpacity style={styles.makePaymentButton} onPress={handleMakePayment}>
          <Text style={styles.makePaymentText}>Make Payment</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5F5F5',
    paddingTop: "5%",
  },
  backButton: {
    margin: 16,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginLeft: 16,
    marginBottom: 16,
  },
  userInfo: {
    backgroundColor: 'white',
    padding: 16,
    marginHorizontal: 16,
    borderRadius: 8,
    marginBottom: 16,
  },
  userName: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  userEmail: {
    fontSize: 14,
    color: '#666',
  },
  userPhone: {
    fontSize: 14,
    color: '#666',
  },
  paymentMethod: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: 'white',
    padding: 16,
    marginHorizontal: 16,
    borderRadius: 8,
    marginBottom: 16,
  },
  paymentMethodText: {
    fontSize: 16,
    color: '#0C5E52',
  },
  cardInfo: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  cardNumber: {
    marginLeft: 8,
    color: '#666',
  },
  wholesalerContainer: {
    backgroundColor: 'white',
    padding: 16,
    marginHorizontal: 16,
    borderRadius: 8,
    marginBottom: 16,
  },
  wholesalerName: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  wholesalerLocation: {
    fontSize: 14,
    color: '#666',
    marginBottom: 8,
  },
  itemContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 8,
  },
  itemImage: {
    width: 50,
    height: 50,
    borderRadius: 4,
  },
  itemDetails: {
    flex: 1,
    marginLeft: 12,
  },
  itemName: {
    fontSize: 16,
    color: '#0C5E52',
  },
  itemQuantity: {
    fontSize: 14,
    color: '#666',
  },
  itemPriceContainer: {
    alignItems: 'flex-end',
  },
  itemPrice: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  itemQuantityPrice: {
    fontSize: 14,
    color: '#666',
  },
  footer: {
    backgroundColor: '#0C5E52',
    padding: 16,
  },
  totalContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 16,
  },
  totalText: {
    fontSize: 18,
    color: 'white',
  },
  totalAmount: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'white',
  },
  makePaymentButton: {
    backgroundColor: '#B5D75F',
    padding: 16,
    borderRadius: 8,
    alignItems: 'center',
  },
  makePaymentText: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
});

export default Payment;