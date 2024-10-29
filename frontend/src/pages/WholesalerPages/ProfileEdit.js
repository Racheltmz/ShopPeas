import React, { useState } from "react";
import {
  View,
  Text,
  Image,
  StyleSheet,
  TouchableOpacity,
  ScrollView,
  TextInput,
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { SafeAreaView } from "react-native-safe-area-context";
import RNPickerSelect from "react-native-picker-select";
import { useUserStore } from "../../lib/userStore";
import wholesalerService from "../../service/WholesalerService";

const ProfileEdit = ({ navigation }) => {
  const { currentUser, userAddress, paymentDetails, fetchUserInfo, userUid } = useUserStore();

  const [currency, setCurrency] = useState(currentUser.currency);
  const [name, setName] = useState(currentUser.name);
  const [phone, setPhone] = useState(currentUser["phone_number"]);
  const [street, setStreet] = useState(userAddress.street_name);
  const [unit, setUnit] = useState(userAddress["unit_no"]);
  const [building, setBuilding] = useState(userAddress["building_name"]);
  const [postalCode, setPostalCode] = useState(userAddress["postal_code"]);
  const [bank, setBank] = useState(paymentDetails.bank);
  const [bankNumber, setBankNumber] = useState(paymentDetails["bank_account_no"]);
  const [city, setCity] = useState(userAddress.city);

  const handleSave = async () => {
    const data = {
      wholesaler: {
        "name": name,
        "email": currentUser.email,
        "phone_number": phone,
        "currency": currency,
      },
      wholesalerAccount: {
        "bank": bank,
        "bank_account_no": bankNumber,
      },
      wholesalerAddress: {
        "street_name": street,
        "unit_no": unit,
        "building_name": building,
        "postal_code": postalCode,
        "city": city,
      },
    };  
    console.log(data);
    await wholesalerService.editProfile(userUid, data)
    .then((res) => {

    })
    await fetchUserInfo(userUid);
    navigation.goBack();
  };

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView>
        <View style={styles.header}>
          {/* Use the Ionicons arrow icon as the back button */}
          <TouchableOpacity
            onPress={() => navigation.goBack()}
            style={styles.backButton}
          >
            <Ionicons name="arrow-back" size={24} color="#0C5E52" />
          </TouchableOpacity>
          <Text style={styles.headerTitle}>Edit Account Details</Text>
        </View>

        <View style={styles.profileSummary}>
          <Image
            source={require("../../../assets/imgs/profile.png")}
            style={styles.profilePic}
          />
          <Text style={styles.name}>{currentUser.name}</Text>
        </View>

        <View style={styles.infoSection}>
          <InfoRow label="Date Joined:" value={currentUser.signupDate} />
          <InfoRow label="UEN:" value={currentUser.uen} />
        </View>

        <View style={styles.editSection}>
          <Text style={styles.sectionTitle}>Name:</Text>
          <TextInput
            style={styles.input}
            placeholder="Name."
            value={name}
            onChangeText={setName}
          />
          <View style={styles.selectInput}>
            <RNPickerSelect
              onValueChange={(value) => setCurrency(value)}
              value={currency}
              items={[{label: 'SGD', value: 'SGD'}, {label: 'MYR', value: 'MYR'}]}
              style={{
                inputIOS: {
                  height: 50,
                  paddingLeft: 10,
                  fontSize: 16,
                  color: '#000',
                },
                inputAndroid: {
                  height: 50,
                  paddingLeft: 10,
                  fontSize: 16,
                  color: '#000',
                },
              }}
              placeholder={{ label: "Select Currency", value: null }}
            />
          </View>

          <Text style={styles.sectionTitle}>Contact Details:</Text>
          <TextInput
            style={styles.input}
            placeholder="Phone No."
            value={phone}
            onChangeText={setPhone}
          />

          <Text style={styles.sectionTitle}>Address:</Text>
          <TextInput
            style={styles.input}
            placeholder="City"
            value={city}
            onChangeText={setCity}
          />
          <TextInput
            style={styles.input}
            placeholder="Street Name"
            value={street}
            onChangeText={setStreet}
          />
          <TextInput
            style={styles.input}
            placeholder="Unit No."
            value={unit}
            onChangeText={setUnit}
          />
          <TextInput
            style={styles.input}
            placeholder="Building Name"
            value={building}
            onChangeText={setBuilding}
          />
          <TextInput
            style={styles.input}
            placeholder="Postal Code"
            value={postalCode}
            onChangeText={setPostalCode}
          />

          <Text style={styles.sectionTitle}>Bank Account Details:</Text>
          <TextInput
            style={styles.input}
            placeholder="Bank"
            value={bank}
            onChangeText={setBank}
          />
          <TextInput
            style={styles.input}
            placeholder="Bank Account Number"
            value={bankNumber}
            onChangeText={setBankNumber}
          />
        </View>

        <View style={styles.buttonContainer}>
          <TouchableOpacity style={styles.saveButton} onPress={handleSave}>
            <Text style={styles.saveButtonText}>Save</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={styles.cancelButton}
            onPress={() => navigation.goBack()}
          >
            <Text style={styles.cancelButtonText}>Cancel</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const InfoRow = ({ label, value }) => (
  <View style={styles.infoRow}>
    <Text style={styles.infoLabel}>{label}</Text>
    <Text style={styles.infoValue}>{value}</Text>
  </View>
);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#F5F5F5",
  },
  header: {
    flexDirection: "row",
    alignItems: "center",
    padding: 16,
    backgroundColor: "#0C5E5240",
  },
  backButton: {
    marginRight: 16,
  },
  headerTitle: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#0C5E52",
  },
  profileSummary: {
    flexDirection: "row",
    alignItems: "center",
    padding: 16,
    backgroundColor: "#0C5E5240",
    paddingRight: 100,
  },
  profilePic: {
    width: 60,
    height: 60,
    borderRadius: 30,
    marginRight: 16,
  },
  name: {
    fontSize: 18,
    fontWeight: "bold",
    color: "#0C5E52",
  },
  infoSection: {
    flexDirection: "row",
    justifyContent: "space-between",
    padding: 16,
    backgroundColor: "#0C5E5240",
  },
  infoRow: {
    alignItems: "center",
  },
  infoLabel: {
    color: "#6B9080",
    marginBottom: 4,
  },
  infoValue: {
    color: "#0C5E52",
    fontWeight: "bold",
  },
  editSection: {
    padding: 16,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#0C5E52",
    marginTop: 16,
    marginBottom: 8,
  },
  selectInput: {
    flexDirection: "row",
    alignItems: "center",
    borderWidth: 1,
    borderColor: "#CCCCCC",
    borderRadius: 8,
    marginBottom: 12,
  },
  input: {
    flex: 1,
    padding: 12,
    fontSize: 16,
    borderRadius: 5,
    borderLeftWidth: 1,
    borderRightWidth: 1,
    borderBottomWidth: 1,
    borderTopWidth: 1,
    borderColor: "#0C5E5240",
    marginBottom: 10,
  },
  buttonContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    padding: 16,
  },
  saveButton: {
    backgroundColor: "#A8E0A4",
    paddingVertical: 12,
    paddingHorizontal: 24,
    borderRadius: 8,
  },
  saveButtonText: {
    color: "#0C5E52",
    fontWeight: "bold",
    fontSize: 16,
  },
  cancelButton: {
    backgroundColor: "#F5F5F5",
    paddingVertical: 12,
    paddingHorizontal: 24,
    borderRadius: 8,
  },
  cancelButtonText: {
    color: "#0C5E52",
    fontSize: 16,
  },
});

export default ProfileEdit;
