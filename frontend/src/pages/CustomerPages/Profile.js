import React, { useState } from "react";
import {
  StyleSheet,
  View,
} from "react-native";
import ProfileDetails from "../../components/customers/ProfileDetails";
import { useUserStore } from "../../lib/userStore";

const Profile = ({ navigation }) => {
  const [activeTab, setActiveTab] = useState("likes");
  const [isEditModalVisible, setIsEditModalVisible] = useState(false);
  const { currentUser, userAddress } = useUserStore();

  // You would typically fetch this data from your user state or props
  console.log(userAddress)
  const [userData, setUserData] = [{
    name: currentUser.first_name + " " + currentUser.last_name,
    following: 19,
    email: currentUser.email,
    contact: currentUser.phone_number,
    dateJoined: currentUser.signupDate,
    streetName: userAddress.street_name,
    unitNo: userAddress.unit_no,
    buildingName: userAddress.building_name,
    city: userAddress.city,
    postalCode: userAddress.postal_code,
  }];

  const handleSaveProfile = (updatedUserData) => {
    // This is where you would typically make an API call to update the user's profile
    console.log("Saving updated user data:", updateqdUserData);
    // setUserData({ ...userData, ...updatedUserData });
    // Here you would handle the response from the API, update local state, etc.
  };


  return (
    // The use of SafeAreaView in the Profile component ensures that the content doesn't overlap with the device's notches or status bar.
    <View style={styles.container}>
      <ProfileDetails 
        onSave={handleSaveProfile}
        userData={userData}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default Profile;
