import React, { useEffect, useState } from "react";
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
import { useUserStore } from "../../lib/userStore";
import { Divider } from 'react-native-paper';
import MapView, { Marker } from 'react-native-maps';
import ProductDetailsHeader from "../../components/customers/ProductDetailsHeader";
import productService from "../../service/ProductService";

const ProductDetails = ({ route }) => {
  const { product } = route.params;
  const { userUid } = useUserStore();
  const [wholesalerInfo, setWholesalerInfo] = useState([]);
  const [sortBy, setSortBy] = useState("price");
  const [showModal, setShowModal] = useState(false);
  const [selectedWholesaler, setSelectedWholesaler] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const { addItem } = useCart();
  const navigation = useNavigation();

  useEffect(() => {
    productService.getDetailsByPID(userUid, product.pid)
      .then((res) => {
        let data = []
        for (let i = 0; i < res.length; i++) {
          let record = {
            'name': res[i].name,
            'package_size': res[i].package_size,
            'location': res[i].location,
            'timeAway': 39, // TODO
            'stocks': res[i].stock,
            'price': res[i].price,
            'ratings': res[i].ratings.toFixed(1),
            'uen': res[i].uen
          }
          data.push(record);
        }
        setWholesalerInfo(data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [product.pid, userUid]);

  const handleWholesalerPress = () => {
    setShowModal(false);
    navigation.navigate("ViewWholesaler", {
      wholesalerUEN: selectedWholesaler.uen,
    });
  };

  const sortedWholesalers = [...wholesalerInfo].sort((a, b) => {
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
      <View style={styles.detailsContainer}>
        <View style={styles.row}>
          <View>
            <View style={styles.wholesalerTopSection}>
              <Text style={styles.wholesalerNameHeader}>{item.name}</Text>
              <View style={styles.priceRating}>
                <Text>{item.ratings} ⭐</Text>
              </View>
            </View>
            <Text style={styles.wholesalerLocation}>
              {item.location}, {item.timeAway} Minutes away
            </Text>
          </View>

        </View>
        <Divider style={styles.divider} />
        <View style={styles.row}>
          <View>
            <Text style={styles.wholesalerStocks}>Stocks: {item.stocks}</Text>
          </View>
          <View style={styles.priceRating}>
            <Text style={styles.price}>${item.price.toFixed(2)}</Text>
          </View>
        </View>
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
      <ProductDetailsHeader name={product.name} desc={`${product.package_size}`} navigation={navigation} />
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
              <Text style={styles.modalText}>{product.package_size}</Text>
              <TouchableOpacity onPress={handleWholesalerPress}>
                <Text style={styles.wholesalerName}>
                  {selectedWholesaler?.name}
                  <Ionicons name="chevron-forward-outline" size={16} color="#0C5E52" />
                </Text>
              </TouchableOpacity>
              <Text style={styles.wholesalerLocation}>
                {selectedWholesaler?.location}, {selectedWholesaler?.timeAway}{" "}
                Minutes away
              </Text>
              <Text style={styles.wholesalerStocks}>Stocks: {selectedWholesaler?.stocks}</Text>
              <View style={styles.wholesalerMap}>
                <MapView style={styles.map}>
                  <Marker coordinate={{latitude: 1.32044079120154, longitude: 103.843825618748}} />
                </MapView>
              </View>
              <View style={styles.quantityContainer}>
                <Text style={styles.wholesalerQty}>Quantity</Text>
                <View style={styles.row}>
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
  row: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
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
  detailsContainer: {
    width: "100%",
  },
  divider: {
    marginVertical: 5,
    height: 1,
    backgroundColor: '#0C5E5250',
  },
  wholesalerTopSection: {
    width: '100%',
    flexDirection: "row",
    justifyContent: "space-between",
  },
  wholesalerItem: {
    flexDirection: "row",
    justifyContent: "space-between",
    backgroundColor: "#D6E8A490",
    padding: 15,
    marginVertical: 5,
    marginHorizontal: 10,
    borderRadius: 10,
  },
  wholesalerNameHeader: {
    fontWeight: "bold",
    fontSize: 16,
    color: '#0C5E52',
    marginBottom: 2,
    width: "80%",
  },
  wholesalerName: {
    fontWeight: "bold",
    fontSize: 16,
    color: '#0C5E52',
    marginBottom: 2,
  },
  wholesalerLocation: {
    color: "#0C5E52",
    marginBottom: 2,
  },
  wholesalerStocks: {
    color: "#0C5E52",
    marginBottom: 2,
  },
  wholesalerQty: {
    fontWeight: 'bold',
    fontSize: 18,
    color: "#0C5E52",
    marginBottom: 2,
  },
  price: {
    fontSize: 14,
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
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 5,
    color: '#0C5E52',
  },
  modalText: {
    color: '#0C5E52',
    marginBottom: 20,
  },
  quantityContainer: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
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
    backgroundColor: "#D6E8A4",
    padding: 15,
    borderRadius: 10,
    alignItems: "center",
    marginVertical: 20,
  },
  addToCartButtonText: {
    color: "#0C5E52",
    fontSize: 20,
    fontWeight: "bold",
  },
  wholesalerMap: {
    width: "100%",
    marginTop: 10,
    marginBottom: 10,
    borderRadius: 10,
  },
  map: {
    width: '100%',
    height: '80%',
    paddingBottom: 0,
  },
});

export default ProductDetails;
