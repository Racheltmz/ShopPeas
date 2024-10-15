import { SafeAreaView, StyleSheet, Text, View, TextInput, TouchableOpacity, Image, FlatList} from 'react-native'
import { Ionicons } from '@expo/vector-icons';
import { useState, useMemo } from 'react';
import OrderDropdown from '../../components/wholesalers/OrderDropdown';
import PendingOrders from '../../components/wholesalers/PendingOrders';
import CompletedOrders from '../../components/wholesalers/CompletedOrders';

const Transactions = ({ defaultValue }) => {
  const [selectedOption, setSelectedOption] = useState('pending');
  // const [selectedStatus, setSelectedStatus] = useState('to_be_accepted');

  const [orders, setOrders] = useState([
    {
      id: '1029842',
      productName: 'Bok Choy',
      quantity: 7,
      imageUrl: 'https://example.com/bok-choy.jpg',
      date: '31-08-2024 13:01',
      buyerName: 'Christopher K.',
      pickupMethod: 'Self Pick-Up',
      total: 12.99,
      paid: 'PAID',
      status: 'to_be_accepted'
    },
    {
      id: '1029843',
      productName: 'Bok Choy',
      quantity: 7,
      imageUrl: 'https://example.com/bok-choy.jpg',
      date: '31-08-2024 13:01',
      buyerName: 'Christopher K.',
      pickupMethod: 'Self Pick-Up',
      total: 12.99,
      paid: 'PAID',
      status: 'to_be_accepted'
    },
    {
      id: '1029844',
      productName: 'Tomatoes',
      quantity: 3,
      imageUrl: 'https://example.com/tomatoes.jpg',
      date: '31-08-2024 13:45',
      buyerName: 'Ray Shyuan',
      pickupMethod: 'Self Pick-Up',
      total: 15.78,
      paid: 'PAID',
      status: 'to_be_completed'
    },
  ]);

  const options = [
    { label: 'Pending Orders', value: 'pending' },
    { label: 'Completed Orders', value: 'completed' },
  ];

  const handleAccept = (orderId) => {
    setOrders(prevOrders =>
      prevOrders.map(order =>
        order.id === orderId
          ? { ...order, status: 'to_be_completed' }
          : order
      )
    );
  };

  const handleComplete = (orderId) => {
    setOrders(prevOrders =>
      prevOrders.map(order =>
        order.id === orderId
          ? { ...order, status: 'completed' }
          : order
      )
    );
  };

  return (
    <SafeAreaView style = {styles.container}>
        <View style={styles.searchBar}>
            <Ionicons name="search" size={24} color="#0C5E52" />
            <TextInput 
            style={styles.searchInput}
            placeholder="Search Orders"
            placeholderTextColor="#0C5E52"
            />
        </View>
        <View style={styles.header}>
          <View style={styles.headerTextContainer}>
              <Text style={styles.headerTitle}>Happy Wholesaler</Text>
              <Text style={styles.subHeaderTitle}>Transactions</Text>
          </View>
          <Image
              source={require('../../../assets/imgs/pea.png')}
              style={styles.headerImage}
          />
        </View>
      <OrderDropdown 
        options={options}
        defaultValue="pending"
        onSelect={setSelectedOption}
      />
      {selectedOption === 'pending' && (
        <PendingOrders
          orders={orders}
          onAccept={handleAccept}
          onComplete={handleComplete}
        />
      )}
      {selectedOption === 'completed' && (
        <CompletedOrders
          orders={orders}
          onAccept={handleAccept}
          onComplete={handleComplete}
        />
      )}
    </SafeAreaView>
  )
}

export default Transactions;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  searchBar: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#E2ECEA',
    margin: 10,
    padding: 10,
    borderRadius: 25,
  },
  searchInput: {
    flex: 1,
    marginLeft: 10,
    color: '#0C5E52',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginLeft: '5%',
    marginTop: '1%',
  },
  headerTitle: {
    fontFamily: "Amiko, Noto Sans",
    fontSize: 25,
    fontWeight: 'normal',
    color: '#0C5E52',
    marginLeft: '2%',
  },
  subHeaderTitle: {
    fontFamily: "Amiko, Noto Sans",
    fontSize: 35,
    fontWeight: 'bold',
    color: '#0C5E52',
    margin: '2%',
  },
  headerImage: {
    width: '18%', 
    height: '60%',
    marginRight: "26.2%",
  },
})