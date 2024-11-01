import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, TextInput, Modal } from 'react-native';
import { Dropdown } from 'react-native-element-dropdown';
import { useUserStore } from "../../lib/userStore";
import { Ionicons } from '@expo/vector-icons';
import { CustomAlert } from '../utils/Alert';
import productService from '../../service/ProductService';

const AddProductModal = ({ visible, onClose, onAddProduct, products, uen }) => {
  const { userUid } = useUserStore();
  const [alertVisible, setAlertVisible] = useState(false);
  const [customAlert, setCustomAlert] = useState({ title: '', message: '', onConfirm: () => { } });
  const [newProduct, setNewProduct] = useState("");
  const [newPrice, setNewPrice] = useState(0);
  const [newStock, setNewStock] = useState(1);
  const [data, setData] = useState([]);

  const getOtherProducts = async () => {
    await productService.fetchProductData(userUid)
      .then((res) => {
        setData(res.filter(product => !products.some(p => p.pid === product.pid)));
      })
      .catch((err) => {
        console.error(err);
      })
  }

  useEffect(() => {
    // Get products wholesaler isn't selling
    getOtherProducts();
  });

  const showAlert = (title, message, onConfirm) => {
    setCustomAlert({ title, message, onConfirm });
    setAlertVisible(true);
  };

  const handleAddProduct = () => {
    if (isNaN(newPrice) || newPrice <= 0 || newProduct.pid === undefined) {
      showAlert("Missing Input", "Please select a product", () => setAlertVisible(false));
    } else {
      onAddProduct({
        uen: uen,
        pid: newProduct.pid,
        price: parseFloat(newPrice),
        stock: parseInt(newStock),
        active: "true",
      });
      setNewProduct("");
      setNewPrice(0);
      setNewStock(1);
    }
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
          <TouchableOpacity style={styles.closeButton} onPress={onClose}>
            <Ionicons name="close" size={24} color="#0C5E52" />
          </TouchableOpacity>
          <Text style={styles.modalTitle}>Add New Product</Text>

          <Text style={styles.inputLabel}>Product Name</Text>
          <Dropdown
            style={styles.input}
            data={data}
            placeholder="Product Name"
            value={newProduct}
            onChange={setNewProduct}
            labelField="name"
            valueField="pid"
          />

          <Text style={styles.inputLabel}>Price</Text>
          <TextInput
            style={styles.input}
            placeholder="Price"
            value={newPrice}
            keyboardType="numeric"
            onChangeText={setNewPrice}
          />

          <View style={styles.quantityContainer}>
            <Text style={styles.inputLabel}>Current Stock</Text>
            <View style={styles.quantityControls}>
              <TouchableOpacity onPress={() => setNewStock((prev) => (parseInt(prev) - 1).toString())}>
                <Ionicons name="remove-circle-outline" size={24} color="#0C5E52" />
              </TouchableOpacity>
              <Text style={styles.quantityText}>{newStock}</Text>
              <TouchableOpacity onPress={() => setNewStock((prev) => (parseInt(prev) + 1).toString())}>
                <Ionicons name="add-circle-outline" size={24} color="#0C5E52" />
              </TouchableOpacity>
            </View>
          </View>

          <TouchableOpacity style={styles.modalAddButton} onPress={handleAddProduct}>
            <Text style={styles.modalAddButtonText}>Add New Product</Text>
          </TouchableOpacity>
        </View>
      </View>

      <CustomAlert
        visible={alertVisible}
        title={customAlert.title}
        message={customAlert.message}
        onConfirm={() => {
          setAlertVisible(false);
          customAlert.onConfirm();
        }}
        onCancel={() => setAlertVisible(false)}
      />
    </Modal>
  );
};

const styles = StyleSheet.create({
  modalContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  modalContent: {
    width: "90%",
    backgroundColor: "#F5F5F5",
    padding: 20,
    borderRadius: 10,
  },
  modalTitle: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#0C5E52",
    marginBottom: 20,
    textAlign: "center",
  },
  input: {
    borderWidth: 1,
    borderColor: "#0C5E52",
    borderRadius: 5,
    padding: 10,
    marginBottom: 12,
    color: '#0C5E52',
  },
  inputLabel: {
    marginBottom: 6,
  },
  modalAddButton: {
    backgroundColor: "#0C5E52",
    padding: 10,
    borderRadius: 5,
    alignItems: "center",
  },
  modalAddButtonText: {
    fontWeight: "bold",
    fontSize: 16,
    color: "#EBF3D1",
  },
  closeButton: {
    position: "absolute",
    top: 10,
    right: 10,
  },
  quantityContainer: {
    marginBottom: 20,
  },
  quantityControls: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    marginTop: 10,
  },
  quantityText: {
    fontSize: 18,
    fontWeight: "bold",
    color: "#0C5E52",
  },
});

export default AddProductModal;
