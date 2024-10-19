import { StyleSheet, View, Text } from 'react-native'
import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createStackNavigator } from '@react-navigation/stack';
import { Ionicons } from '@expo/vector-icons';
import Home from '../WholesalerPages/Home';
import Transactions from '../WholesalerPages/Transactions';
import Profile from '../WholesalerPages/Profile';
import ProfileEdit from '../WholesalerPages/ProfileEdit';

const Tab = createBottomTabNavigator();
const Stack = createStackNavigator();

const WholesalerPages = () => {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="MainTabs">
        {() => (
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
         </Stack.Screen>
         <Stack.Screen name="ProfileEdit" component={ProfileEdit} />
       </Stack.Navigator>
  )}

export default WholesalerPages;

const styles = StyleSheet.create({
  tabBarStyle: {
    height: '8%',
    backgroundColor: "#E2ECEA",
    paddingBottom: 10,
  },
  tabBarItemStyle:{
    margin: 5,
    borderRadius: 10,
  }
})