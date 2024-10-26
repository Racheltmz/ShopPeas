import React, { useState, useEffect, useMemo } from 'react';
import { StyleSheet, View } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import Fuse from 'fuse.js';
import { Dialog, ALERT_TYPE } from 'react-native-alert-notification';
import Products from '../../components/customers/Products';
import { useUserStore } from '../../lib/userStore';
import { Searchbar } from 'react-native-paper';
import Loader from '../../components/utils/Loader';
import productService from '../../service/ProductService';

const Explore = () => {
  const [searchText, setSearchText] = useState("");
  const navigation = useNavigation();
  const [loading, setLoading] = useState(false);
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const { userUid } = useUserStore();

  const handleProductPress = (item) => {
    navigation.navigate('ProductDetails', { product: item });
  }

  const loadProducts = async (userUid) => {
    await productService.fetchProductData(userUid)
      .then((res) => {
        setProducts(res);
        setFilteredProducts(res);
        setLoading(false);
      })
      .catch((err) => {
        Dialog.show({
          type: ALERT_TYPE.DANGER,
          title: err.status.code,
          textBody: "Failed to load products, please try again later.",
          button: 'close',
        })
        setLoading(false);
      })
  };

  useEffect(() => {
    setLoading(true);
    loadProducts(userUid);
  }, [userUid]);

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