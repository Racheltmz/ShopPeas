import React, { useState, useMemo } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, FlatList } from 'react-native';
import Order from './Order';

const PendingOrders = ({ orders, onAccept, onComplete }) => {
  const [selectedStatus, setSelectedStatus] = useState('to_be_accepted');

  const { filteredOrders, orderCounts } = useMemo(() => {
    const pendingOrders = orders.filter(order => 
      order.status === 'to_be_accepted' || order.status === 'to_be_completed'
    );
    const filtered = pendingOrders.filter(order => order.status === selectedStatus);
    const counts = {
      to_be_accepted: pendingOrders.filter(order => order.status === 'to_be_accepted').length,
      to_be_completed: pendingOrders.filter(order => order.status === 'to_be_completed').length,
    };

    return { filteredOrders: filtered, orderCounts: counts };
  }, [selectedStatus, orders]);

  return (
    <View>
      <View style={styles.statusTabs}>
        <TouchableOpacity 
          style={[styles.statusTab, selectedStatus === 'to_be_accepted' && styles.activeTab]}
          onPress={() => setSelectedStatus('to_be_accepted')}
        >
          <Text style={styles.statusTabText}>To be accepted</Text>
          <View style={styles.statusCount}>
            <Text style={styles.statusCountText}>{orderCounts.to_be_accepted}</Text>
          </View>
        </TouchableOpacity>
        <TouchableOpacity 
          style={[styles.statusTab, selectedStatus === 'to_be_completed' && styles.activeTab]}
          onPress={() => setSelectedStatus('to_be_completed')}
        >
          <Text style={styles.statusTabText}>To be completed</Text>
          <View style={styles.statusCount}>
            <Text style={styles.statusCountText}>{orderCounts.to_be_completed}</Text>
          </View>
        </TouchableOpacity>
      </View>
      
      <FlatList
        data={filteredOrders}
        renderItem={({ item }) => <Order order={item} onAccept={onAccept} onComplete={onComplete}/>}
        keyExtractor={item => item.id}
        contentContainerStyle={styles.orderList}
      />
    </View>
  );
};

const styles = StyleSheet.create({
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
});

export default PendingOrders;