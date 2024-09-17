import React from 'react';
import { View, Text, Image, StyleSheet } from 'react-native';

const ProductItem = ({ name, quantity, imageUrl, style }) => {
  return (
    <View style={[styles.container, style]}>
      <Image source={{ uri: imageUrl }} style={styles.image} />
      <Text style={styles.name}>{name}</Text>
      <Text style={styles.quantity}>{quantity} Packet</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#f0f0f0',
    borderRadius: 10,
    padding: 10,
    margin: 5,
    alignItems: 'center',
  },
  image: {
    width: '100%',
    height: 100,
    borderRadius: 5,
    marginBottom: 5,
  },
  name: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  quantity: {
    fontSize: 14,
    color: '#666',
  },
});

export default ProductItem;