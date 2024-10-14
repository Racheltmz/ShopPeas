import React, { useState } from 'react';
import { StyleSheet, View, TextInput, Text } from 'react-native';
import Products from '../../components/customers/Products';
import { useNavigation } from '@react-navigation/native';

const Explore = () => {
  const [searchText, setSearchText] = useState("");
  const navigation = useNavigation();

  const DUMMY_ITEMS = [
    {
    name: "Bok Choy",
    quantity: 1,
    img: require("../../../assets/imgs/DummyImage.jpg"),
    id: 1,
  },
    {
    name: "Tomato",
    quantity: 5,
    img: require("../../../assets/imgs/DummyImage.jpg"),
    id: 2,
  },
    {
    name: "Lemonade",
    quantity: 1,
    img: require("../../../assets/imgs/DummyImage.jpg"),
    id: 3,
  },
    {
    name: "Potato",
    quantity: 3,
    img: require("../../../assets/imgs/DummyImage.jpg"),
    id: 4,
  },
    {
    name: "Bok Choy",
    quantity: 1,
    img: require("../../../assets/imgs/DummyImage.jpg"),
    id: 5,
  },
    {
    name: "Tomato",
    quantity: 5,
    img: require("../../../assets/imgs/DummyImage.jpg"),
    id: 6,
  },
    {
    name: "Lemonade",
    quantity: 1,
    img: require("../../../assets/imgs/DummyImage.jpg"),
    id: 7,
  },
    {
    name: "Potato",
    quantity: 3,
    img: require("../../../assets/imgs/DummyImage.jpg"),
    id: 8,
  },
    {
    name: "Bok Choy",
    quantity: 1,
    img: require("../../../assets/imgs/DummyImage.jpg"),
    id: 9,
  },
    {
    name: "Tomato",
    quantity: 5,
    img: require("../../../assets/imgs/DummyImage.jpg"),
    id: 10,
  },
    {
    name: "Lemonade",
    quantity: 1,
    img: require("../../../assets/imgs/DummyImage.jpg"),
    id: 11,
  },
    {
    name: "Potato",
    quantity: 3,
    img: require("../../../assets/imgs/DummyImage.jpg"),
    id: 12,
  },
]

  const handleProductPress = (item) => {
    navigation.navigate('ProductDetails', {product: item});
  }

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TextInput 
          style={styles.searchBox}
          placeholder='Search ShopPeas'
          value={searchText}
          autoCapitalize="none"
          onChangeText={(text) => setSearchText(text)}
        />
      </View>
      <View style={{ flex: 2 , justifyContent: 'center'}}>
        <Products productsData={DUMMY_ITEMS} onProductPress={handleProductPress} />
      </View>
    </View>
  );
};

export default Explore;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'transparent',
  },
  header: {
    marginTop: 80,
    paddingHorizontal: 20,
  },
  searchBox: {
    backgroundColor: 'rgba(255, 255, 255, 1)', // Semi-transparent white
    borderRadius: 20,
    padding: 10,
  },
});