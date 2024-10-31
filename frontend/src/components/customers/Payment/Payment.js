import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView, Image } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';
import { useUserStore } from '../../../lib/userStore';
import { useCart } from '../../../lib/userCart';
import Loader from '../../utils/Loader';
import productService from '../../../service/ProductService';
import CustomAlert from '../../utils/Alert';

const Payment = () => {
  const navigation = useNavigation();
  const { userUid, currentUser } = useUserStore();
  const { cart, checkout, getTotal, fetchCart } = useCart();
  const [ loading, setLoading ] = useState(false);
  const [productImages, setProductImages] = useState({}); 
  const [alertVisible, setAlertVisible] = useState(false);
  const [customAlert, setCustomAlert] = useState({ title: '', message: '', onConfirm: () => { } });
  
  const showAlert = (title, message, onConfirm) => {
    setCustomAlert({ title, message, onConfirm });
    setAlertVisible(true);
  };

  // Add this function to fetch images
  const fetchProductImages = async () => {
    try {
      const imagePromises = cart.flatMap(wholesaler =>
        wholesaler.items.map(async (item) => {
          try {
            const imageData = await productService.fetchProductImage(userUid, item.name);
            return { 
              name: item.name, 
              image: imageData // If this is already the URL, don't try to access image_url
            };
          } catch (err) {
            console.error(`Error fetching image for ${item.name}:`, err);
            return { name: item.name, image: null };
          }
        })
      );
  
      const images = await Promise.all(imagePromises);
      const imageMap = images.reduce((acc, item) => {
        acc[item.name] = item.image; 
        return acc;
      }, {});
      setProductImages(imageMap);
    } catch (err) {
      console.error("Error fetching product images:", err);
    }
  };

  useEffect(() => {
    if (cart.length > 0) {
      fetchProductImages();
    }
  }, [cart]);
  const handlePaymentMethodPress = () => {
    navigation.navigate('PaymentMethod');
  };

  const handleMakePayment = async () => {
    try {
      setLoading(true); 
      await checkout(userUid);
      await fetchCart(userUid); 
      showAlert("Success!", "Payment Successful!");
    } catch (err) {
      console.error("Payment failed:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleWholesalerPress = (wholesalerName) => {
    navigation.navigate('ViewWholesaler', { wholesalerName });
  };

  return (
    <View style={styles.container}>
      {loading && <Loader loading={loading}></Loader>}
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
                <Image 
                source={
                  productImages[item.name] 
                    ? { uri: productImages[item.name] }
                    : require('../../../../assets/imgs/DummyImage.jpg')
                } 
                style={styles.itemImage}
              />
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
      <CustomAlert
          visible={alertVisible}
          title={customAlert.title}
          message={customAlert.message}
          onConfirm={() => {
          setAlertVisible(false);
          customAlert.onConfirm;
          navigation.navigate('History', { refresh: true }); 
          }}
      />
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