import React, { useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  Modal,
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { useCart } from "../../lib/userCart";
import { useNavigation } from "@react-navigation/native";
import ProductDetailsHeader from "../../components/customers/ProductDetailsHeader";

const ProductDetails = ({ route }) => {
  const { product } = route.params;
  const [sortBy, setSortBy] = useState("price");
  const [showModal, setShowModal] = useState(false);
  const [selectedWholesaler, setSelectedWholesaler] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const { addItem } = useCart();
  const navigation = useNavigation();

  const handleWholesalerPress = () => {
    setShowModal(false);
    navigation.navigate("ViewWholesaler", {
      wholesalerName: selectedWholesaler.name,
    });
  };

  const DUMMY_WHOLESALERS = [
    {
      name: "Happy Wholesaler",
      location: "Bishan",
      timeAway: 39,
      stocks: 39,
      price: 1.29,
      ratings: 4.9,
      uen: 123456,
    },
    {
      name: "Cheap Wholesaler",
      location: "Serangoon",
      timeAway: 47,
      stocks: 45,
      price: 1.27,
      ratings: 4.3,
      uen: 12342323,
    },
    {
      name: "Quality buy",
      location: "Bishan",
      timeAway: 39,
      stocks: 80,
      price: 1.51,
      ratings: 4.7,
      uen: 123452316,
    },
    {
      name: "Value Dollar",
      location: "Punggol",
      timeAway: 71,
      stocks: 15,
      price: 1.19,
      ratings: 4.5,
      uen: 123456213231,
    },
    {
      name: "Big Box",
      location: "Jurong East",
      timeAway: 41,
      stocks: 28,
      price: 1.49,
      ratings: 4.9,
      uen: 123451232316,
    },
  ];

  const sortedWholesalers = [...DUMMY_WHOLESALERS].sort((a, b) => {
    if (sortBy === "price") return a.price - b.price;
    if (sortBy === "duration") return a.timeAway - b.timeAway;
    if (sortBy === "stocks") return b.stocks - a.stocks;
    return 0;
  });

  const renderWholesalerItem = ({ item }) => (
    <TouchableOpacity
      style={styles.wholesalerItem}
      onPress={() => {
        setSelectedWholesaler(item);
        setShowModal(true);
      }}
    >
      <View>
        <Text style={styles.wholesalerName}>{item.name}</Text>
        <Text style={styles.wholesalerLocation}>
          {item.location}, {item.timeAway} Minutes away
        </Text>
        <Text>Stocks: {item.stocks}</Text>
      </View>
      <View style={styles.priceRating}>
        <Text style={styles.price}>${item.price.toFixed(2)}</Text>
        <Text>{item.ratings} ⭐</Text>
      </View>
    </TouchableOpacity>
  );

  const handleAddCartItem = () => {
    const wholesaler = {
      wholesaler: selectedWholesaler.name,
      location: selectedWholesaler.location,
      distance: selectedWholesaler.timeAway,
    };
    const item = { name: product.name, price: selectedWholesaler.price };
    addItem(wholesaler, item, quantity);
    setQuantity(1);
    setShowModal(false);
  };

  return (
    <View style={{ flex: 1 }}>
      <ProductDetailsHeader name={product.name} navigation={navigation} />
      <View style={styles.bodyContainer}>
        <View style={styles.sortContainer}>
          <Text>Sort By:</Text>
          <TouchableOpacity onPress={() => setSortBy("price")}>
            <Text
              style={
                sortBy === "price" ? styles.activeSortButton : styles.sortButton
              }
            >
              Price ▼
            </Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={() => setSortBy("duration")}>
            <Text
              style={
                sortBy === "duration"
                  ? styles.activeSortButton
                  : styles.sortButton
              }
            >
              Duration ▼
            </Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={() => setSortBy("stocks")}>
            <Text
              style={
                sortBy === "stocks"
                  ? styles.activeSortButton
                  : styles.sortButton
              }
            >
              Stocks ▼
            </Text>
          </TouchableOpacity>
        </View>

        <FlatList
          data={sortedWholesalers}
          renderItem={renderWholesalerItem}
          keyExtractor={(item) => item.name}
          style={styles.list}
        />

        <Modal visible={showModal} animationType="slide" transparent={true}>
          <View style={styles.modalContainer}>
            <View style={styles.modalContent}>
              <TouchableOpacity
                style={styles.closeButton}
                onPress={() => setShowModal(false)}
              >
                <Ionicons name="close" size={24} color="black" />
              </TouchableOpacity>
              <Text style={styles.modalTitle}>{product.name}</Text>
              <Text>{product.quantity} Packet</Text>
              <TouchableOpacity onPress={handleWholesalerPress}>
                <Text style={styles.wholesalerName}>
                  {selectedWholesaler?.name}
                </Text>
              </TouchableOpacity>
              <Text>
                {selectedWholesaler?.location}, {selectedWholesaler?.timeAway}{" "}
                Minutes away
              </Text>
              <Text>Stocks: {selectedWholesaler?.stocks}</Text>
              {/* <Image> </Image> */}
              <View style={styles.quantityContainer}>
                <TouchableOpacity
                  onPress={() => setQuantity(Math.max(1, quantity - 1))}
                >
                  <Text style={styles.quantityButton}>-</Text>
                </TouchableOpacity>
                <Text style={styles.quantity}>{quantity}</Text>
                <TouchableOpacity onPress={() => setQuantity(quantity + 1)}>
                  <Text style={styles.quantityButton}>+</Text>
                </TouchableOpacity>
              </View>
              <TouchableOpacity
                style={styles.addToCartButton}
                onPress={handleAddCartItem}
              >
                <Text style={styles.addToCartButtonText}>Add to Cart</Text>
              </TouchableOpacity>
            </View>
          </View>
        </Modal>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  bodyContainer: {
    flex: 1,
    backgroundColor: "#ffffff",
    borderRadius: 24,
    padding: 10,
  },
  sortContainer: {
    flexDirection: "row",
    justifyContent: "space-around",
    padding: 10,
    backgroundColor: "white",
    marginTop: 10,
  },
  sortButton: {
    color: "gray",
  },
  activeSortButton: {
    color: "green",
    fontWeight: "bold",
  },
  list: {
    flex: 1,
  },
  wholesalerItem: {
    flexDirection: "row",
    justifyContent: "space-between",
    backgroundColor: "#D6E8A4",
    padding: 15,
    marginVertical: 5,
    marginHorizontal: 10,
    borderRadius: 10,
  },
  wholesalerName: {
    fontWeight: "bold",
    fontSize: 16,
  },
  wholesalerLocation: {
    color: "gray",
  },
  price: {
    fontSize: 18,
    fontWeight: "bold",
    color: "green",
  },
  priceRating: {
    alignItems: "flex-end",
  },
  modalContainer: {
    flex: 1,
    justifyContent: "flex-end",
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  modalContent: {
    backgroundColor: "white",
    padding: 20,
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
  },
  closeButton: {
    alignSelf: "flex-end",
  },
  modalTitle: {
    fontSize: 20,
    fontWeight: "bold",
    marginBottom: 10,
  },
  quantityContainer: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "center",
    marginTop: 20,
  },
  quantityButton: {
    fontSize: 24,
    paddingHorizontal: 20,
  },
  quantity: {
    fontSize: 18,
    paddingHorizontal: 20,
  },
  addToCartButton: {
    backgroundColor: "green",
    padding: 15,
    borderRadius: 10,
    alignItems: "center",
    marginTop: 20,
  },
  addToCartButtonText: {
    color: "white",
    fontWeight: "bold",
  },
});

export default ProductDetails;
