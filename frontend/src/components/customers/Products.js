import React, { useState } from 'react';
import { FlatList, StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import ProductItem from './ProductItem';

const Products = ({ productsData, onProductPress }, ) => {
  const [isGridView, setIsGridView] = useState(true);
  const numColumns = isGridView ? 2 : 1;
  
  const renderItem = ({ item }) => (
    <ProductItem
      name={item.name}
      quantity={item.quantity}
      imageUrl={item.img}
      isGridView={isGridView}
      onPress={() => onProductPress(item)}
    />
  );

  return (
    <View style={styles.productsContainer}>
      <View style={styles.headerContainer}>
        <Text style={styles.headerText}>New Products</Text>
        <View style={styles.viewToggle}>
          <TouchableOpacity onPress={() => setIsGridView(false)} style={styles.toggleButton}>
            <Ionicons name="list" size={24} color={isGridView ? '#000' : '#4CAF50'} />
          </TouchableOpacity>
          <TouchableOpacity onPress={() => setIsGridView(true)} style={styles.toggleButton}>
            <Ionicons name="grid" size={24} color={isGridView ? '#4CAF50' : '#000'} />
          </TouchableOpacity>
        </View>
      </View>
      <FlatList
        data={productsData}
        renderItem={renderItem}
        keyExtractor={(item) => item.id.toString()}
        numColumns={numColumns}
        key={numColumns}  // This forces the list to re-render when switching views
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
    margin: 10,
    borderRadius: 20,
  },
  headerContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginVertical: 10,
  },
  headerText: {
    fontSize: 20,
    fontWeight: '500',
  },
  viewToggle: {
    flexDirection: 'row',
  },
  toggleButton: {
    padding: 5,
  },
  flatListContent: {
    paddingVertical: 10,
  },
});

export default Products;