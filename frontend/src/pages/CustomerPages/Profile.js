import React, { useState } from "react";
import {
  StyleSheet,
  View,
} from "react-native";
import ProfileDetails from "../../components/customers/ProfileDetails";
import { createStackNavigator } from '@react-navigation/stack';
import { useUserStore } from "../../lib/userStore";
import ProfileEdit from "../../components/customers/ProfileEdit";

const Profile = ({ navigation }) => {
  const { currentUser, userAddress, paymentDetails} = useUserStore();
  const Stack = createStackNavigator();

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
    console.log("Saving updated user data:", updatedUserData);
    // setUserData({ ...userData, ...updatedUserData });
    // Here you would handle the response from the API, update local state, etc.
  };


  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="ProfileDetails">
        {(props) => <ProfileDetails {...props} userData={userData} />}
      </Stack.Screen>
      <Stack.Screen name="ProfileEdit">
        {(props) => (
          <ProfileEdit
            {...props}
            route={{
              ...props.route,
              params: { ...props.route.params, userData: userData, onSave: handleSaveProfile }
            }}
          />
        )}
      </Stack.Screen>
    </Stack.Navigator>
  );
};

export default Profile;
