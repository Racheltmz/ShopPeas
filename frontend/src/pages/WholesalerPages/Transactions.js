import { SafeAreaView, StyleSheet, Text, View, TextInput, TouchableOpacity, Image, FlatList} from 'react-native'
import { Ionicons } from '@expo/vector-icons';
import { useState, useMemo, useEffect } from 'react';
import { useUserStore } from '../../lib/userStore';
import { Dialog, ALERT_TYPE } from 'react-native-alert-notification';
import OrderDropdown from '../../components/wholesalers/OrderDropdown';
import PendingOrders from '../../components/wholesalers/PendingOrders';
import CompletedOrders from '../../components/wholesalers/CompletedOrders';
import transactionService from '../../service/TransactionService';

const Transactions = ({ defaultValue }) => {
  const [selectedOption, setSelectedOption] = useState('pending');
  const { currentUser, userUid } = useUserStore();
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(false);
  // const [selectedStatus, setSelectedStatus] = useState('to_be_accepted');

  const loadTransactions = async (status) => {
    if (!currentUser?.uen || !userUid) {
      console.log('Missing uen or userUid:', { uen: currentUser?.uen, userUid });
      return;
    }
  
    setLoading(true);
    try {
      const res = await transactionService.getTransactions(
        userUid, 
        currentUser.uen, 
        status === 'pending' ? 'PENDING' : 'COMPLETED'
      );
      console.log('API Response:', res);
      
      setTransactions(res);
    } catch (err) {
      console.error('Load transactions error:', err);
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
    console.log('Loading transactions for status:', selectedOption);
    loadTransactions(selectedOption);
  }, [currentUser?.uen, userUid, selectedOption]);
  
  // console.log('Filtered transactions:', filteredTransactions);


  const filteredTransactions = transactions.filter(transaction => {
    const isPending = selectedOption === 'pending' && 
      ['PENDING-ACCEPTANCE', 'PENDING-COMPLETION'].includes(transaction.status);
    
    const isCompleted = selectedOption === 'completed' && 
      transaction.status === 'COMPLETED';

    return isPending || isCompleted;
  });

  const options = [
    { label: 'Pending Orders', value: 'pending' },
    { label: 'Completed Orders', value: 'completed' },
  ];

  const handleAccept = async (orderId) => {
    try {
      setTransactions(prevTransactions =>
        prevTransactions.map(transaction =>
          transaction.id === orderId
            ? { ...transaction, status: 'PENDING-COMPLETION' }
            : transaction
        )
      );
    } catch (error) {
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
      setTransactions(prevTransactions =>
        prevTransactions.map(transaction =>
          transaction.id === orderId
            ? { ...transaction, status: 'COMPLETED' }
            : transaction
        )
      );
    } catch (error) {
      Dialog.show({
        type: ALERT_TYPE.DANGER,
        title: 'Error',
        textBody: "Failed to complete order. Please try again.",
        button: 'close',
      });
    }
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
            <Text style={styles.headerTitle}>{currentUser.name}</Text>
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
          orders={transactions}
          onAccept={handleAccept}
          onComplete={handleComplete}
          loading={loading}
        />
      )}
      {selectedOption === 'completed' && (
        <CompletedOrders
          orders={transactions}
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