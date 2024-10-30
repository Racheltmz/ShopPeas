import React, { useCallback, useEffect, useState } from "react";
import {
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  TextInput,
  Modal,
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import Alert from "../utils/Alert";
import productService from "../../service/ProductService";
import { useUserStore } from "../../lib/userStore";
import { Picker } from "@react-native-picker/picker";

const AddProductModal = ({
  visible,
  onClose,
  onAddProduct,
  wholesalerProducts,
}) => {
  const [alertVisible, setAlertVisible] = useState(false);
  const [customAlert, setCustomAlert] = useState({
    title: "",
    message: "",
    onConfirm: () => {},
  });
  const [availableProducts, setAvailableProducts] = useState([]);
  const { userUid } = useUserStore();

  const fetchAllProducts = useCallback(async () => {
    try {
      const productsFetched = await productService.fetchProductData(userUid);

      if (
        !Array.isArray(productsFetched) ||
        !Array.isArray(wholesalerProducts)
      ) {
        console.error("Invalid data format received");
        return;
      }

      const filteredProducts = productsFetched.filter((product) => {
        return !wholesalerProducts.some(
          (wholeProd) => wholeProd.pid === product.pid
        );
      });

      setAvailableProducts(filteredProducts);
    } catch (error) {
      console.error("Error fetching or filtering products:", error);
    }
  }, [availableProducts]);

  useEffect(() => {
    fetchAllProducts();
  }, [fetchAllProducts]);

  const showAlert = (title, message, onConfirm) => {
    setCustomAlert({ title, message, onConfirm });
    setAlertVisible(true);
  };
  const [newProduct, setNewProduct] = useState({
    "pid": "",
    "price": "",
    "stock": 0,
    "active": true,
  });

  const [newProductName, setNewProductName] = useState("");

  const handleProductChange = (itemValue) => {
    const selected = availableProducts.find(product => product.name === itemValue);
    setNewProductName(itemValue);
    setNewProduct({
        ...newProduct,
        pid: selected.pid
    });
  };

  const handleAddProduct = () => {
    const price = parseFloat(newProduct.price);
    const stock = parseInt(newProduct.stock);
    
    if (isNaN(price) || newProductName.trim() === "") {
      showAlert("Missing Input", "Please enter a valid name and price", () =>
        setAlertVisible(false)
      );
      return;
    }

    const productToAdd = {
      ...newProduct,
      price: price,
      stock: isNaN(stock) ? 0 : stock,
    };

    onAddProduct(productToAdd);
    setNewProduct({
      "pid": "",
      "price": "",
      "stock": 0,
      "active": true,
    });
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

          <Text>Product Name</Text>
          <Picker
            selectedValue={newProductName}
            onValueChange={handleProductChange}
            style={styles.picker}
          >
            <Picker.Item label="Select a product..." value="" />
            {availableProducts.map((product) => (
              <Picker.Item
                key={product.pid}
                label={product.name}
                value={product.name}
              />
            ))}
          </Picker>
          <Text>Price</Text>
          <TextInput
            style={styles.input}
            placeholder="Price"
            value={newProduct.price}
            keyboardType="numeric"
            onChangeText={(text) =>
              setNewProduct({ ...newProduct, price: text })
            }
          />

          <View style={styles.quantityContainer}>
            <Text>Current Stock</Text>
            <View style={styles.quantityControls}>
              <TouchableOpacity
                onPress={() =>
                  setNewProduct({
                    ...newProduct,
                    stock: Math.max(newProduct.stock - 1, 0),
                  })
                }
              >
                <Ionicons
                  name="remove-circle-outline"
                  size={24}
                  color="#0C5E52"
                />
              </TouchableOpacity>
              <Text style={styles.quantityText}>{newProduct.stock}</Text>
              <TouchableOpacity
                onPress={() =>
                  setNewProduct({ ...newProduct, stock: newProduct.stock + 1 })
                }
              >
                <Ionicons name="add-circle-outline" size={24} color="#0C5E52" />
              </TouchableOpacity>
            </View>
          </View>
          <TouchableOpacity
            style={styles.modalAddButton}
            onPress={handleAddProduct}
          >
            <Text style={styles.modalAddButtonText}>Add New Product</Text>
          </TouchableOpacity>
        </View>
      </View>

      <Alert
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
    marginBottom: 10,
    color: "#0C5E52",
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
