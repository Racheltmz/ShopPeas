import React from "react";
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

const ViewWholesaler = ({ route }) => {
  const navigation = useNavigation();
  const { wholesalerName } = route.params;

  // Dummy data (replace with actual data fetching logic later)
  const wholesalerData = {
    name: wholesalerName,
    location: "Bishan, 39 Minutes away",
    address: "123 Bishan Street 10, #01-45\nHappy Building\nS23491",
    averageRating: 4.9,
    ratingCounts: [100, 50, 10, 5, 2],
    products: [
      { name: "Bok Choy", quantity: "1 Packet", id: 1 },
      { name: "Tomatoes", quantity: "1 Packet", id: 2 },
      { name: "Spinach", quantity: "1 Packet", id: 3 },
      { name: "Carrots", quantity: "1 Kg", id: 4 },
    ],
  };

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
            <Text style={styles.wholesalerName}>{wholesalerData.name}</Text>
            <Text style={styles.location}>📍{wholesalerData.location}</Text>
            <Text style={styles.addressHeader}>Address</Text>
            <Text style={styles.address}>{wholesalerData.address}</Text>
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
                  {wholesalerData.averageRating}
                </Text>
              </View>
              <Text style={styles.averageRatingText}>Average Rating</Text>
            </View>
            <View style={styles.ratingBars}>
              {wholesalerData.ratingCounts.map((count, index) => (
                <View key={index} style={styles.ratingBar}>
                  <Text style={styles.ratingNumber}>{5 - index}</Text>
                  <View style={styles.barContainer}>
                    <View
                      style={[
                        styles.bar,
                        {
                          width: `${(count / Math.max(...wholesalerData.ratingCounts)) *
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
              {wholesalerData.products.map((product, index) => (
                <TouchableOpacity
                  onPress={() => { handleProductPress(product) }}
                >
                  <View key={index} style={styles.productItem}>
                    <Image style={styles.productImagePlaceholder} source={require("../../../assets/imgs/DummyImage.jpg")} />
                    <Text style={styles.productName}>{product.name}</Text>
                    <Text style={styles.productQuantity}>{product.quantity}</Text>
                  </View>
                </TouchableOpacity>
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
