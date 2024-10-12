import React, { useMemo } from 'react';
import { StyleSheet, Text, View, FlatList } from 'react-native';
import Order from './Order';

const CompletedOrders = ({ orders }) => {
  const { completedOrders, orderCount } = useMemo(() => {
    const completed = orders.filter(order => order.status === 'completed');
    return {
      completedOrders: completed,
      orderCount: completed.length
    };
  }, [orders]);

  return (
    <View>
      <View style={styles.statusBar}>
        <Text style={styles.statusText}>Completed Orders</Text>
        <View style={styles.statusCount}>
          <Text style={styles.statusCountText}>{orderCount}</Text>
        </View>
      </View>
      
      <FlatList
        data={completedOrders}
        renderItem={({ item }) => <Order order={item} />}
        keyExtractor={item => item.id}
        contentContainerStyle={styles.orderList}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  statusBar: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 15,
    paddingVertical: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#E0E0E0',
  },
  statusText: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  statusCount: {
    backgroundColor: '#0C5E52',
    borderRadius: 15,
    paddingHorizontal: 10,
    paddingVertical: 5,
  },
  statusCountText: {
    color: 'white',
    fontSize: 14,
    fontWeight: 'bold',
  },
  orderList: {
    paddingHorizontal: 10,
    paddingTop: 10,
  },
});

export default CompletedOrders;