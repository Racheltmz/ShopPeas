import { StyleSheet, View, Text } from 'react-native'
import React from 'react';
import { BottomTabBarHeightCallbackContext, createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createStackNavigator } from '@react-navigation/stack';
import { Ionicons } from '@expo/vector-icons';
import Home from '../WholesalerPages/Home';
import Transactions from '../WholesalerPages/Transactions';
import Profile from '../WholesalerPages/Profile';

const Tab = createBottomTabNavigator();
const Stack = createStackNavigator();

const WholesalerPages = () => {
  return (
    <Tab.Navigator
      initialRouteName='Home'
      screenOptions={(
        ({route})=>({
          tabBarIcon: ({color, size, focused})=>
          {
            let iconName;
            size = 30;
            switch(route.name){
              case "Home":
                iconName = focused ? "home": "home-outline";
                break;
              case "Transactions":
                iconName = focused ? "cash": "cash-outline";
                break;
              case "Profile":
                iconName = focused ? "person": "person-outline"
            }
            return <Ionicons name={iconName} size={size} color={color}></Ionicons>
          },
          tabBarLabel: ({children, color, focused}) => (<Text style={{
            fontSize : 10,
            color,
            fontWeight: focused ? "bold": "normal"
          }}>{children}</Text>),
          tabBarStyle: styles.tabBarStyle,
          tabBarItemStyle: styles.tabBarItemStyle,
          tabBarActiveTintColor: "#0C5E52",
          tabBarInactiveTintColor: "#8cab89",
          headerShown: false
        })
      )}
    >
      <Tab.Screen name="Home" component={Home} />
      <Tab.Screen name="Transactions" component={Transactions} />
      <Tab.Screen name="Profile" component={Profile} />
    </Tab.Navigator>
  )}

export default WholesalerPages;

const styles = StyleSheet.create({
  tabBarStyle: {
    height: 60,
    backgroundColor: "#E2ECEA",
    position: 'absolute',
    bottom: 20, 
    left: 20,
    right: 20,
    borderRadius: 20,
    paddingBottom: 3,
  },
  tabBarItemStyle:{
    margin: 5,
    borderRadius: 10,
  }
})