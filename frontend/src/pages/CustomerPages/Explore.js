import React, { useState, useEffect, useMemo } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import Fuse from 'fuse.js';
import Products from '../../components/customers/Products';
import { useUserStore } from '../../lib/userStore';
import { Searchbar } from 'react-native-paper';
import Loader from '../../components/utils/Loader';
import productService from '../../service/ProductService';

const Explore = () => {
  const [searchText, setSearchText] = useState("");
  const navigation = useNavigation();
  const [loading, setLoading] = useState(true);
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [error, setError] = useState("");
  const { userUid } = useUserStore();

  const handleProductPress = (item) => {
    navigation.navigate('ProductDetails', { product: item });
  }

  useEffect(() => {
    const loadProducts = async () => {
      try {
        setLoading(true);
        const productsList = await productService.fetchProductData(userUid);
        setProducts(productsList);
        setFilteredProducts(productsList);
      } catch (err) {
        console.error('Error loading products:', err);
        setError('Failed to load products. Please try again later.');
      } finally {
        setLoading(false);
      }
    };

    loadProducts();
  }, []);

  const fuse = useMemo(() => new Fuse(products, {
    // fields to search
    keys: ['name'], 
    threshold: 0.3, // Adjust this value to make the search more or less strict
    includeScore: true
  }), [products]);

  const handleSearch = (query) => {
    setSearchText(query);
    if (query.trim() === '') {
      setFilteredProducts(products);
    } else {
      const results = fuse.search(query);
      setFilteredProducts(results.map(result => result.item));
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Searchbar
          placeholder="Search ShopPeas"
          onChangeText={handleSearch}
          value={searchText}
          style={styles.searchBox}
          autoCapitalize="none"
        />
      </View>
      {loading && <Loader loading={loading}></Loader>}
      {error ? <Text>{error}</Text> : null}
      <View style={{ flex: 2, justifyContent: 'center' }}>
        <Products onProductPress={handleProductPress} productData={filteredProducts}/>
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
    borderRadius: 25,
  },
});

export default Explore;