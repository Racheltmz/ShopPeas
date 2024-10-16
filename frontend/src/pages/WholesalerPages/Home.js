import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, ScrollView, TouchableOpacity, TextInput, SafeAreaView, Image} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';
import WholesalerProduct from '../../components/wholesalers/WholesalerProduct';
import AddProduct from '../../components/wholesalers/AddProduct';
import WholesalerService from '../../service/WholesalerService';
import { useUserStore } from '../../lib/userStore';

const Home = () => {
  const [searchText, setSearchText] = useState("");
  const [showAddProduct, setShowAddProduct] = useState(false);
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigation = useNavigation();
  const { userUid } = useUserStore();

  console.log('Current userUid:', userUid);

  const loadProfile = async () => {
    try {
      setLoading(true);
      console.log('Fetching profile for userUid:', userUid);
      const fetchedProfile = await WholesalerService.retrieveProfile(userUid);
      console.log('Fetched profile:', fetchedProfile);
      setProfile(fetchedProfile);
    } catch (err) {
      console.error('Error loading profile:', err);
      setError('Failed to load profile. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    console.log(userUid)
    if (userUid) {
      loadProfile();
    } else {
      setError('User ID is missing. Please ensure you are logged in.');
      setLoading(false);
    }
  }, []);
  

  const navigateToProfile = () => {
    navigation.navigate('Profile');
  };

  const [products, setProducts] = useState([
    {name: 'Bok Choy', price: 1.29, unit: '1 Packet', stock: 22 , description: 'Veggies'},
    {name: 'Tomatoes', price: 1.82, unit: '1 Packet', stock: 10, description: 'Red Fruit' },
    {name: 'Soy Sauce', price: 2.27, unit: '500 ml', stock: 30, description: 'Salty Dressing'},
    {name: 'Rolled Oats', price: 4.80, unit: '1kg', stock: 52, description: 'Special type of oats'},
    {name: 'Carrots', price: 2.27, unit: '500 ml', stock: 30, description: 'Orange fruit that is a root' },
    {name: 'Potatoes', price: 4.80, unit: '1kg', stock: 52, description: 'Edible rocks found underground'},
  ]);

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
    <SafeAreaView style = {styles.container}>
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
              <Text style={styles.headerTitle}>{profile.name}</Text>
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
            key={index}
            index={index}
            name={product.name}
            price={product.price}
            unit={product.unit}
            stock={product.stock}
            description={product.description}
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