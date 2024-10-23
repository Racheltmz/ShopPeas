import React, { useEffect } from "react";
import ProfileDetails from "../../components/customers/ProfileDetails";
import { createStackNavigator } from '@react-navigation/stack';
import { useUserStore } from "../../lib/userStore";
import ProfileEdit from "../../components/customers/ProfileEdit";
import consumerService from "../../service/ConsumerService";

const Profile = ({ navigation }) => {
  const { currentUser, userUid, userAddress, paymentDetails} = useUserStore();
  const Stack = createStackNavigator();
  const [userData, setUserData] = [{
    name: currentUser.first_name + " " + currentUser.last_name,
    email: currentUser.email,
    contact: currentUser.phone_number,
    dateJoined: currentUser.signupDate,
    streetName: '',
    unitNo: '',
    buildingName: '',
    city: '',
    postalCode: '',
  }];

  // todo: update this in line with user store
  const fetchData = async (userUid) => {
    await consumerService.viewProfile(userUid)
      .then((res) => {
        console.log(res);
        if (res.consumerAddress.buildingName === null) {
          res.consumerAddress.buildingName = '';
        }
        const profile = {
          name: currentUser.first_name + " " + currentUser.last_name,
          email: currentUser.email,
          contact: currentUser.phone_number,
          dateJoined: currentUser.signupDate,
          streetName: res.consumerAddress.street_name,
          unitNo: res.consumerAddress.unit_no,
          buildingName: res.consumerAddress.building_name,
          city: res.consumerAddress.city,
          postalCode: res.consumerAddress.postal_code,
        }
        setUserData(profile);
      })
  }

  useEffect(() => {
    fetchData(userUid);
  }, [userUid, userData]);

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
