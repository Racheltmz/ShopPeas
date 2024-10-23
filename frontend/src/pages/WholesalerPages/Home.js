import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, View, ScrollView, TouchableOpacity, TextInput, SafeAreaView, Image} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';
import { useUserStore } from "../../lib/userStore";
import WholesalerProduct from '../../components/wholesalers/WholesalerProduct';
import AddProduct from '../../components/wholesalers/AddProduct';
import productService from '../../service/ProductService';

const Home = () => {
  const navigation = useNavigation();
  const { currentUser, userUid } = useUserStore();
  // const [products, setProducts] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [showAddProduct, setShowAddProduct] = useState(false);
  const [products, setProducts] = useState([]);

  const fetchProducts = async (userUid) => {
    await productService.getProductsByUEN(userUid)
      .then((res) => {
        setProducts(res);
      })
  }

  useEffect(() => {
    fetchProducts(userUid);
  }, [userUid])

  const navigateToProfile = () => {
    navigation.navigate('Profile');
  };

  const removeProduct = (index) => {
    setProducts(currentProducts => currentProducts.filter((_, i) => i !== index));
  };

  const editProduct = (index, updatedProduct) => {
    setProducts(currentProducts =>
      currentProducts.map((product, i) =>
        i === index ? { ...product, ...updatedProduct } : product
      )
    );
  };

  const handleAddProduct = (newProduct) => {
    setProducts(currentProducts => [...currentProducts, newProduct]);
  };

  return (
    <SafeAreaView style={styles.container}>
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
        <TouchableOpacity onPress={navigateToProfile}>
          <Ionicons name="person-circle-outline" size={28} color="#0C5E52" />
        </TouchableOpacity>
      </View>

      <View style={styles.header}>
        <View style={styles.headerTextContainer}>
          <Text style={styles.headerTitle}>{currentUser.name}</Text> 
          <Text style={styles.subHeaderTitle}>My Products</Text>
        </View>
        <Image
          source={require('../../../assets/imgs/pea.png')}
          style={styles.headerImage}
        />
      </View>

      <View style={styles.addProductContainer}>
        <TouchableOpacity style={styles.addButton} onPress={() => setShowAddProduct(true)}>
          <Ionicons name="add-circle-outline" size={24} color="white" />
        </TouchableOpacity>
        <Text style={styles.addButtonText}>Add New Product</Text>
        <TouchableOpacity style={styles.filterButton}>
          <Ionicons name="funnel-outline" size={30} color="#0C5E52" />
        </TouchableOpacity>
      </View>
      <ScrollView style={styles.productList}>
        {products.map((product, index) => (
          <WholesalerProduct
            key={product.pid}
            index={index}
            name={product.name}
            price={100} // TODO
            unit={product.package_size}
            stock={100} // TODO
            image_url={product.image_url}
            onRemove={removeProduct}
            onEdit={editProduct}
          />
        ))}
      </ScrollView>

      <AddProduct
        visible={showAddProduct}
        onClose={() => setShowAddProduct(false)}
        onAddProduct={handleAddProduct}
      />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  searchBar: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#E2ECEA',
    margin: 10,
    padding: 10,
    borderRadius: 25,
  },
  searchInput: {
    flex: 1,
    marginLeft: 10,
    color: '#0C5E52',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginLeft: '5%',
    marginTop: '1%',
  },
  headerTitle: {
    fontFamily: "Amiko, Noto Sans",
    fontSize: 25,
    fontWeight: 'normal',
    color: '#0C5E52',
    marginLeft: '2%',
  },
  subHeaderTitle: {
    fontFamily: "Amiko, Noto Sans",
    fontSize: 35,
    fontWeight: 'bold',
    color: '#0C5E52',
    margin: '2%',
  },
  headerImage: {
    width: '18%',
    height: '65%',
    marginRight: "29.2%",
  },
  addProductContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: '1%',
    marginLeft: '6%',
    marginBottom: '1%',
  },
  addButton: {
    backgroundColor: '#FF7B5F',
    padding: 5,
    borderRadius: 10,
    justifyContent: 'center',
    alignItems: 'center',
  },
  addButtonText: {
    color: '#0C5E52',
    marginLeft: 10,
    fontSize: '20%',
    fontWeight: 'normal',
  },
  filterButton: {
    padding: '1%',
    marginLeft: "auto",
    marginRight: '3%',
  },
});

export default Home;