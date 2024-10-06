import React from "react";
import { StyleSheet, View, Text, Image, FlatList } from "react-native";

const LikedItems = () => {
  // Dummy items
  const likedItems = [
    {
      id: "1",
      name: "Organic Bok Choy",
      quantity: "1 Packet",
      description: "Description",
    },
    {
      id: "2",
      name: "Organic Bok Choy",
      quantity: "1 Packet",
      description: "Description",
    },
    {
      id: "3",
      name: "Organic Bok Choy",
      quantity: "1 Packet",
      description: "Description",
    },
    {
      id: "4",
      name: "Organic Bok Choy",
      quantity: "1 Packet",
      description: "Description",
    },
    {
      id: "5",
      name: "Organic d Choy",
      quantity: "1 Packet",
      description: "Description",
    },
  ];

  const renderItem = ({ item }) => (
    <View style={styles.itemContainer}>
      <Image
        source={require("../../../assets/imgs/DummyImage.jpg")} // Replace with actual image path
        style={styles.productImage}
      />
      <View style={styles.itemDetails}>
        <Text style={styles.itemName}>{item.name}</Text>
        <Text style={styles.itemQuantity}>{item.quantity}</Text>
        <Text style={styles.itemDescription}>{item.description}</Text>
      </View>
    </View>
  );

  return (
    <FlatList
      data={likedItems}
      renderItem={renderItem}
      keyExtractor={(item) => item.id}
      style={styles.list}
    />
  );
};

const styles = StyleSheet.create({
  list: {
    padding: 10,
  },
  itemContainer: {
    flexDirection: "row",
    backgroundColor: "#D6E8A4",
    borderRadius: 10,
    padding: 15,
    marginBottom: 10,
  },
  productImage: {
    width: 140,
    height: 140,
    borderRadius: 5,
  },
  itemDetails: {
    marginLeft: 10,
    flex: 1,
  },
  itemName: {
    fontSize: 16,
    fontWeight: "bold",
  },
  itemQuantity: {
    color: "#666",
  },
  itemDescription: {
    fontStyle: "italic",
  },
});

export default LikedItems;
