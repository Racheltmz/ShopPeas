import React from 'react';
import { StyleSheet, Text, View, ScrollView, TouchableOpacity } from 'react-native';
import { useCart } from '../../lib/userCart';
import { Ionicons } from '@expo/vector-icons'; 
import CartItem from '../../components/customers/CartItem';

const Cart = ({ navigation }) => {
  const { cart, clearCart, getTotal } = useCart();

  // const handleClearCart = () => {
  //   clearCart();
  // };

  const handleCheckout = () => {
    navigation.navigate('Payment');
  }

  const handleWholesalerPress = (wholesalerName) => {
    navigation.navigate('ViewWholesaler', { wholesalerName });
  };

  const totalPrice = getTotal();

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Ionicons name="arrow-back" size={24} color="#0C5E52" />
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Shopping Cart</Text>
        <Ionicons name="cart-outline" size={24} color="#0C5E52" />
      </View>
      <ScrollView style={styles.cartList}>
        {cart.map((wholesaler, index) => (
          <View key={index} style={styles.wholesalerSection}>
            <TouchableOpacity onPress={() => handleWholesalerPress(wholesaler.wholesaler)}>
              <Text style={styles.wholesalerName}>{wholesaler.wholesaler} <Ionicons name="chevron-forward" size={14} color="#0C5E52" /></Text>
            </TouchableOpacity>
            <Text style={styles.wholesalerLocation}>
              {wholesaler.location}, {wholesaler.distance} Minutes away
            </Text>
            {wholesaler.items.map((item, itemIndex) => (
              <CartItem key={itemIndex} item={item} wholesalerName = {wholesaler.wholesaler}/>
            ))}
          </View>
        ))}
      </ScrollView>
      <View style={styles.footer}>
        <Text style={styles.totalPrice}>Total ${totalPrice.toFixed(2)}</Text>
        <TouchableOpacity style={styles.checkoutButton} onPress={handleCheckout}>
          <Text style={styles.checkoutButtonText}>Check Out</Text>
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
    justifyContent: 'space-between',
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
  },
  cartList: {
    flex: 1,
  },
  wholesalerSection: {
    backgroundColor: 'white',
    margin: 10,
    padding: 15,
    borderRadius: 10,
  },
  wholesalerName: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  wholesalerLocation: {
    color: 'gray',
    marginBottom: 10,
  },
  footer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 15,
    backgroundColor: 'rgba(12, 94, 82, 0.8)',
  },
  totalPrice: {
    fontSize: 24,
    fontWeight: 'bold',
    color: 'white',
  },
  checkoutButton: {
    backgroundColor: '#B5D75F',
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 5,
  },
  checkoutButtonText: {
    color: '#0C5E52',
    fontSize: 16,
    fontWeight: 'bold',
  },
});

export default Cart;