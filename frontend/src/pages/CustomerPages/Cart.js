import React, { useCallback, useState } from "react";
import {
  StyleSheet,
  Text,
  View,
  ScrollView,
  TouchableOpacity,
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { useCart } from "../../lib/userCart";
import CartItem from "../../components/customers/CartItem";
import Empty from '../../components/utils/Empty';
import { useUserStore } from "../../lib/userStore";
import CustomAlert from "../../components/utils/Alert";

const Cart = ({ navigation }) => {
  const { cart, clearCart, getTotal, fetchCart } = useCart();
  const { userUid } = useUserStore();
  const [alertVisible, setAlertVisible] = useState(false);
  const [customAlert, setCustomAlert] = useState({ title: '', message: '', onConfirm: () => { } });

  const showAlert = (title, message, onConfirm) => {
      setCustomAlert({ title, message, onConfirm });
      setAlertVisible(true);
  };

  const handleCheckout = async () => {
    if (cart.length == 0) {
      showAlert("Error", "Cart is empty!");
    }
    else {
      navigation.navigate("Payment");
    }
  };

  const handleWholesalerPress = (uen) => {
    navigation.navigate("ViewWholesaler", { wholesalerUEN: uen });
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

      {cart.length === 0 ? (
        <Empty subject="Cart Items" />
      ) : (
        <ScrollView style={styles.container}>
          {cart.map((wholesaler, index) => {
            const formattedLocation = wholesaler.location
              ? `${wholesaler.location.street_name}${wholesaler.location.unit_no
                ? `, ${wholesaler.location.unit_no}`
                : ""
              }`
              : "";

            return (
              <View key={index} style={styles.wholesalerSection}>
                <TouchableOpacity
                  onPress={() => handleWholesalerPress(wholesaler.uen)}
                >
                  <Text style={styles.wholesalerName}>
                    {wholesaler.wholesaler}{" "}
                    <Ionicons name="chevron-forward" size={14} color="#0C5E52" />
                  </Text>
                </TouchableOpacity>
                <Text>{formattedLocation}</Text>
                {wholesaler.items.map((item, itemIndex) => (
                  <CartItem
                    key={itemIndex}
                    item={item}
                    wholesalerUEN={wholesaler.uen}
                  />
                ))}
              </View>
            );
          })}
        </ScrollView>
      )}

      <View style={styles.footer}>
        <Text style={styles.totalPrice}>Total ${totalPrice.toFixed(2)}</Text>
        <TouchableOpacity
          style={styles.checkoutButton}
          onPress={handleCheckout}
        >
          <Text style={styles.checkoutButtonText}>Check Out</Text>
        </TouchableOpacity>
      </View>
      <CustomAlert
          visible={alertVisible}
          title={customAlert.title}
          message={customAlert.message}
          onConfirm={() => {
          setAlertVisible(false);
          customAlert.onConfirm;
          }}
      />
    </View >
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  loadingSpinner: {},
  header: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    padding: 15,
    backgroundColor: "white",
    marginBottom: 10,
    marginTop: 60,
    marginHorizontal: 10,
    borderRadius: 10,
  },
  headerTitle: {
    fontSize: 24,
    fontWeight: "bold",
    color: "#0C5E52",
  },
  cartList: {
    flex: 1,
  },
  wholesalerSection: {
    backgroundColor: "white",
    margin: 10,
    padding: 15,
    borderRadius: 10,
  },
  wholesalerName: {
    fontSize: 18,
    fontWeight: "bold",
    color: "#0C5E52",
  },
  footer: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    padding: 15,
    backgroundColor: "rgba(12, 94, 82, 0.8)",
  },
  totalPrice: {
    fontSize: 24,
    fontWeight: "bold",
    color: "white",
  },
  checkoutButton: {
    backgroundColor: "#B5D75F",
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 5,
  },
  checkoutButtonText: {
    color: "#0C5E52",
    fontSize: 16,
    fontWeight: "bold",
  },
});

export default Cart;
