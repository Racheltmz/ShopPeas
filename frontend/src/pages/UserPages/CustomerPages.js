import { StyleSheet, View, Text} from 'react-native'
import Explore from '../CustomerPages/Explore';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Profile from '../CustomerPages/Profile';
import History from '../CustomerPages/History';

const Tab = createBottomTabNavigator();

const CustomerPages = () => {
  return (
      <Tab.Navigator
        initialRouteName='Explore'
        screenOptions={{
          tabBarStyle: { backgroundColor: "#0C5E52"},
          headerShown: false,

        }}
        >
        <Tab.Screen 
            name="Explore"
            component={Explore}
            />
        <Tab.Screen 
            name="Updates"
            component={Explore}
            />
        <Tab.Screen 
            name="Cart"
            component={Explore}
            />
        <Tab.Screen 
            name="History"
            component={History}
            />
        <Tab.Screen 
            name="Profile"
            component={Profile}
            />
      </Tab.Navigator>
  )
}

export default CustomerPages;

// const styles = StyleSheet.create({
//   backgroundImage: {
//     flex: 1,
//     width: '100%',
//     height: '100%',
//   },
//   tabBar: {
//     backgroundColor: 'rgba(255, 255, 255, 0.8)', // Semi-transparent white
//   },
// });