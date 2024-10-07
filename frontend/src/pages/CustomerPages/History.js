import React, { useState } from 'react';
import { StyleSheet, Text, View, FlatList, TouchableOpacity } from 'react-native';
import RatingModal from '../../components/customers/RatingModal';

const DUMMY_ITEMS = [
  {
    orderDate: "19/8/24",
    purchasedItemsViaWholesaler: [
      {
        wholesalerName: "Happy Wholesaler",
        items: [
          {
            name: "Bok Choy",
            Quantity: 5,
            Measurement: " Packet",
            price: 1.29,
          },
          {
            name: "Rolled Oats",
            Quantity: 2,
            Measurement: "kg",
            price: 7.29,
          },
        ],
      },
      {
        wholesalerName: "Cheap Wholesaler",
        items: [
          {
            name: "Tomatoes",
            Quantity: 7,
            Measurement: " Packet",
            price: 1.17,
          },
          {
            name: "Apples",
            Quantity: 5,
            Measurement: "Packet",
            price: 1.89,
          },
        ],
      },
    ],
    Status: "Collected",
    TotalPrice: 38.69,
  },
  {
    orderDate: "16/8/24",
    purchasedItemsViaWholesaler: [
      {
        wholesalerName: "Quality Buy",
        items: [
          {
            name: "Bok Choy",
            Quantity: 5,
            Measurement: " Packet",
            price: 1.19,
          },
          {
            name: "Rolled Oats",
            Quantity: 2,
            Measurement: "kg",
            price: 6.5,
          },
        ],
      },
    ],
    Status: "Collected",
    TotalPrice: 38.69,
  },
];

const History = () => {
  const [modalVisible, setModalVisible] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState(null);

  const renderItem = ({ item }) => (
    <View style={styles.orderItem}>
      <Text style={styles.orderDate}>Order Date: {item.orderDate}</Text>
      <Text style={styles.orderStatus}>Status: {item.Status}</Text>
      <Text style={styles.orderTotal}>Total: ${item.TotalPrice.toFixed(2)}</Text>
      {item.purchasedItemsViaWholesaler.map((wholesaler, index) => (
        <View key={index} style={styles.wholesalerSection}>
          <Text style={styles.wholesalerName}>{wholesaler.wholesalerName}</Text>
          {wholesaler.items.map((product, productIndex) => (
            <Text key={productIndex} style={styles.productItem}>
              {product.name} - {product.Quantity} {product.Measurement} - ${product.price.toFixed(2)}
            </Text>
          ))}
        </View>
      ))}
      <TouchableOpacity
        style={styles.ratingButton}
        onPress={() => {
          setSelectedOrder(item);
          setModalVisible(true);
        }}
      >
        <Text style={styles.ratingButtonText}>Give Rating</Text>
      </TouchableOpacity>
    </View>
  );

  return (
    <View style={styles.container}>
      <FlatList
        data={DUMMY_ITEMS}
        renderItem={renderItem}
        keyExtractor={(item, index) => index.toString()}
      />
      <RatingModal
        visible={modalVisible}
        onClose={() => setModalVisible(false)}
        order={selectedOrder}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 10,
  },
  orderItem: {
    backgroundColor: '#f0f0f0',
    padding: 15,
    marginBottom: 10,
    borderRadius: 5,
  },
  orderDate: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  orderStatus: {
    fontSize: 16,
    color: 'green',
  },
  orderTotal: {
    fontSize: 16,
    fontWeight: 'bold',
    marginTop: 5,
  },
  wholesalerSection: {
    marginTop: 10,
  },
  wholesalerName: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#333',
  },
  productItem: {
    fontSize: 14,
    marginLeft: 10,
  },
  ratingButton: {
    backgroundColor: '#007AFF',
    padding: 10,
    borderRadius: 5,
    marginTop: 10,
    alignItems: 'center',
  },
  ratingButtonText: {
    color: 'white',
    fontSize: 16,
  },
});

export default History;