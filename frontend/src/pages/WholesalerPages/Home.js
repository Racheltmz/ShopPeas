import React, { useCallback, useEffect, useState, useMemo } from "react";
import {
  StyleSheet,
  Text,
  View,
  ScrollView,
  TouchableOpacity,
  TextInput,
  SafeAreaView,
  Image,
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { useNavigation } from "@react-navigation/native";
import { useUserStore } from "../../lib/userStore";
import Fuse from "fuse.js";
import WholesalerProduct from "../../components/wholesalers/WholesalerProduct";
import AddProduct from "../../components/wholesalers/AddProduct";
import productService from "../../service/ProductService";
import Loader from "../../components/utils/Loader";
import Alert from "../../components/utils/Alert";

const Home = () => {
  const navigation = useNavigation();
  const { currentUser, userUid } = useUserStore();
  const [searchText, setSearchText] = useState("");
  const [showAddProduct, setShowAddProduct] = useState(false);
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [alertVisible, setAlertVisible] = useState(false);
  const [customAlert, setCustomAlert] = useState({
    title: "",
    message: "",
    onConfirm: () => {},
  });

  const showAlert = (title, message, onConfirm) => {
    setCustomAlert({ title, message, onConfirm });
    setAlertVisible(true);
  };

  const fetchProducts = async (userUid) => {
    try {
      const res = await productService.getProductsByUEN(userUid);
      setProducts(res);
      setFilteredProducts(res);
      return res;
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    const initializePage = async () => {
      setLoading(true);
      await fetchProducts(userUid);
      setLoading(false);
    };

    initializePage();
  }, [userUid]);

  const navigateToProfile = () => {
    navigation.navigate("Profile");
  };

  // Use useCallback for handleAddProduct to prevent re-renders
  const handleAddProduct = async (newProduct) => {
    setLoading(true);
    try {
      await productService.addWholesalerProduct(userUid, newProduct);
      setProducts((currentProducts) => [...currentProducts, newProduct]);
      await fetchProducts(userUid);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
      showAlert("SUCCESS", "New Product Added!", setAlertVisible(false))
    }
  };

  const editProduct = async (productName, updatedProduct) => {
    setLoading(true);
    const selectedProductswpid = products.find(
      (product) => product.name === productName
    )?.swp_id;

    try {
      await productService.editWholesalerProduct(
        userUid,
        selectedProductswpid,
        updatedProduct
      );
      setProducts((currentProducts) =>
        currentProducts.map((product) =>
          product.swp_id === selectedProductswpid
            ? {
                ...product,
                price: updatedProduct.price,
                stock: updatedProduct.stock,
              }
            : product
        )
      );
      await fetchProducts(userUid);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
      showAlert("SUCCESS", "Product Edited!", setAlertVisible(false))
    }
  };

  const removeProduct = async (index) => {
    setLoading(true);
    try {
      await productService.deleteWholesalerProduct(userUid, products[index].swp_id)
      setProducts((currentProducts) =>
        currentProducts.filter((_, i) => i !== index)
      );
      await fetchProducts(userUid);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
        showAlert("SUCCESS", "Product Removed!", setAlertVisible(false))
      }
  };

  const fuse = useMemo(() => {
    return new Fuse(products, {
      // fields to search
      keys: ["name"],
      threshold: 0.3, // Adjust this value to make the search more or less strict
      includeScore: true,
    });
  }, [products]);

  const handleSearch = (query) => {
    setSearchText(query);
    if (query.trim() === "") {
      setFilteredProducts(products);
    } else {
      const results = fuse.search(query);
      setFilteredProducts(results.map((result) => result.item));
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      {loading && <Loader loading={loading}></Loader>}
      <View style={styles.searchBar}>
        <Ionicons name="search" size={24} color="#0C5E52" />
        <TextInput
          style={styles.searchInput}
          placeholder="Search Products"
          placeholderTextColor="#0C5E52"
          value={searchText}
          autoCapitalize="none"
          onChangeText={(text) => handleSearch(text)}
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
          source={require("../../../assets/imgs/pea.png")}
          style={styles.headerImage}
        />
      </View>

      <View style={styles.addProductContainer}>
        <TouchableOpacity
          style={styles.addButton}
          onPress={() => setShowAddProduct(true)}
        >
          <Ionicons name="add-circle-outline" size={24} color="white" />
        </TouchableOpacity>
        <Text style={styles.addButtonText}>Add New Product</Text>
        <TouchableOpacity style={styles.filterButton}>
          <Ionicons name="funnel-outline" size={30} color="#0C5E52" />
        </TouchableOpacity>
      </View>
      <ScrollView style={styles.productList}>
        {filteredProducts.map((product, index) => (
          <WholesalerProduct
            key={product.pid}
            index={index}
            name={product.name}
            price={product.price}
            unit={product.package_size}
            stock={[product.stock]}
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
        products={products}
        uen={currentUser.uen}
      />

      <Alert
        visible={alertVisible}
        title={customAlert.title}
        message={customAlert.message}
        onConfirm={() => {
          setAlertVisible(false);
          customAlert.onConfirm;
        }}
      />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
  searchBar: {
    flexDirection: "row",
    alignItems: "center",
    backgroundColor: "#E2ECEA",
    margin: 10,
    padding: 10,
    borderRadius: 25,
  },
  searchInput: {
    flex: 1,
    marginLeft: 10,
    color: "#0C5E52",
  },
  header: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    marginLeft: "5%",
    marginTop: "1%",
  },
  headerTitle: {
    fontFamily: "Amiko, Noto Sans",
    fontSize: 25,
    fontWeight: "normal",
    color: "#0C5E52",
    marginLeft: "2%",
  },
  subHeaderTitle: {
    fontFamily: "Amiko, Noto Sans",
    fontSize: 35,
    fontWeight: "bold",
    color: "#0C5E52",
    margin: "2%",
  },
  headerImage: {
    width: "18%",
    height: "65%",
    marginRight: "29.2%",
  },
  addProductContainer: {
    flexDirection: "row",
    alignItems: "center",
    marginTop: "1%",
    marginLeft: "6%",
    marginBottom: "1%",
  },
  addButton: {
    backgroundColor: "#FF7B5F",
    padding: 5,
    borderRadius: 10,
    justifyContent: "center",
    alignItems: "center",
  },
  addButtonText: {
    color: "#0C5E52",
    marginLeft: 10,
    fontSize: "20%",
    fontWeight: "normal",
  },
  filterButton: {
    padding: "1%",
    marginLeft: "auto",
    marginRight: "3%",
  },
});

export default Home;
