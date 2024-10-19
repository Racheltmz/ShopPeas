import React, { useState } from "react";
import {
  StyleSheet,
  Text,
  View,
  FlatList,
  TouchableOpacity,
  Image,
} from "react-native";
import { Ionicons } from '@expo/vector-icons';
import RatingModal from "./RatingModal";

const HistoryItems = ({ navigation, historyList }) => {
  const [modalVisible, setModalVisible] = useState(false);
  const [selectedWholesaler, setSelectedWholesaler] = useState(null);

  const handleWholesalerPress = (wholesalerName) => {
    navigation.navigate('ViewWholesaler', { wholesalerName });
  };

  const renderItem = ({ item }) => (
    <View style={styles.orderItem}>
      <Text style={styles.orderDate}>Order place on: {item.orderDate}</Text>
      {item.purchasedItemsViaWholesaler.map((wholesaler, index) => (
        <View key={index} style={styles.wholesalerSection}>
          <View style={styles.ratingWholesaler}>
            <TouchableOpacity onPress={() => handleWholesalerPress(wholesaler.wholesalerName)}>
              <Text style={styles.wholesalerName}>📍{wholesaler.wholesalerName} <Ionicons name="chevron-forward" size={14} color="#0C5E52" /></Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.ratingButton}
              onPress={() => {
                setSelectedWholesaler(wholesaler);
                setModalVisible(true);
              }}
            >
              <Text style={styles.ratingButtonText}>Give Rating</Text>
            </TouchableOpacity>
          </View>

          {wholesaler.items.map((product, productIndex) => (
            <View key={productIndex} style={styles.productItem}>
              <View style={styles.productItemLeft}>
                <Image
                  source={require("../../../assets/imgs/DummyImage.jpg")}
                  style={styles.productItemImage}
                />
                <View style={styles.productItemName}>
                  <Text style={{ color: "#0C5E52", fontWeight: "700" }}>{product.name}</Text>
                  <Text style={{ color: "#0C5E52" }}>
                    {product.Quantity}{product.Measurement}{" "}
                  </Text>
                </View>
              </View>
              <View style={styles.productItemQuantity}>
                <Text style={{ color: "#0C5E52" }}>x {product.Quantity}</Text>
                <Text style={{ color: "#0C5E52", fontWeight: "700" }}>${product.price.toFixed(2)}</Text>
              </View>
            </View>
          ))}
        </View>
      ))}
      <Text style={styles.orderStatus}>Status: {item.Status}</Text>
      <View style={styles.orderItemBottom}>
        <View style={styles.orderTotalContainer}>
          <Text style={styles.totalPurchase}>
            Total Purchase:
          </Text>
          <Text style={styles.orderTotal}>
            ${item.TotalPrice.toFixed(2)}
          </Text>
        </View>
      </View>
    </View>
  );

  return (
    <>
      <FlatList
        data={historyList}
        renderItem={renderItem}
        keyExtractor={(item, index) => index.toString()}
      />
      <RatingModal
        visible={modalVisible}
        onClose={() => setModalVisible(false)}
        wholesaler={selectedWholesaler}
      />
    </>
  );
};

const styles = StyleSheet.create({
  orderItem: {
    backgroundColor: "white",
    padding: 15,
    margin: 10,
    borderRadius: 10,
  },

  orderDate: {
    fontSize: 15,
    fontWeight: "600",
    color: "#0C5E52",
  },

  wholesalerSection: {
    marginTop: 10,
  },

  ratingWholesaler: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    marginBottom: "3%",
  },

  wholesalerName: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#0C5E52",
  },

  ratingButton: {
    backgroundColor: "#B5D75F",
    paddingHorizontal: "5%",
    paddingVertical: "1%",
    borderRadius: 5,
    marginTop: 10,
    alignItems: "center",
  },

  ratingButtonText: {
    fontSize: 14,
    color: "#0C5E52",
    fontWeight: "600",
  },

  productItem: {
    fontSize: 14,
    marginLeft: 10,
    flexDirection: "row",
    marginBottom: "5%",
    justifyContent: "space-between",
  },

  productItemLeft: {
    flexDirection: "row",
  },
  productItemImage: {
    height: 50,
    width: 50,
    marginRight: "7%",
  },
  productItemQuantity: {
    alignItems: "flex-end",
    justifyContent: "space-around",
  },
  orderItemBottom: {
    alignItems: "flex-end",
  },
  orderStatus: {
    fontSize: 16,
    color: "#0C5E52",
    fontWeight: "700",
  },
  orderTotalContainer: {
    flexDirection: "row",
    alignItems: "center",
  },
  totalPurchase: {
    fontSize: 16,
    fontWeight: "bold",
    marginTop: 5,
    color: "#0C5E52",
    marginRight: 5,
  },
  orderTotal: {
    fontSize: 20,
    fontWeight: "bold",
    marginTop: 5,
    color: "#66821F",
  },
});

export default HistoryItems;
