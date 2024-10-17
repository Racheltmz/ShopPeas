import React from "react";
import { StyleSheet, View, Text, Image, TouchableOpacity } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { Bold } from "lucide-react-native";
import { useUserStore } from "../../lib/userStore";
import { FirebaseAuth } from "../../lib/firebase";

const ProfileDetails = ({ navigation, onToggle, userData }) => {
  const fullAddress = `${userData.streetName}, ${userData.unitNo}, ${userData.city}, ${userData.postalCode}`;

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity
          style={styles.backButton}
          onPress={() => navigation.goBack()}
        >
          <Ionicons name="arrow-back" size={24} color="#0C5E52" />
        </TouchableOpacity>
        <Image
          source={require("../../../assets/imgs/DummyImage.jpg")} // Replace with actual image path
          style={styles.profilePicture}
        />
        <View>
          <Text style={styles.name}>{userData.name}</Text>
          <Text style={styles.followingCount}>
            {userData.following} following
          </Text>
        </View>
        {/* <Image
          source={require('../../../assets/imgs/DummyImage.jpg')} // Replace with actual image path
          style={styles.peaIcon}
        /> */}
      </View>
      <View style={styles.accountDetails}>
        <View style={styles.accountHeader}>
          <Text style={styles.accountTitle}>Account Details</Text>
          <TouchableOpacity onPress={onToggle}>
            <Ionicons name="create-outline" size={28} color="#0C5E52" />
          </TouchableOpacity>
        </View>
        <View style={styles.detailText}>
          <Text style={styles.detailTextHeaders}>Email: </Text>
          <Text>{userData.email}</Text>
        </View>
        <View style={styles.detailText}>
          <Text style={styles.detailTextHeaders}>Contact: </Text>
          <Text>{userData.contact}</Text>
        </View>
        <View style={styles.detailText}>
          <Text style={styles.detailTextHeaders}>Location: </Text>
          <Text>{fullAddress}</Text>
        </View>
        <View style={styles.detailText}>
          <Text style={styles.detailTextHeaders}>Date Joined: </Text>
          <Text>{userData.dateJoined}</Text>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 20,
    paddingBottom: 0,
    margin: 10,
    backgroundColor: "white",
    borderRadius: 10,
  },
  header: {
    flexDirection: "row",
    alignItems: "center",
    gap: "5",
    paddingLeft: "10%",
  },
  backButton: {
    position: "absolute",
    top: 0,
    left: 0,
  },

  profilePicture: {
    width: 80,
    height: 80,
    borderRadius: 40,
    marginRight: 15,
  },
  name: {
    fontSize: 20,
    fontWeight: "bold",
  },
  followingCount: {
    color: "#666",
  },
  peaIcon: {
    width: 30,
    height: 30,
    marginLeft: "auto",
  },
  accountDetails: {
    padding: 15,
    borderRadius: 10,
  },
  accountHeader: {
    flexDirection: "row",
    gap: 10,
    alignItems: "center",
    marginBottom: 10,
  },
  accountTitle: {
    fontSize: 18,
    fontWeight: "bold",
  },
  detailText: {
    marginBottom: 5,
    flexDirection: "row",
    flexWrap: "wrap",
  },
  detailTextHeaders: {
    fontWeight: "bold",
    color: "#0C5E52",
  },
});

export default ProfileDetails;
