import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { useNavigation } from "@react-navigation/native";
import { useUserStore } from '../../lib/userStore';
import Loader from '../../components/utils/Loader';
import HistoryItems from '../../components/customers/HistoryItems';


const History = ({ historyData, onUpdateRating }) => {
  const navigation = useNavigation();
  const [loading, setLoading] = useState(true);
  const [history, setHistory] = useState([]);

  // Update history when historyData prop changes
  useEffect(() => {
    if (historyData) {
      setHistory(historyData);
      setLoading(false);
    }
  }, [historyData]);

  // Handle navigation focus updates
  useEffect(() => {
    const unsubscribe = navigation.addListener('focus', () => {
      setLoading(true);
      if (historyData) {
        setHistory(historyData);
        setLoading(false);
      }
    });

    return unsubscribe;
  }, [navigation, historyData]);

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
        onRatedItem={onUpdateRating} 
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