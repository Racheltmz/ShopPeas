import React from 'react';
import { StyleSheet, TouchableOpacity, View, Text } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { useNavigation } from '@react-navigation/native';

const ProductDetailsHeader = ({ name, desc }) => {
  const navigation = useNavigation();

  const navigateToCart = () => {
    navigation.navigate('Cart');
  };

  return (
    <View style={styles.header}>
      <TouchableOpacity onPress={() => navigation.goBack()}>
        <Ionicons name="arrow-back" size={24} color="#0C5E52" />
      </TouchableOpacity>
      <View styles={styles.row}>
        <Text style={styles.headerTitle}>{name}</Text>
        {/* <Text style={styles.headerSubtitle}>{desc}</Text> */}
      </View>
      {/* <TouchableOpacity onPress={navigateToCart}>
        <Ionicons name="cart-outline" size={24} color="#0C5E52" />
      </TouchableOpacity> */}
    </View>
  );
};

const styles = StyleSheet.create({
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 15,
    backgroundColor: 'white',
    marginBottom: 10,
    marginTop: 60,
    marginHorizontal: 10,
    borderRadius: 10,
  },
  headerTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#0C5E52',
    textAlign: 'center',
  },
  headerSubtitle: {
    fontSize: 18,
    color: '#0C5E52',
    textAlign: 'center',
    marginTop: 2,
  },
  row: {
    flexDirection: 'row',
  },
});

export default ProductDetailsHeader;