import React, { useState } from "react";
import ProfileDetails from "../../components/customers/ProfileDetails";
import { createStackNavigator } from '@react-navigation/stack';
import { useUserStore } from "../../lib/userStore";
import ProfileEdit from "../../components/customers/ProfileEdit";
import consumerService from "../../service/ConsumerService";

const Profile = ({ navigation }) => {
  const { currentUser, userUid, userAddress, paymentDetails, fetchUserInfo } = useUserStore();
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

  const handleSaveProfile = async (updatedUserData, navigation) => {
    try {
      // First update the backend
      await consumerService.editProfile(userUid, updatedUserData);
      
      // Update local state immediately
      setUserData(prevData => ({
        ...prevData,
        ...updatedUserData
      }));
      
      // Batch update user info
      await fetchUserInfo(userUid);
      
      // Navigate back only after all updates are complete
      navigation.goBack();
      
    } catch (error) {
      console.error('Error updating profile:', error);
      // Handle error (maybe show an alert)
    }
  };

  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="ProfileDetails">
        {(props) => <ProfileDetails {...props} userData={userData} />}
      </Stack.Screen>
      <Stack.Screen name="ProfileEdit" >
        {(props) => (
          <ProfileEdit
            {...props}
            route={{
              ...props.route,
              params: { ...props.route.params, userData: userData, onSave: (updatedData) => handleSaveProfile(updatedData, props.navigation) }
            }}
          />
        )}
      </Stack.Screen>
    </Stack.Navigator>
  );
};

export default Profile;