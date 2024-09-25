import React, { useState } from 'react';
import { StyleSheet, View, TextInput, Text } from 'react-native';
import FilteredProducts from '../../components/customers/FilteredProducts';

const Explore = () => {
  const [searchText, setSearchText] = useState("");

  const DUMMY_ITEMS = [
    {
    name: "Bok Choy",
    quantity: 1,
    img: "../../assests/imgs/DummyImage.jpg",
  },
    {
    name: "Tomato",
    quantity: 5,
    img: "../../assests/imgs/DummyImage.jpg"
  },
    {
    name: "Lemonade",
    quantity: 1,
    img: "../../assests/imgs/DummyImage.jpg"
  },
    {
    name: "Potato",
    quantity: 3,
    img: "../../assests/imgs/DummyImage.jpg"
  },
]

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
      <View style={{ flex: 1 }}>
        <View style={{ flex: 3 }}>
          
        </View>
        <Text>New Products</Text>
        <FilteredProducts productsData={DUMMY_ITEMS} />
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