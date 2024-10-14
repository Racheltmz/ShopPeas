import React, { useState } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, TextInput, Modal } from 'react-native';
import { Ionicons } from '@expo/vector-icons';

const AddProductModal = ({ visible, onClose, onAddProduct }) => {
  const [newProduct, setNewProduct] = useState({
    name: '',
    price: '',
    description: '',
    stock: 0,
    minimumOrder: 0,
  });

    const handleAddProduct = () => {
        const price = parseFloat(newProduct.price);
        const stock = parseInt(newProduct.stock);
        
        if (isNaN(price) || newProduct.name.trim() === '') {
        alert('Please enter a valid name and price');
        return;
        }
    
        const productToAdd = {
        ...newProduct,
        name: newProduct.name.trim(),
        price: price,
        stock: isNaN(stock) ? 0 : stock,
        description: newProduct.description.trim(),
        };
        onAddProduct(productToAdd);
        setNewProduct({ name: '', price: '', description: '', stock: 0, minimumOrder: 0 });
        onClose();
    };

  return (
    <Modal
      animationType="slide"
      transparent={true}
      visible={visible}
      onRequestClose={onClose}
    >
      <View style={styles.modalContainer}>
        <View style={styles.modalContent}>
          <TouchableOpacity
            style={styles.closeButton}
            onPress={onClose}
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
  );
};

const styles = StyleSheet.create({
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
    backgroundColor: '#0C5E52',
    padding: 10,
    borderRadius: 5,
    alignItems: 'center',
  },
  modalAddButtonText: {
    fontWeight: 'bold',
    fontSize: 16,
    color: '#EBF3D1',
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

export default AddProductModal;