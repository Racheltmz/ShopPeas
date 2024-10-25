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
  console.log(paymentDetails);

  const handlePaymentMethodPress = () => {
    navigation.navigate('PaymentMethod');
  };

  const handleMakePayment = () => {
    // Implement payment logic here
    console.log('Processing payment...');
  };

  const handleWholesalerPress = (wholesalerName) => {
    navigation.navigate('ViewWholesaler', { wholesalerName });
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity style={styles.backButton} onPress={() => navigation.goBack()}>
          <Ionicons name="arrow-back" size={24} color="#0C5E52" />
        </TouchableOpacity>
        <View>
          <Text style={styles.headerTitle}>Payment</Text>
          <View style={styles.userInfo}>
            <Text style={styles.userName}>{currentUser?.name}</Text>
            <Text style={styles.userEmail}>{currentUser?.email}</Text>
            <Text style={styles.userPhone}>{currentUser?.phone}</Text>
          </View>
        </View>
      </View>
      <ScrollView>
        <TouchableOpacity style={styles.paymentMethod} onPress={handlePaymentMethodPress}>
          <Text style={styles.paymentMethodText}>Payment Method</Text>
          <View style={styles.cardInfo}>
            <Ionicons name="card" size={24} color="#0C5E52" />
            {/* <Text style={styles.cardNumber}>Card *{paymentDetails?.card_no.toString().slice(-4)}</Text> */}
            <Ionicons name="chevron-forward" size={24} color="#0C5E52" />
          </View>
        </TouchableOpacity>
        {cart.map((wholesaler, index) => (
          <View key={index} style={styles.wholesalerContainer}>
            <TouchableOpacity onPress={() => handleWholesalerPress(wholesaler.wholesaler)}>
              <Text style={styles.wholesalerName}>{wholesaler.wholesaler} <Ionicons name="chevron-forward" size={14} color="#0C5E52" /></Text>
            </TouchableOpacity>
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
    flexDirection: 'row',
    fontSize: 24,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  backButton: {
    marginRight: 16,
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
    marginHorizontal: 10,
    borderRadius: 8,
    marginBottom: 16,
  },
  paymentMethodText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  cardInfo: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  cardNumber: {
    marginHorizontal: 8,
    color: '#666',
  },
  wholesalerContainer: {
    backgroundColor: 'white',
    padding: 16,
    marginHorizontal: 10,
    borderRadius: 8,
    marginBottom: 16,
  },
  wholesalerName: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginBottom: 5,
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
    fontWeight: 'bold',
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
    marginBottom: 10,
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