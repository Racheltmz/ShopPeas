import React, { useState } from 'react';
import { StyleSheet, TextInput, View } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';
import Products from '../../components/customers/Products';

const Explore = () => {
  const [searchText, setSearchText] = useState("");
  const navigation = useNavigation();

  const handleProductPress = (item) => {
    navigation.navigate('ProductDetails', { product: item });
  }

  return (
    <View style={styles.container}>
      <View style={styles.searchBar}>
        <Ionicons name="search" size={24} color="#0C5E52" />
        <TextInput
          style={styles.searchInput}
          placeholder="Search Products"
          placeholderTextColor="#0C5E52"
          value={searchText}
          autoCapitalize="none"
          onChangeText={(text) => setSearchText(text)}
        />
      </View>
      <View style={{ flex: 2, justifyContent: 'center' }}>
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
  searchBar: {
    marginTop: 80,
    paddingHorizontal: 20,
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FAF9F6',
    margin: 10,
    padding: 14,
    borderRadius: 25,
  },
  searchInput: {
    marginLeft: 10,
  },
});

export default Explore;