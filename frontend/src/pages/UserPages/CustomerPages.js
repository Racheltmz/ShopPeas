import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createStackNavigator } from '@react-navigation/stack';
import Explore from '../CustomerPages/Explore';
import ProductDetails from '../CustomerPages/ProductDetails';
import Profile from '../CustomerPages/Profile';
import History from '../CustomerPages/History';
import Cart from '../CustomerPages/Cart';

const Tab = createBottomTabNavigator();
const Stack = createStackNavigator();

const ExploreStack = () => (
  <Stack.Navigator screenOptions={{ headerShown: false }}>
    <Stack.Screen name="ExploreMain" component={Explore} />
    <Stack.Screen name="ProductDetails" component={ProductDetails} />
  </Stack.Navigator>
);

const CustomerPages = () => {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="MainTabs">
        {() => (
          <Tab.Navigator
            initialRouteName='Explore'
            screenOptions={{
              tabBarStyle: { backgroundColor: "#0C5E52"},
              headerShown: false,
            }}
          >
            <Tab.Screen name="Explore" component={ExploreStack} />
            <Tab.Screen name="Cart" component={Cart} />
            <Tab.Screen name="History" component={History} />
            <Tab.Screen name="Profile" component={Profile} />
          </Tab.Navigator>
        )}
      </Stack.Screen>
      <Stack.Screen name="Cart" component={Cart} />
    </Stack.Navigator>
  );
};

export default CustomerPages;