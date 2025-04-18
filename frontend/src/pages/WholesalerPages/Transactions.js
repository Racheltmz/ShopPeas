import { SafeAreaView, StyleSheet, Text, View, TextInput, TouchableOpacity, Image, FlatList} from 'react-native'
import { Ionicons } from '@expo/vector-icons';
import { useState, useMemo, useEffect } from 'react';
import { useUserStore } from '../../lib/userStore';
import { Dialog, ALERT_TYPE } from 'react-native-alert-notification';
import OrderDropdown from '../../components/wholesalers/OrderDropdown';
import PendingOrders from '../../components/wholesalers/PendingOrders';
import CompletedOrders from '../../components/wholesalers/CompletedOrders';
import transactionService from '../../service/TransactionService';
import Fuse from 'fuse.js';

const Transactions = ({ defaultValue }) => {
  const [selectedOption, setSelectedOption] = useState('pending');
  const { currentUser, userUid } = useUserStore();
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [searchText, setSearchText] = useState("");
  const [filteredTransactions, setFilteredTransactions] = useState([]);

  const loadTransactions = async () => {
    if (!currentUser?.uen || !userUid) {
      console.log('Missing uen or userUid:', { uen: currentUser?.uen, userUid });
      return;
    }
  
    setLoading(true);
    try {
      // load both statuses at once as they are on the same 'pending orders' page
      const [pendingAcceptanceRes, pendingCompletionRes, completedRes] = await Promise.all([
        transactionService.getTransactions(userUid, currentUser.uen, 'PENDING-ACCEPTANCE'),
        transactionService.getTransactions(userUid, currentUser.uen, 'PENDING-COMPLETION'),
        transactionService.getTransactions(userUid, currentUser.uen, 'COMPLETED')
      ]);
      
      const getOrders = (res, status) => {
        if (!res) return [];
        const orders = Array.isArray(res) ? res : [res];
        return orders.map(order => ({
          id: order.tid || order.id,
          status: status,
          items: Array.isArray(order.items) ? order.items : [],
          total_price: order.total_price.toFixed(2),
          currency: order.currency,
          uid: userUid
        })).filter(order => order.items.length > 0);
      };

      const pendingAcceptanceOrders = getOrders(pendingAcceptanceRes, 'PENDING-ACCEPTANCE');
      const pendingCompletionOrders = getOrders(pendingCompletionRes, 'PENDING-COMPLETION');
      const completedOrders = getOrders(completedRes, 'COMPLETED');
      
      // combine orders from both statuses
      const allOrders = [...pendingAcceptanceOrders, ...pendingCompletionOrders, ...completedOrders];
      
      setTransactions(allOrders);
      setFilteredTransactions(allOrders);
      
    } catch (err) {
      setTransactions([]); 
      Dialog.show({
        type: ALERT_TYPE.DANGER,
        title: err?.status?.code || 'Error',
        textBody: "Failed to load Transactions, please try again later.",
        button: 'close',
      });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadTransactions();
  }, [currentUser?.uen, userUid]);

  const handleAccept = async (orderId) => {
    try {
      await transactionService.updateStatus(userUid, orderId, 'PENDING-COMPLETION');
      await loadTransactions();
    } catch (error) {
      console.error('Accept order error:', error);
      Dialog.show({
        type: ALERT_TYPE.DANGER,
        title: 'Error',
        textBody: "Failed to accept order. Please try again.",
        button: 'close',
      });
    }
  };

  const handleComplete = async (orderId) => {
    try {
      await transactionService.updateStatus(userUid, orderId, 'COMPLETED');
      await loadTransactions();
    } catch (error) {
      console.error('Complete order error:', error);
      Dialog.show({
        type: ALERT_TYPE.DANGER,
        title: 'Error',
        textBody: "Failed to complete order. Please try again.",
        button: 'close',
      });
    }
  };

  const fuse = useMemo(() => new Fuse(transactions, {
    // fields to search
    keys: ['id'], 
    threshold: 0.3, // Adjust this value to make the search more or less strict
    includeScore: true
  }), [transactions]);

  const handleSearch = (query) => {
    setSearchText(query);
    if (query.trim() === '') {
      setFilteredTransactions(transactions);
    } else {
      const results = fuse.search(query);
      setFilteredTransactions(results.map(result => result.item));
    }
  };

  const options = [
    { label: 'Pending Orders', value: 'pending' },
    { label: 'Completed Orders', value: 'completed' },
  ];


  return (
    <SafeAreaView style = {styles.container}>
      <View style={styles.searchBar}>
          <Ionicons name="search" size={24} color="#0C5E52" />
          <TextInput
          style={styles.searchInput}
          placeholder="Search Orders"
          placeholderTextColor="#0C5E52"
          value={searchText}
          autoCapitalize="none"
          onChangeText={(text) => handleSearch(text)}
          />
      </View>
      <Text style={styles.headerTitle}>{currentUser.name}</Text>
      <View style={styles.header}>
        <View style={styles.headerTextContainer}>
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
        onSelect={(value) => {
          setSelectedOption(value);
          loadTransactions(value);
        }}
      />
      {selectedOption === 'pending' && (
        <PendingOrders
          orders={filteredTransactions}
          onAccept={handleAccept}
          onComplete={handleComplete}
          loading={loading}
        />
      )}
      {selectedOption === 'completed' && (
        <CompletedOrders
          orders={filteredTransactions}
          loading={loading}
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
    marginLeft: '5%',
  },
  subHeaderTitle: {
    fontFamily: "Amiko, Noto Sans",
    fontSize: 35,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  headerImage: {
    width: '18%',
    height: '100%',
    marginRight: "30%",
  },
})