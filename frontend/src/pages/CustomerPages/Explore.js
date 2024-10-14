import React, { useState } from 'react';
import { StyleSheet, View } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Searchbar } from 'react-native-paper';
import Products from '../../components/customers/Products';

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
        <Searchbar
          placeholder="Search ShopPeas"
          onChangeText={setSearchText}
          value={searchText}
          style={styles.searchBox}
        />
      </View>
      <View style={{ flex: 2 , justifyContent: 'center'}}>
        <Products productsData={DUMMY_ITEMS} onProductPress={handleProductPress} />
      </View>
    </View>
  );
};

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
    backgroundColor: '#FAF9F6',
    borderRadius: 10,
  },
});

export default Explore;