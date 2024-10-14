import React, { useState } from 'react';
import { StyleSheet, Text, View, FlatList, TouchableOpacity } from 'react-native';
import RatingModal from '../../components/customers/RatingModal';
import HistoryItems from '../../components/customers/HistoryItems';

// api call for items here
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
  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerText}>Order History</Text>
      </View>
      <HistoryItems historyList={DUMMY_ITEMS}/>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: "5%",
  },
  header: {
    alignItems: "center",
    backgroundColor: 'white',
    padding: 15,
    marginVertical: "5%",
    borderRadius: 15,
    
  },
  headerText: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#0C5E52",
  }

});

export default History;