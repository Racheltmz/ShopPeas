import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { useNavigation } from "@react-navigation/native";
import { Dialog, ALERT_TYPE } from 'react-native-alert-notification';
import { useUserStore } from '../../lib/userStore';
import Loader from '../../components/utils/Loader';
import HistoryItems from '../../components/customers/HistoryItems';
import transactionService from '../../service/TransactionService';

const History = () => {
  const navigation = useNavigation();
  const { userUid } = useUserStore();
  const [loading, setLoading] = useState(true);
  const [history, setHistory] = useState([]);
  const [rated, setRated] = useState(false);

  const fetchData = async (userUid) => {
    await transactionService.viewOrderHistory(userUid)
      .then((res) => {
        setHistory(res);
        setLoading(false);
      })
      .catch((err) => {
        setLoading(false);
        if (err.status === 404) {
          setHistory([]);
        } else {
          Dialog.show({
            type: ALERT_TYPE.DANGER,
            title: err.status.code,
            textBody: err.message,
            button: 'close',
          })
        }
      });
  }
  
  const updateRated = () => {
    setRated(true);
  }

  useEffect(() => {
    fetchData(userUid);
  }, [userUid, rated]);

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
      <HistoryItems navigation={navigation} historyList={history} onRatedItem={updateRated} />
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