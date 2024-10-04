import React, { useEffect, useState } from "react";
import {
  Modal,
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Image,
} from "react-native";
import { Ionicons } from "@expo/vector-icons";

const ProfileEdit = ({ visible, onSave, userData }) => {
  const [editMode, setEditMode] = useState(false);
  const [formData, setFormData] = useState({});

  // List of editable fields
  const editableFields = [
    "email",
    "contact",
    "streetName",
    "unitNo",
    "buildingName",
    "city",
    "postalCode",
  ];

  useEffect(() => {
    // Initialize formData with only editable fields
    const initialFormData = {};
    editableFields.forEach((field) => {
      if (userData.hasOwnProperty(field)) {
        initialFormData[field] = userData[field];
      }
    });
    setFormData(initialFormData);
  }, [userData]);

  const toggleEdit = () => {
    if (editMode) {
      // If we're exiting edit mode, revert changes
      setFormData({ ...userData });
    }
    setEditMode((prev) => !prev);
  };

  const handleChange = (key, value) => {
    setFormData((prev) => ({ ...prev, [key]: value }));
  };

  const handleSave = () => {
    
    onSave(formData);
    setEditMode(false);
  };

  const handleCancel = () => {
    setFormData({ ...userData });
    setEditMode(false);
  };

  const buttonElements = (
    <View style={styles.buttonContainer}>
      <TouchableOpacity style={styles.saveButton} onPress={handleSave}>
        <Text style={styles.saveButtonText}>Save</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.cancelButton} onPress={handleCancel}>
        <Text style={styles.cancelButtonText}>Cancel</Text>
      </TouchableOpacity>
    </View>
  );

  const renderField = (
    key,
    label,
    editable = true,
    keyboardType = "default"
  ) => (
    <View key={key}>
      <Text style={styles.label}>{label}:</Text>
      {editable ? (
        <TextInput
          style={editMode ? styles.inputEdit : styles.input}
          value={formData[key]}
          onChangeText={(text) => handleChange(key, text)}
          editable={editMode}
          keyboardType={keyboardType}
        />
      ) : (
        <Text style={styles.dateJoined}>{userData[key]}</Text>
      )}
    </View>
  );

  return (
    <View visible={visible} transparent={true}>
      <View style={styles.container}>
        <View style={styles.content}>
          <View style={styles.header}>
            <Image
              source={require("../../../assets/imgs/DummyImage.jpg")}
              style={styles.profilePicture}
            />
            <Text style={styles.name}>{userData.name}</Text>
            {/* <Image source={require('../../assets/pea-icon.png')} style={styles.peaIcon} /> */}
          </View>

          <View style={styles.accountHeader}>
            <Text style={styles.sectionTitle}>Account Details</Text>
            <TouchableOpacity onPress={toggleEdit}>
              <Ionicons name="create-outline" size={28} color="#0C5E52" />
            </TouchableOpacity>
          </View>

          {renderField("email", "Email", true, "email-address")}
          {renderField("contact", "Contact", true, "phone-pad")}
          {renderField("dateJoined", "Date Joined", false)}
          {renderField("streetName", "Street Name")}
          {renderField("unitNo", "Unit-No")}
          {renderField("buildingName", "Building Name")}
          {renderField("city", "City")}
          {renderField("postalCode", "Postal Code", true, "numeric")}

          {editMode && buttonElements}
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    justifyContent: "center",
    alignItems: "center",
  },
  content: {
    backgroundColor: "white",
    borderRadius: 20,
    paddingHorizontal: "10%",
    width: "90%",
    height: "97%",
    marginTop: "5%",
    paddingVertical: "5%",
  },
  closeButton: {
    alignSelf: "flex-end",
  },
  header: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 10,
  },
  profilePicture: {
    width: 60,
    height: 60,
    borderRadius: 30,
    marginRight: 10,
  },
  name: {
    fontSize: 20,
    fontWeight: "bold",
  },
  peaIcon: {
    width: 30,
    height: 30,
    marginLeft: "auto",
  },
  accountHeader: {
    flexDirection: "row",
    gap: 10,
    alignItems: "center",
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 10,
  },
  label: {
    fontSize: 16,
    fontWeight: "bold",
    marginTop: 10,
  },
  input: {
    borderWidth: 1,
    borderColor: "#0C5E52",
    backgroundColor: "#D9D9D9",
    borderRadius: 5,
    padding: 10,
    marginTop: 5,
  },

  inputEdit: {
    borderWidth: 1,
    borderColor: "#0C5E52",
    borderRadius: 5,
    padding: 10,
    marginTop: 5,
  },

  dateJoined: {
    fontSize: 16,
    marginTop: 5,
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
  saveButtonText: {
    color: "white",
    textAlign: "center",
    fontWeight: "bold",
  },
  cancelButton: {
    backgroundColor: "#f0f0f0",
    padding: 10,
    borderRadius: 5,
    flex: 1,
    marginLeft: 10,
  },
  cancelButtonText: {
    color: "black",
    textAlign: "center",
  },
});

export default ProfileEdit;
