import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { useNavigation } from "@react-navigation/native";
import { useUserStore } from '../../lib/userStore';
import Loader from '../../components/utils/Loader';
import HistoryItems from '../../components/customers/HistoryItems';
import transactionService from '../../service/TransactionService';

const History = () => {
  const navigation = useNavigation();
  const { userUid } = useUserStore();
  const [loading, setLoading] = useState(true);
  const [history, setHistory] = useState([]);

  const fetchData = async (userUid) => {
    await transactionService.viewOrderHistory(userUid)
      .then((res) => {
        setHistory(res);
        setLoading(false);
      })
      .catch((err) => {
        if (err.status === 404) {
          setHistory([]);
        }
      });
  }

  useEffect(() => {
    fetchData(userUid);
  }, [userUid]);

  if (loading) {
    return (
      <Loader loading={loading}></Loader>
    )
  }

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>Order History</Text>
      </View>
      <HistoryItems navigation={navigation} historyList={history} />
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