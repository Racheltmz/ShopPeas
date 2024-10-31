import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Image,
  ScrollView,
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { useUserStore } from "../../lib/userStore";


const ProfileEdit = ({ route, navigation }) => {
  const { currentUser } = useUserStore();
  const { userData, onSave } = route.params;
  const [formData, setFormData] = useState({ ...userData });
  

  const handleChange = (key, value) => {
    setFormData((prev) => ({ ...prev, [key]: value }));
  };

  const handleSave = async () => {  
    const data = {
      consumer: {
        "first_name": formData.firstName,
        "last_name": formData.lastName,
        "phone_number": formData.contact,
        "email": currentUser.email,
      },
      consumerAddress: {
        "street_name": formData.streetName,
        "unit_no": formData.unitNo,
        "building_name": formData.buildingName,
        "postal_code": formData.postalCode,
        "city": formData.city,
      },
    };  
    try {
      await onSave(data);
    } catch(error) {
      console.error('Error saving:', error);
    }
  };

  return (
    <ScrollView style={styles.container}>
      <View style={{backgroundColor: "#D6E8A4"}}>
        <View style={styles.header}>
          <TouchableOpacity onPress={() => navigation.goBack()}>
            <Ionicons name="arrow-back" size={24} color="#0C5E52" />
          </TouchableOpacity>
          <Text style={styles.headerTitle}>Edit Account Details</Text>
        </View>
        <View style={styles.profileCard}>
          <Image
            source={require('../../../assets/imgs/profile.png')}
            style={styles.profilePicture}
          />
          <Text style={styles.name}>{userData.firstName}</Text>
          <Text style={styles.name}>{userData.lastName}</Text>
          <Text style={styles.dateJoined}>
            Date Joined: {userData.dateJoined}
          </Text>
        </View>
      </View>


      <View style={{paddingVertical: "3%", paddingHorizontal: "10%"}}>

        <Text style={styles.label}>Contact Details:</Text>
        <TextInput
          style={styles.input}
          value={formData.contact}
          onChangeText={(text) => handleChange("contact", text)}
          placeholder="Phone No."
          keyboardType="phone-pad"
        />
        {/* <TextInput
          style={styles.input}
          value={formData.email}
          onChangeText={(text) => handleChange("email", text)}
          placeholder="Email"
          keyboardType="email-address"
        /> */}

        <Text style={styles.label}>Address:</Text>
        <TextInput
          style={styles.input}
          value={formData.streetName}
          onChangeText={(text) => handleChange("streetName", text)}
          placeholder="Street Name"
        />
        <TextInput
          style={styles.input}
          value={formData.unitNo}
          onChangeText={(text) => handleChange("unitNo", text)}
          placeholder="Unit No."
        />
        <TextInput
          style={styles.input}
          value={formData.buildingName}
          onChangeText={(text) => handleChange("buildingName", text)}
          placeholder="Building Name"
        />
        <TextInput
          style={styles.input}
          value={formData.city}
          onChangeText={(text) => handleChange("city", text)}
          placeholder="City"
        />
        <TextInput
          style={styles.input}
          value={formData.postalCode}
          onChangeText={(text) => handleChange("postalCode", text)}
          placeholder="Postal Code"
          keyboardType="numeric"
        />

        <View style={styles.buttonContainer}>
          <TouchableOpacity style={styles.saveButton} onPress={handleSave}>
            <Text style={styles.buttonText}>Save</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={styles.cancelButton}
            onPress={() => navigation.goBack()}
          >
            <Text style={styles.buttonText}>Cancel</Text>
          </TouchableOpacity>
        </View>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
  header: {
    padding: 20,
    marginTop: "10%",
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 20,
  },
  headerTitle: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#0C5E52",
    marginLeft: 20,
  },
  profileCard: {
    alignItems: "center",
    marginBottom: 20,
  },
  profilePicture: {
    width: 80,
    height: 80,
    borderRadius: 40,
    marginBottom: 10,
  },
  name: {
    fontSize: 22,
    fontWeight: "bold",
    color: "#0C5E52",
  },
  dateJoined: {
    fontSize: 16,
    color: "#666",
    marginTop: 8,
  },
  label: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#0C5E52",
    marginTop: 10,
    marginBottom: 5,
  },
  picker: {
    backgroundColor: "white",
    borderRadius: 5,
    marginBottom: 10,
  },
  input: {
    backgroundColor: "white",
    borderRadius: 5,
    padding: 10,
    marginBottom: 10,
    borderLeftWidth: 1,
    borderRightWidth: 1,
    borderBottomWidth: 1,
    borderTopWidth: 1,
    borderColor: "#0C5E52",
  },
  buttonContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginTop: 20,
  },
  saveButton: {
    backgroundColor: "#4CAF50",
    padding: 10,
    borderRadius: 5,
    flex: 1,
    marginRight: 10,
  },
  cancelButton: {
    backgroundColor: "#f0f0f0",
    padding: 10,
    borderRadius: 5,
    flex: 1,
    marginLeft: 10,
  },
  buttonText: {
    color: "white",
    textAlign: "center",
    fontWeight: "bold",
  },
});

export default ProfileEdit;