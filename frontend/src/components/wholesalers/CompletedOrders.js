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

    // convert orders into an array
    const ordersList = Array.isArray(orders) ? orders : [orders];
    
    const orderGroups = ordersList.map(order => ({
      orderId: order.id || order.tid,
      status: order.status,
      items: Array.isArray(order.items) ? order.items : [],
      totalAmount: order.total_price || order.totalAmount,
      uid: order.uid
    }));

    // filter orders based on status
    const filtered = orderGroups.filter(group => 
      group.status === 'COMPLETED' && group.items.length > 0
    );

    // counting for header
    const counts = {
      'COMPLETED': orderGroups.filter(group => group.status === 'COMPLETED').length,
    };

    return { groupedOrders: filtered, orderCounts: counts };
  }, [orders]); 


  return (
    <View>
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
  statusBar: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: 10,
    alignSelf: 'left',
    marginLeft: '5%'
  },
  statusText: {
    fontSize: 16,
    color: '#0C5E52',
    marginRight: 5,
    alignItems: 'left',
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