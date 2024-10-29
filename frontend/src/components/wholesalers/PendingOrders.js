import React, { useState, useMemo, useEffect } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, FlatList } from 'react-native';
import Order from './Order';

const PendingOrders = ({ orders, onAccept, onComplete }) => {
  const [selectedStatus, setSelectedStatus] = useState('PENDING-ACCEPTANCE');

  const { groupedOrders, orderCounts } = useMemo(() => {
    if (!orders) {
      return {
        groupedOrders: [],
        orderCounts: {
          'PENDING-ACCEPTANCE': 0,
          'PENDING-COMPLETION': 0
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
      currency: order.currency,
      uid: order.uid
    }));

    // filter orders based on status
    const filtered = orderGroups.filter(group => 
      group.status === selectedStatus && group.items.length > 0
    );

    // counting for header
    const counts = {
      'PENDING-ACCEPTANCE': orderGroups.filter(group => group.status === 'PENDING-ACCEPTANCE').length,
      'PENDING-COMPLETION': orderGroups.filter(group => group.status === 'PENDING-COMPLETION').length,
    };

    return { groupedOrders: filtered, orderCounts: counts };
  }, [orders, selectedStatus]); 

  const handleStatusChange = (newStatus) => {
    setSelectedStatus(newStatus);
  };

  return (
    <View style={styles.container}>
      <View style={styles.statusTabs}>
        <TouchableOpacity 
          style={[
            styles.statusTab, 
            selectedStatus === 'PENDING-ACCEPTANCE' && styles.activeTab
          ]}
          onPress={() => handleStatusChange('PENDING-ACCEPTANCE')}
        >
          <Text style={styles.statusTabText}>To be accepted</Text>
          <View style={styles.statusCount}>
            <Text style={styles.statusCountText}>
              {orderCounts['PENDING-ACCEPTANCE']}
            </Text>
          </View>
        </TouchableOpacity>
        
        <TouchableOpacity 
          style={[
            styles.statusTab, 
            selectedStatus === 'PENDING-COMPLETION' && styles.activeTab
          ]}
          onPress={() => handleStatusChange('PENDING-COMPLETION')}
        >
          <Text style={styles.statusTabText}>To be completed</Text>
          <View style={styles.statusCount}>
            <Text style={styles.statusCountText}>
              {orderCounts['PENDING-COMPLETION']}
            </Text>
          </View>
        </TouchableOpacity>
      </View>
      
      <FlatList
        data={groupedOrders}
        renderItem={({ item }) => (
          <Order 
            orderGroup={item}
            onAccept={onAccept}
            onComplete={onComplete}
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
  },
  statusTabs: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    marginVertical: 10,
  },
  statusTab: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: 10,
  },
  activeTab: {
    borderBottomWidth: 2,
    borderBottomColor: '#0C5E52',
  },
  statusTabText: {
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
    fontSize: 12,
  },
  orderList: {
    paddingHorizontal: 10,
  },
  flatList: {
    flex: 1,
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

export default PendingOrders;