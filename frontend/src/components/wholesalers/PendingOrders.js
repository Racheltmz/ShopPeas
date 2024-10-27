import React, { useState, useMemo } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, FlatList } from 'react-native';
import Order from './Order';

const PendingOrders = ({ orders, onAccept, onComplete }) => {
  const [selectedStatus, setSelectedStatus] = useState('PENDING-ACCEPTANCE');

  const { filteredOrders, orderCounts } = useMemo(() => {
    const pendingOrders = orders.filter(order => 
      order.status === 'PENDING-ACCEPTANCE' || order.status === 'PENDING-COMPLETION'
    );
    
    const filtered = pendingOrders.filter(order => order.status === selectedStatus);
    
    const counts = {
      'PENDING-ACCEPTANCE': pendingOrders.filter(order => order.status === 'PENDING-ACCEPTANCE').length,
      'PENDING-COMPLETION': pendingOrders.filter(order => order.status === 'PENDING-COMPLETION').length,
    };

    return { filteredOrders: filtered, orderCounts: counts };
  }, [selectedStatus, orders]);

  return (
    <View style={styles.container}>
      <View style={styles.statusTabs}>
        <TouchableOpacity 
          style={[
            styles.statusTab, 
            selectedStatus === 'PENDING-ACCEPTANCE' && styles.activeTab
          ]}
          onPress={() => setSelectedStatus('PENDING-ACCEPTANCE')}
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
          onPress={() => setSelectedStatus('PENDING-COMPLETION')}
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
        data={filteredOrders}
        renderItem={({ item }) => (
          <Order 
            order={item} 
            onAccept={onAccept} 
            onComplete={onComplete}
          />
        )}
        keyExtractor={item => item.id}
        contentContainerStyle={styles.orderList}
        style={styles.flatList}
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
});

export default PendingOrders;