import React from 'react';
import { StyleSheet, TouchableOpacity, View, Text } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { useNavigation } from '@react-navigation/native';

const ProductDetailsHeader = ({ name }) => {
  const navigation = useNavigation();

  const navigateToCart = () => {
    navigation.navigate('Cart');
  };

  return (
    <View style={styles.header}>
      <TouchableOpacity onPress={() => navigation.goBack()}>
        <Ionicons name="arrow-back" size={24} color="black" />
      </TouchableOpacity>
      <Text style={styles.headerTitle}>{name}</Text>
      <TouchableOpacity onPress={navigateToCart}>
        <Ionicons name="cart-outline" size={24} color="black" />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  header: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    height: 60,
    padding: 10,
    backgroundColor: "white",
    marginVertical: 10,
    marginTop: 30,
    marginHorizontal: 10,
  },
  headerTitle: {
    fontSize: 18,
    fontWeight: "bold",
  },
});

export default ProductDetailsHeader;