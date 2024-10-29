import React from "react";
import { StyleSheet, Text, View, Image, TouchableOpacity } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { useCart } from "../../lib/userCart";
import { useUserStore } from "../../lib/userStore";

const CartItem = ({ item, wholesalerUEN }) => {
  const { fetchCart, updateItemQuantity, removeItem } = useCart();
  const { userUid } = useUserStore();

  const handleIncrease = async () => {
    try {
      const quantityData = {
        "swp_id": item.swp_id,
        "price": item.price,
        "quantity": item.quantity + 1,
        "uen": wholesalerUEN,
      };
      await updateItemQuantity(userUid, quantityData, item.name, item.quantity + 1);
    } catch (error) {
      // Handle error 
      console.error(error);
    }
  };

  const handleDecrease = async () => {
    if (item.quantity > 1) {
      try {
        const quantityData = {
          "swp_id": item.swp_id,
          "price": item.price * -1,
          "quantity": item.quantity - 1,
          "uen": wholesalerUEN,
        };
        await updateItemQuantity(userUid, quantityData, item.name, item.quantity - 1);
      } catch (error) {
        // Handle error
        console.error(error);
      }
    }
  };

  const handleRemove = async () => {
    try {
      const deleteData = {
        "swp_id": item.swp_id,
        "uen": wholesalerUEN,
      };
      await removeItem(userUid, deleteData, item.name);
      await fetchCart(userUid);
    } catch (error) {
      // Handle error
    }
  };

  return (
    <View style={styles.container}>
      <Image
        source={
          item.image_url
            ? { uri: item.image_url }
            : require("../../../assets/imgs/DummyImage.jpg")
        }
        style={styles.image}
      />
      <View style={styles.itemDetails}>
        <Text style={styles.itemName}>{item.name}</Text>
        <Text style={styles.itemQuantity}>{item.quantity} Packet</Text>
        <Text style={styles.itemPrice}>${item.price.toFixed(2)}</Text>
      </View>
      <View style={styles.quantityControl}>
        <TouchableOpacity
          style={[
            styles.quantityButton,
            item.quantity <= 1 && styles.quantityButtonDisabled,
          ]}
          onPress={handleDecrease}
          disabled={item.quantity <= 1}
        >
          <Text style={styles.quantityButtonText}>-</Text>
        </TouchableOpacity>
        <Text style={styles.quantity}>{item.quantity}</Text>
        <TouchableOpacity
          style={styles.quantityButton}
          onPress={handleIncrease}
        >
          <Text style={styles.quantityButtonText}>+</Text>
        </TouchableOpacity>
      </View>
      <TouchableOpacity style={styles.deleteButton} onPress={handleRemove}>
        <Ionicons name="trash-outline" size={24} color="gray" />
      </TouchableOpacity>
    </View>
  );
};
const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    alignItems: "center",
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: "#e0e0e0",
  },
  image: {
    width: 60,
    height: 60,
    marginRight: 10,
  },
  itemDetails: {
    flex: 1,
  },
  itemName: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#0C5E52",
  },
  itemQuantity: {
    color: "gray",
  },
  itemPrice: {
    fontWeight: "bold",
    color: "#0C5E52",
  },
  quantityControl: {
    flexDirection: "row",
    alignItems: "center",
  },
  quantityButton: {
    width: 30,
    height: 30,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#e0e0e0",
    borderRadius: 15,
  },
  quantityButtonText: {
    fontSize: 18,
    fontWeight: "bold",
  },
  quantityButtonDisabled: {
    opacity: 0.5,
  },
  quantity: {
    marginHorizontal: 10,
    fontSize: 16,
  },
  deleteButton: {
    marginLeft: 10,
  },
});

export default CartItem;
