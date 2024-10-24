import React from "react";
import { View, Text, Image, StyleSheet, TouchableOpacity } from "react-native";

const ProductItem = ({ name, imageUrl, packageSize, isGridView, onPress }) => {
  return (
    <TouchableOpacity
      style={[styles.container, isGridView ? styles.gridItem : styles.listItem]}
      onPress={onPress}
    >
      <Image
        source={{uri: imageUrl}}
        style={[styles.image, isGridView ? styles.gridImage : styles.listImage]}
      />
      <View style={{ flex: 1, marginLeft: isGridView ? 0 : 15 }}>
        <Text style={styles.name}>{name}</Text>
        <Text style={styles.packageSize}>{packageSize}</Text>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: "#D6E8A4",
    borderRadius: 10,
    padding: 10,
    margin: 10,
  },
  gridItem: {
    width: "44%",
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
    marginBottom: 5,
    fontSize: 14,
    fontWeight: "bold",
    color: '#0C5E52',
  },
  packageSize: {
    fontSize: 14,
    color: "#666",
  },
});

export default ProductItem;