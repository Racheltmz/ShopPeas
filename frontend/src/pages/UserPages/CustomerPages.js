import React, { useEffect, useState } from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createStackNavigator } from '@react-navigation/stack';
import Explore from '../CustomerPages/Explore';
import ProductDetails from '../CustomerPages/ProductDetails';
import Profile from '../CustomerPages/Profile';
import History from '../CustomerPages/History';
import Cart from '../CustomerPages/Cart';
import { Ionicons } from '@expo/vector-icons';
import { StyleSheet, Text } from 'react-native';
import Payment from '../../components/customers/Payment/Payment';
import PaymentMethod from '../../components/customers/Payment/PaymentMethod';
import AddCard from '../../components/customers/Payment/AddCard';
import ViewWholesaler from '../../components/customers/ViewWholesaler';
import cartService from '../../service/CartService';
import { useUserStore } from '../../lib/userStore';
import { useCart } from '../../lib/userCart';
import transactionService from '../../service/TransactionService';

const Tab = createBottomTabNavigator();
const Stack = createStackNavigator();

const ExploreStack = () => (
  <Stack.Navigator screenOptions={{ headerShown: false }}>
    <Stack.Screen name="ExploreMain" component={Explore} />
    <Stack.Screen name="ProductDetails" component={ProductDetails} />
    <Stack.Screen name="ViewWholesaler" component={ViewWholesaler} />
  </Stack.Navigator>
);


const CustomerPages = () => {
  const { userUid } = useUserStore();
  const { fetchCart } = useCart();
  const [ history, setHistory ] = useState([])
  
  const fetchHistory = async () => {
    try {
      const res = await transactionService.viewOrderHistory(userUid);
      const data = res.sort((a, b) => new Date(b.date) - new Date(a.date));
      setHistory(data);
    } catch (err) {
      if (err.status === 404) {
        setHistory([]);
      } else {
        console.error(err);
      }
    }
  }

  const updateHistoryRating = (tid) => {
    setHistory(prevHistory => 
      prevHistory.map(order => ({
        ...order,
        orders: order.orders.map(wholesaler => {
          if (wholesaler.tid === tid) {
            return { ...wholesaler, rated: true };
          }
          return wholesaler;
        })
      }))
    );
  };
  
  // fetch cart and history
  useEffect(() => {
    fetchCart(userUid);
    fetchHistory()
  }, [userUid]);
  
  const HistoryWrapper = () => {
    return <History historyData={history} onUpdateRating={updateHistoryRating} />;
  };
  
  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="MainTabs">
        {() => (
          <Tab.Navigator
            initialRouteName='Explore'
            screenOptions={({ route }) => ({
              tabBarIcon: ({ color, size, focused }) => {
                let iconName;
                size = 30;
                switch (route.name) {
                  case "Explore":
                    iconName = focused ? "search" : "search-outline";
                    break;
                  case "Cart":
                    iconName = focused ? "cart" : "cart-outline";
                    break;
                  case "History":
                    iconName = focused ? "time" : "time-outline";
                    break;
                  case "Profile":
                    iconName = focused ? "person" : "person-outline"
                    break;
                }
                return <Ionicons name={iconName} size={size} color={color}></Ionicons>
              },
              tabBarLabel: ({ children, color, focused }) => (
                <Text style={{
                  fontSize: 10,
                  color,
                  fontWeight: focused ? "bold" : "normal"
                }}>{children}</Text>
              ),
              tabBarStyle: styles.tabBarStyle,
              tabBarItemStyle: styles.tabBarItemStyle,
              tabBarActiveTintColor: "#FFFFFF",
              tabBarInactiveTintColor: "#EFEFEF",
              headerShown: false
            })}
          >
            <Tab.Screen name="Explore" component={ExploreStack} />
            <Tab.Screen name="Cart" component={Cart} />
            <Tab.Screen name="History" component={HistoryWrapper} />
            <Tab.Screen name="Profile" component={Profile} />
          </Tab.Navigator>
        )}
      </Stack.Screen>
      <Stack.Screen name="Cart" component={Cart} />
      <Stack.Screen name="Payment" component={Payment} />
      <Stack.Screen name="PaymentMethod" component={PaymentMethod} />
      <Stack.Screen name="AddCard" component={AddCard} />
      <Stack.Screen name="ViewWholesaler" component={ViewWholesaler} />
    </Stack.Navigator>
  );
};

const styles = StyleSheet.create({
  tabBarStyle: {
    height: "8%",
    backgroundColor: "#0C5E52",
    paddingBottom: 10,
  },
  tabBarItemStyle: {
    margin: 5,
    borderRadius: 10,
  }
})

export default CustomerPages;