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
      group.status === selectedStatus && group.items.length > 0
    );

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
    <View style={styles.mainContainer}>
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
      
      <View style={styles.listContainer}>
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
          showsVerticalScrollIndicator={true}
          ListEmptyComponent={() => (
            <View style={styles.emptyContainer}>
              <Text style={styles.emptyText}>No orders found</Text>
            </View>
          )}
        />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    height: '100%',
  },
  listContainer: {
    flex: 1,
    height: '100%',
  },
  statusTabs: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    marginVertical: 10,
    paddingHorizontal: 10,
  },
  statusTab: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: 10,
    paddingHorizontal: 15,
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
  flatList: {
    flex: 1,
  },
  orderList: {
    paddingHorizontal: 10,
    paddingBottom: 20,
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