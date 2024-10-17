import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  Image,
  ScrollView,
  TouchableOpacity,
} from "react-native";
import { useNavigation } from "@react-navigation/native";
import { Ionicons } from "@expo/vector-icons";
import { useUserStore } from "../../lib/userStore";
import ProductItem from "./ProductItem";
import Alert from '../utils/Alert';
import wholesalerService from "../../service/WholesalerService";

const ViewWholesaler = ({ route }) => {
  const navigation = useNavigation();
  const { userUid } = useUserStore();
  const { wholesalerUEN } = route.params;
  const [alertVisible, setAlertVisible] = useState(false);
  const [isGridView, setIsGridView] = useState(true);
  const [customAlert, setCustomAlert] = useState({ title: '', message: '', onConfirm: () => {} });
  const [wholesalerInfo, setWholesalerInfo] = useState({
    name: '',
    location: '',
    address: '',
    averageRating: 0,
    ratingCounts: [],
    products: [],
  });

  useEffect(() => {
    wholesalerService.viewWholesaler(userUid, wholesalerUEN)
      .then((res) => {
        const data = {
          name: res.wholesaler.name,
          location: "Bishan, 39 Minutes away",
          address: `${res.wholesalerAddress.street_name}, ${res.wholesalerAddress.unit_no}\n${res.wholesalerAddress.city}, ${res.wholesalerAddress.postal_code}`,
          averageRating: res.wholesaler.rating.toFixed(1),
          ratingCounts: Array.from(res.wholesaler.num_ratings),
          products: Array.from(res.wholesalerProducts),
        }
        setWholesalerInfo(data);
      })
      .catch((err) => {
        setAlertVisible(true);
        <Alert
            visible={alertVisible}
            title={err.response.status}
            message={err.message}
            onConfirm={() => {
              setAlertVisible(false);
              customAlert.onConfirm();
            }}
            onCancel={() => setAlertVisible(false)}
        />
      });
  }, [userUid, wholesalerUEN]);

  const handleProductPress = (item) => {
    navigation.navigate('ProductDetails', { product: item });
  }

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity
          style={styles.backButton}
          onPress={() => navigation.goBack()}
        >
          <Ionicons name="arrow-back" size={24} color="#0C5E52" />
        </TouchableOpacity>
        <View style={styles.headerContent}>
          <View style={styles.headerInfo}>
            <Text style={styles.wholesalerName}>{wholesalerInfo.name}</Text>
            <Text style={styles.location}>📍{wholesalerInfo.location}</Text>
            <Text style={styles.addressHeader}>Address</Text>
            <Text style={styles.address}>{wholesalerInfo.address}</Text>
          </View>
          <Image
            source={require("../../../assets/imgs/profile.png")}
            style={styles.avatar}
          />
        </View>
      </View>
      <ScrollView>
        <View style={styles.body}>
          <Text style={styles.sectionTitle}>Ratings</Text>
          <View style={styles.ratingSection}>
            <View style={styles.ratingOverview}>
              <View style={styles.starRating}>
                <Ionicons name="star" size={24} color="#FFD700" />
                <Text style={styles.averageRating}>
                  {wholesalerInfo.averageRating}
                </Text>
              </View>
              <Text style={styles.averageRatingText}>Average Rating</Text>
            </View>
            <View style={styles.ratingBars}>
              {wholesalerInfo.ratingCounts.map((count, index) => (
                <View key={index} style={styles.ratingBar}>
                  <Text style={styles.ratingNumber}>{5 - index}</Text>
                  <View style={styles.barContainer}>
                    <View
                      style={[
                        styles.bar,
                        {
                          width: `${(count / Math.max(...wholesalerInfo.ratingCounts)) *
                            100
                            }%`,
                        },
                      ]}
                    />
                  </View>
                </View>
              ))}
            </View>
          </View>
          <Text style={styles.sectionTitle}>Products</Text>
          <View style={styles.productsSection}>
            <View style={styles.productGrid}>
              {wholesalerInfo.products.map((product, index) => (
                <ProductItem
                  key={index}
                  name={product.name}
                  packageSize={product.package_size}
                  imageUrl={product.image_url}
                  isGridView={isGridView}
                  onPress={() => handleProductPress(product)}
                />
              ))}
            </View>
          </View>
        </View>
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  backButton: {
    marginLeft: "1%",
  },
  header: {
    backgroundColor: "white",
    borderRadius: 8,
    marginBottom: "4%",
    marginBottom: 10,
    marginTop: 60,
    padding: "3%",
    marginHorizontal: "3%",
    borderRadius: "15",
  },
  headerContent: {
    flexDirection: "row",
    justifyContent: "space-around",
    alignItems: "center",
    paddingLeft: "8%",
    borderRadius: 8,
    marginBottom: "4%",
  },
  headerInfo: {
    flex: 1,
  },
  wholesalerName: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#0C5E52",
    marginTop: "3%",
  },
  location: {
    fontSize: 14,
    color: "#0C5E52",
  },
  addressHeader: {
    fontSize: 16,
    color: "#0C5E52",
    marginTop: "5%",
    fontWeight: "bold",
  },
  address: {
    fontSize: 14,
    color: "#0C5E52",
  },
  avatar: {
    width: 100,
    height: 100,
    borderRadius: 50,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: "bold",
    color: "#0C5E52",
    marginTop: 4,
    marginBottom: 16,
  },
  body: {
    backgroundColor: "white",
    borderRadius: "30",
    padding: "5%",
  },
  ratingSection: {
    backgroundColor: "#0C5E5220",
    padding: 10,
    marginBottom: 16,
    borderRadius: 8,
  },
  ratingOverview: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 16,
  },
  starRating: {
    flexDirection: "row",
    alignItems: "center",
    marginRight: 8,
  },
  averageRating: {
    fontSize: 24,
    fontWeight: "bold",
    marginLeft: 8,
  },
  averageRatingText: {
    fontSize: 16,
    color: "#444",
  },
  ratingBars: {
    marginTop: 8,
  },
  ratingBar: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 4,
  },
  ratingNumber: {
    width: 20,
    fontSize: 14,
    color: "#666",
  },
  barContainer: {
    flex: 1,
    height: 12,
    backgroundColor: "#94BEB8",
    borderRadius: 4,
  },
  bar: {
    height: 12,
    backgroundColor: "#0C5E52",
    borderRadius: 4,
    maxWidth: "100%",
  },
  productGrid: {
    flexDirection: "row",
    flexWrap: "wrap",
    justifyContent: "space-between",
  },
  productItem: {
    width: "50%",
    backgroundColor: "white",
    borderRadius: 8,
    padding: 16,
    marginBottom: 16,
    backgroundColor: "#E4F2BC",
  },
  productImagePlaceholder: {
    width: 140,
    height: 140,
    backgroundColor: "#E0E0E0",
    borderRadius: 8,
    marginBottom: 8,
  },
  productName: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#0C5E52",
    textAlign: "left",
  },
  productQuantity: {
    fontSize: 14,
    color: "#666",
    textAlign: "left",
  },
});

export default ViewWholesaler;
