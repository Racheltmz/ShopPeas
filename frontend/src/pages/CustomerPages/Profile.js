import React, { useState } from "react";
import ProfileDetails from "../../components/customers/ProfileDetails";
import { createStackNavigator } from "@react-navigation/stack";
import { useUserStore } from "../../lib/userStore";
import ProfileEdit from "../../components/customers/ProfileEdit";
import consumerService from "../../service/ConsumerService";
import CustomAlert from "../../components/utils/Alert";

const Profile = ({ navigation }) => {
  const { currentUser, userUid, userAddress, paymentDetails, fetchUserInfo } =
    useUserStore();

  const Stack = createStackNavigator();
  const [userData, setUserData] = useState({
    firstName: currentUser.first_name,
    lastName: currentUser.last_name,
    email: currentUser.email,
    contact: currentUser.phone_number,
    dateJoined: currentUser.signupDate,
    streetName: userAddress["street_name"],
    unitNo: userAddress["unit_no"],
    buildingName: userAddress["building_name"],
    city: userAddress["city"],
    postalCode: userAddress["postal_code"],
  });

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

  const handleSaveProfile = async (updatedUserData, navigation) => {
    try {
      // First update the backend
      await consumerService.editProfile(userUid, updatedUserData);

      // Update local state immediately
      setUserData((prevData) => ({
        ...prevData,
        ...updatedUserData,
      }));

      // Update global user info before showing alert
      
      // Show alert and handle navigation
      showAlert("Success!", "Profile has been updated!", async () => {
        // Use setTimeout to delay navigation until after alert is closed
        await fetchUserInfo(userUid);
        setTimeout(() => {
          navigation.goBack();
        }, 100);
      });
    } catch (error) {
      console.error("Error updating profile:", error);
      // Handle error (maybe show an alert)
      showAlert(
        "Error",
        "Failed to update profile. Please try again.",
        () => {}
      );
    }
  };

  return (
    <>
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
                params: {
                  ...props.route.params,
                  userData: userData,
                  onSave: (updatedData) =>
                    handleSaveProfile(updatedData, props.navigation),
                },
              }}
            />
          )}
        </Stack.Screen>
      </Stack.Navigator>

      <CustomAlert
        visible={alertVisible}
        title={customAlert.title}
        message={customAlert.message}
        onConfirm={customAlert.onConfirm}
      />
    </>
  );
};

export default Profile;
