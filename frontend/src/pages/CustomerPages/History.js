import React, { useCallback, useEffect, useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { useFocusEffect, useNavigation } from "@react-navigation/native";
import { useUserStore } from '../../lib/userStore';
import Loader from '../../components/utils/Loader';
import HistoryItems from '../../components/customers/HistoryItems';
import transactionService from '../../service/TransactionService';


const History = () => {
  const navigation = useNavigation();
  const { userUid } = useUserStore();
  const [loading, setLoading] = useState(true);
  const [history, setHistory] = useState([]);
  const [error, setError] = useState(null);

  const fetchHistory = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await transactionService.viewOrderHistory(userUid);
      const sortedData = res.sort((a, b) => new Date(b.date) - new Date(a.date));
      setHistory(sortedData);
    } catch (err) {
      if (err.status === 404) {
        setHistory([]);
      } else {
        setError(err.message);
        console.error('Error fetching history:', err);
      }
    } finally {
      setLoading(false);
    }
  }, [userUid]);

  // Fetch when component mounts and whenever it comes into focus
  useFocusEffect(
    useCallback(() => {
      fetchHistory();
    }, [fetchHistory])
  );

  const handleRating = async (tid) => {
    // Update local state immediately for better UX
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
    
    // Optionally fetch fresh data after a short delay
    setTimeout(() => {
      fetchHistory();
    }, 1000);
  };

  if (loading) {
    return <Loader loading={loading} />;
  }

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>Order History</Text>
      </View>
      <HistoryItems 
        navigation={navigation} 
        historyList={history} 
        onRatedItem={handleRating} 
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    padding: 15,
    backgroundColor: 'white',
    marginBottom: 10,
    marginTop: 60,
    marginHorizontal: 10,
    borderRadius: 10,
  },
  headerTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
});

export default History;