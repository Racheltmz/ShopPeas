import React from "react";
import { View, Text, Image, StyleSheet, TouchableOpacity } from "react-native";

const ProductItem = ({ name, quantity, imageUrl, isGridView, onPress }) => {
  return (
    <TouchableOpacity
      style={[styles.container, isGridView ? styles.gridItem : styles.listItem]}
      onPress={onPress}
    >
      <Image
        source={imageUrl}
        style={[styles.image, isGridView ? styles.gridImage : styles.listImage]}
      />
      <View style={{ flex: 1, marginLeft: isGridView ? 0 : 10 }}>
        <Text style={styles.name}>{name}</Text>
        <Text style={styles.quantity}>{quantity} Packet</Text>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: "#f0f0f0",
    borderRadius: 10,
    padding: 10,
    margin: 5,
  },
  gridItem: {
    width: "47%",
  },
  listItem: {
    flexDirection: "row",
    alignItems: "center",
  },
  image: {
    borderRadius: 5,
    marginBottom: 5,
  },
  gridImage: {
    width: "100%",
    height: 100,
  },
  listImage: {
    width: 80,
    height: 80,
  },
  name: {
    fontSize: 16,
    fontWeight: "bold",
  },
  quantity: {
    fontSize: 14,
    color: "#666",
  },
});

export default ProductItem;