import React, { useMemo } from 'react';
import { StyleSheet, Text, View, FlatList } from 'react-native';
import Order from './Order';

const CompletedOrders = ({ orders }) => {
  const { groupedOrders, orderCounts } = useMemo(() => {
    if (!orders) {
      return {
        groupedOrders: [],
        orderCounts: {
          'COMPLETION': 0,
        }
      };
    }

    const ordersList = Array.isArray(orders) ? orders : [orders];
    
    const orderGroups = ordersList.map(order => ({
      orderId: order.id || order.tid,
      status: order.status,
      items: Array.isArray(order.items) ? order.items : [],
      totalAmount: order.total_price || order.totalAmount,
      currency: order.currency,
      uid: order.uid
    }));

    const filtered = orderGroups.filter(group => 
      group.status === 'COMPLETED' && group.items.length > 0
    );

    const counts = {
      'COMPLETED': orderGroups.filter(group => group.status === 'COMPLETED').length,
    };

    return { groupedOrders: filtered, orderCounts: counts };
  }, [orders]); 

  return (
    <View style={styles.container}>
      <View style={styles.statusBar}>
        <Text style={styles.statusText}>Completed Orders</Text>
        <View style={styles.statusCount}>
          <Text style={styles.statusCountText}>{orderCounts.COMPLETED}</Text>
        </View>
      </View>
      
      <FlatList
        data={groupedOrders}
        renderItem={({ item }) => (
          <Order 
            orderGroup={item}
          />
        )}
        keyExtractor={item => item.orderId?.toString()}
        contentContainerStyle={styles.orderList}
        style={styles.flatList}
        ListEmptyComponent={() => (
          <View style={styles.emptyContainer}>
            <Text style={styles.emptyText}>No orders found</Text>
          </View>
        )}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1, 
    height: '100%',  
  },
  statusBar: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: 10,
    paddingHorizontal: '5%',
  },
  statusText: {
    fontSize: 16,
    color: '#0C5E52',
    marginRight: 5,
  },
  statusCount: {
    backgroundColor: '#0C5E52',
    borderRadius: 10,
    paddingHorizontal: 8,
    paddingVertical: 2,
  },
  statusCountText: {
    color: 'white',
    fontSize: 14,
    fontWeight: 'bold',
  },
  flatList: {
    flex: 1,  
  },
  orderList: {
    paddingHorizontal: 10,
    paddingTop: 10,
  },
  emptyContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    paddingTop: 20,
  },
  emptyText: {
    color: '#666',
    fontSize: 16,
  },
});

export default CompletedOrders;