import React, { useState } from "react";
import {
  StyleSheet,
  Text,
  View,
  FlatList,
  TouchableOpacity,
  Image,
} from "react-native";
import { Divider } from 'react-native-paper';
import { Ionicons } from '@expo/vector-icons';
import Empty from "../utils/Empty";
import RatingModal from "./RatingModal";

const HistoryItems = ({ navigation, historyList, onRatedItem }) => {
  const [modalVisible, setModalVisible] = useState(false);
  const [selectedWholesaler, setSelectedWholesaler] = useState(null);
  const [tid, setTid] = useState(null);

  const handleWholesalerPress = (uen) => {
    navigation.navigate('ViewWholesaler', { wholesalerUEN: uen, });
  };

  const userFriendlyDate = (inputDate) => {
    const date = new Date(inputDate);
    const options = { day: '2-digit', month: 'short', year: 'numeric' };
    return date.toLocaleDateString('en-GB', options);
  }

  const userFriendlyStatus = (status) => {
    return status
      .toLowerCase()
      .split('-')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');
  }

  const renderItem = ({ item }) => (
    <View key={item.oid} style={styles.orderItem}>
      <Text style={styles.orderDate}>Order placed on: {userFriendlyDate(item.date)}</Text>
      {item.orders.map((wholesaler, index) => (
        <View key={index} style={styles.wholesalerSection}>
          <View style={styles.ratingWholesaler}>
            <TouchableOpacity onPress={() => handleWholesalerPress(wholesaler.uen)}>
              <Text style={styles.wholesalerName}>📍{wholesaler.wholesalerName} <Ionicons name="chevron-forward" size={14} color="#0C5E52" /></Text>
            </TouchableOpacity>
          </View>
          {wholesaler.items.map((product, productIndex) => (
            <View key={productIndex} style={styles.productItem}>
              <View style={styles.productItemLeft}>
                <Image
                  source={{ uri: product.image_url }}
                  style={styles.productItemImage}
                />
                <View style={styles.productItemDetails}>
                  <Text style={{ color: "#0C5E52", fontWeight: "700" }}>{product.name}</Text>
                  <Text style={{ color: "#0C5E52" }}>{product.description}</Text>
                  <Text style={{ color: "#0C5E52" }}>Quantity: {product.quantity}</Text>
                  <Text style={{ color: "#0C5E52", fontWeight: "700" }}>${product.price.toFixed(2)}</Text>
                </View>
              </View>
            </View>
          ))}
          <Text style={styles.orderStatus}>Status: {userFriendlyStatus(wholesaler.status)}</Text>
          <View>
            <View style={styles.bottomRowContainer}>
              <View style={styles.orderTotalContainer}>
                <Text style={styles.totalPurchase}>
                  Total Purchase:
                </Text>
                <Text style={styles.orderTotal}>
                  ${wholesaler.total_price.toFixed(2)}
                </Text>
              </View>
              <TouchableOpacity
                disabled={wholesaler.rated}
                style={wholesaler.rated ? styles.ratingDisabled : styles.ratingButton}
                onPress={() => {
                  setSelectedWholesaler(wholesaler.uen);
                  setModalVisible(true);
                  setTid(wholesaler.tid);
                }}
              >
                <Text style={wholesaler.rated ? styles.ratingDisabledText : styles.ratingButtonText}>{wholesaler.rated === false ? "Give Rating" : "Rated"}</Text>
              </TouchableOpacity>
            </View>
          </View>
          <Divider />
        </View>
      ))}
    </View>
  );

  return (
    historyList.length > 0 ?
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
          tid={tid}
          onRated={onRatedItem}
        />
      </> :
      <Empty subject="Orders"></Empty>
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
    fontSize: 16,
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

  ratingDisabled: {
    backgroundColor: "#0C5E52",
    paddingHorizontal: "5%",
    paddingVertical: "1%",
    borderRadius: 5,
    marginTop: 10,
    alignItems: "center",
  },

  ratingDisabledText: {
    fontSize: 14,
    color: "#FFF",
    fontWeight: "600",
  },
  productItemDetails: {
    width: "75%",
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
    height: 75,
    width: 75,
    marginRight: 10,
  },
  productItemQuantity: {
    alignItems: "flex-end",
    justifyContent: "space-around",
  },
  orderStatus: {
    fontSize: 16,
    color: "#0C5E52",
    fontWeight: "700",
    marginBottom: 10,
  },
  bottomRowContainer: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    marginBottom: 10,
  },
  orderTotalContainer: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-end",
  },
  totalPurchase: {
    fontSize: 16,
    fontWeight: "bold",
    marginTop: 5,
    color: "#0C5E52",
  },
  orderTotal: {
    fontSize: 20,
    fontWeight: "bold",
    marginTop: 5,
    marginLeft: 5,
    color: "#66821F",
  },
});

export default HistoryItems;
