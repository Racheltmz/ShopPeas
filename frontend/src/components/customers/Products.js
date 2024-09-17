import React from 'react';
import { FlatList, StyleSheet, View, Dimensions } from 'react-native';
import ProductItem from './ProductItem';

const { width } = Dimensions.get('window');
const numColumns = 2;
const itemWidth = (width - 40) / numColumns; // 40 is the total horizontal padding

const Products = ({ productsData }) => {
  const renderItem = ({ item }) => (
    <ProductItem
      name={item.name}
      quantity={item.quantity}
      imageUrl={item.img}
      style={{ width: itemWidth }}
    />
  );

  return (
    <View style={styles.productsContainer}>
      <FlatList
        data={productsData}
        renderItem={renderItem}
        keyExtractor={(item) => item.id.toString()}
        numColumns={numColumns}
        contentContainerStyle={styles.flatListContent}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  productsContainer: {
    flex: 1,
    paddingHorizontal: 10,
  },
  flatListContent: {
    paddingVertical: 10,
  },
});

export default Products;