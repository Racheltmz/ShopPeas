import React, { useState } from 'react';
import { StyleSheet, View } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Searchbar } from 'react-native-paper';
import Products from '../../components/customers/Products';

const Explore = () => {
  const [searchText, setSearchText] = useState("");
  const navigation = useNavigation();

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
        <Products onProductPress={handleProductPress} />
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