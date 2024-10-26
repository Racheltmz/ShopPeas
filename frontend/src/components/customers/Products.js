import React, { useEffect, useState } from "react";
import {
  FlatList,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import ProductItem from "./ProductItem";

const Products = ({ productData, onProductPress }) => {
  const [isGridView, setIsGridView] = useState(true);
  const numColumns = isGridView ? 2 : 1;

  const renderItem = ({ item }) => (
    <ProductItem
      name={item.name}
      packageSize={item.package_size}
      imageUrl={item.image_url}
      isGridView={isGridView}
      onPress={() => onProductPress(item)}
    />
  );

  return (
    <View style={styles.productsContainer}>
      <View style={styles.headerContainer}>
        <Text style={styles.headerText}>New Products</Text>
        <View style={styles.viewToggle}>
          <TouchableOpacity
            onPress={() => setIsGridView(false)}
            style={[
              styles.toggleButton,
              styles.toggleListButton,
              isGridView ? null : styles.listViewActive,
            ]}
          >
            <Ionicons
              name="list"
              size={24}
              color={isGridView ? "#0C5E52" : "#D6E8A4"}
            />
          </TouchableOpacity>
          <TouchableOpacity
            onPress={() => setIsGridView(true)}
            style={[
              styles.toggleButton,
              styles.toggleGridButton,
              isGridView ? styles.gridViewActive : null,
            ]}
          >
            <Ionicons
              name="grid"
              size={24}
              color={isGridView ? "#D6E8A4" : "#0C5E52"}
            />
          </TouchableOpacity>
        </View>
      </View>
      <FlatList
        data={productData}
        renderItem={renderItem}
        keyExtractor={(item) => item.name}
        numColumns={numColumns}
        key={numColumns} 
        contentContainerStyle={styles.flatListContent}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  productsContainer: {
    flex: 1,
    paddingHorizontal: 10,
    backgroundColor: "white",
    borderTopLeftRadius: 30,
    borderTopRightRadius: 30,
    marginTop: 20,
  },
  headerContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    marginHorizontal: 20,
    marginTop: 20,
    marginBottom: 10,
  },
  headerText: {
    fontSize: 20,
    fontWeight: "600",
    color: "#0C5E52",
  },
  viewToggle: {
    flexDirection: "row",
  },
  toggleButton: {
    padding: 5,
  },
  toggleGridButton: {
    borderTopRightRadius: 5,
    borderBottomRightRadius: 5,
    backgroundColor: "#0C5E5250",
  },
  toggleListButton: {
    borderTopLeftRadius: 5,
    borderBottomLeftRadius: 5,
    backgroundColor: "#0C5E5250",
  },
  gridViewActive: {
    backgroundColor: "#0C5E5295",
  },
  listViewActive: {
    backgroundColor: "#0C5E5295",
  },
  flatListContent: {
    marginHorizontal: 16,
    paddingVertical: 10,
  },
});

export default Products;
