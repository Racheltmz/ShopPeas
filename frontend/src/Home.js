import React, { useState } from 'react';
import { StyleSheet, Text, View, ScrollView, TouchableOpacity, TextInput, SafeAreaView, Image, Modal } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import WholesalerProduct from '../../components/wholesalers/WholesalerProduct';

const Home = ({ navigation }) => {
  const [products, setProducts] = useState([
    { name: 'Bok Choy', price: 1.29, unit: '1 Packet', stock: 22, description: 'Veggies' },
    { name: 'Tomatoes', price: 1.82, unit: '1 Packet', stock: 10, description: 'Red Fruit' },
    { name: 'Soy Sauce', price: 2.27, unit: '500 ml', stock: 30, description: 'Chinese Dressing' },
    { name: 'Rolled Oats', price: 4.80, unit: '1kg', stock: 52, description: 'Special type of oats' },
    { name: 'Carrots', price: 2.27, unit: '500 ml', stock: 30, description: 'Orange fruit that is a root' },
    { name: 'Potatoes', price: 4.80, unit: '1kg', stock: 52, description: 'Edible rocks found underground' },
  ]);

  const [showAddProductModal, setShowAddProductModal] = useState(false);
  const [newProduct, setNewProduct] = useState({
    name: '',
    price: '',
    description: '',
    stock: 0,
    minimumOrder: 0,
  });

  const handleAddProduct = () => {
    const productToAdd = {
      ...newProduct,
      price: parseFloat(newProduct.price),
      stock: parseInt(newProduct.stock),
      description: newProduct.description,
    };

    setProducts([productToAdd, ...products]); // Add the new product at the top of the list
    setShowAddProductModal(false); // Close the modal
    setNewProduct({ name: '', price: '', description: '', stock: 0, minimumOrder: 0 }); // Reset the form
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.searchBar}>
        <Ionicons name="search" size={24} color="#0C5E52" />
        <TextInput
          style={styles.searchInput}
          placeholder="Search Products"
          placeholderTextColor="#0C5E52"
        />
        <TouchableOpacity>
          <Ionicons name="person-circle-outline" size={28} color="#0C5E52" />
        </TouchableOpacity>
      </View>

      <View style={styles.header}>
        <View style={styles.headerTextContainer}>
          <Text style={styles.headerTitle}>Happy Wholesaler</Text>
          <Text style={styles.subHeaderTitle}>My Products</Text>
        </View>
        <Image
          source={require('../../../assets/imgs/pea.png')}
          style={styles.headerImage}
        />
      </View>

      <View style={styles.addProductContainer}>
        <TouchableOpacity
          style={styles.addButton}
          onPress={() => setShowAddProductModal(true)} // Show the Add Product modal
        >
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
          />
        ))}
      </ScrollView>

      {/* Modal for adding a new product */}
      <Modal
        animationType="slide"
        transparent={true}
        visible={showAddProductModal}
        onRequestClose={() => setShowAddProductModal(false)}
      >
        <View style={styles.modalContainer}>
          <View style={styles.modalContent}>
            <TouchableOpacity
              style={styles.closeButton}
              onPress={() => setShowAddProductModal(false)}
            >
              <Ionicons name="close" size={24} color="#0C5E52" />
            </TouchableOpacity>
            <Text style={styles.modalTitle}>Add New Product</Text>

            <Text>Product Name</Text>

            <TextInput
              style={styles.input}
              placeholder="Product Name"
              value={newProduct.name}
              onChangeText={(text) => setNewProduct({ ...newProduct, name: text })}
            />
            <Text>Price</Text>
            <TextInput
              style={styles.input}
              placeholder="Price"
              value={newProduct.price}
              keyboardType="numeric"
              onChangeText={(text) => setNewProduct({ ...newProduct, price: text })}
            />
            <Text>Description</Text>
            <TextInput
              style={styles.input}
              placeholder="Description"
              value={newProduct.description}
              onChangeText={(text) => setNewProduct({ ...newProduct, description: text })}
            />

            <View style={styles.quantityContainer}>
              <Text>Current Stock</Text>
              <View style={styles.quantityControls}>
                <TouchableOpacity onPress={() => setNewProduct({ ...newProduct, stock: Math.max(newProduct.stock - 1, 0) })}>
                  <Ionicons name="remove-circle-outline" size={24} color="#0C5E52" />
                </TouchableOpacity>
                <Text style={styles.quantityText}>{newProduct.stock}</Text>
                <TouchableOpacity onPress={() => setNewProduct({ ...newProduct, stock: newProduct.stock + 1 })}>
                  <Ionicons name="add-circle-outline" size={24} color="#0C5E52" />
                </TouchableOpacity>
              </View>
            </View>

            <View style={styles.quantityContainer}>
              <Text>Minimum Order Quantity</Text>
              <View style={styles.quantityControls}>
                <TouchableOpacity onPress={() => setNewProduct({ ...newProduct, minimumOrder: Math.max(newProduct.minimumOrder - 1, 0) })}>
                  <Ionicons name="remove-circle-outline" size={24} color="#0C5E52" />
                </TouchableOpacity>
                <Text style={styles.quantityText}>{newProduct.minimumOrder}</Text>
                <TouchableOpacity onPress={() => setNewProduct({ ...newProduct, minimumOrder: newProduct.minimumOrder + 1 })}>
                  <Ionicons name="add-circle-outline" size={24} color="#0C5E52" />
                </TouchableOpacity>
              </View>
            </View>

            <TouchableOpacity style={styles.modalAddButton} onPress={handleAddProduct}>
              <Text style={styles.modalAddButtonText}>Add New Product</Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
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
    marginLeft: 20,
    marginTop: 5,
  },
  headerTextContainer: {
    flex: 1,
  },
  headerTitle: {
    fontFamily: 'Noto Sans',
    fontSize: 25,
    fontWeight: 'normal',
    color: '#0C5E52',
    marginLeft: 5,
  },
  subHeaderTitle: {
    fontFamily: 'Noto Sans',
    fontSize: 35,
    fontWeight: 'bold',
    color: '#0C5E52',
    margin: 5,
  },
  headerImage: {
    width: 80,
    height: 90,
    marginRight: '28.2%',
  },
  addProductContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 5,
    marginLeft: 20,
    marginBottom: 5,
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
    fontSize: 22,
    fontWeight: 'normal',
  },
  filterButton: {
    padding: 5,
    borderRadius: 10,
    marginLeft: 'auto',
    marginRight: 10,
  },
  productList: {
    marginTop: 10,
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  modalContent: {
    width: '90%',
    backgroundColor: '#F5F5F5',
    padding: 20,
    borderRadius: 10,
  },
  modalTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginBottom: 20,
    textAlign: 'center',
  },
  input: {
    borderWidth: 1,
    borderColor: '#0C5E52',
    borderRadius: 5,
    padding: 10,
    marginBottom: 10,
    color: '#0C5E52',
  },
  modalAddButton: {
    backgroundColor: '#A8E0A4',
    padding: 10,
    borderRadius: 5,
    alignItems: 'center',
  },
  modalAddButtonText: {
    fontWeight: 'bold',
    fontSize: 16,
    color: '#0C5E52',
  },
  closeButton: {
    position: 'absolute',
    top: 10,
    right: 10,
  },
  quantityContainer: {
    marginBottom: 20,
  },
  quantityControls: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginTop: 10,
  },
  quantityText: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
});

export default Home;
