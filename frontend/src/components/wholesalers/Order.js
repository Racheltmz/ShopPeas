import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';

const Order = ({ orderGroup, onAccept, onComplete }) => {
  if (!orderGroup || !orderGroup.items) return null;

  const isToBeAccepted = orderGroup.status === 'PENDING-ACCEPTANCE';
  const isToBeCompleted = orderGroup.status === 'PENDING-COMPLETION';
  const isCompleted = orderGroup.status === 'COMPLETED';

  const handleAccept = () => {
    if (isToBeAccepted && onAccept) {
      onAccept(orderGroup.orderId);
    }
  };

  const handleComplete = () => {
    if (isToBeCompleted && onComplete) {
      onComplete(orderGroup.orderId);
    }
  };

  return (
    <View style={styles.orderItem}>
      <View style={styles.orderHeader}>
        <Text style={styles.orderId}>Order ID: {orderGroup.orderId.slice(0, 8)}</Text>
        {isToBeAccepted && (
          <TouchableOpacity style={styles.acceptButton} onPress={handleAccept}>
            <Text style={styles.buttonText}>Accept</Text>
          </TouchableOpacity>
        )}
        {isToBeCompleted && (
          <TouchableOpacity style={styles.completeButton} onPress={handleComplete}>
            <Text style={styles.buttonText}>Complete</Text>
          </TouchableOpacity>
        )}
        {isCompleted && (
          <View style={styles.completedButton}>
            <Text style={styles.buttonText}>COMPLETED</Text>
          </View>
        )}
      </View>
      
      {orderGroup.items.map((item, index) => (
        <View key={index} style={styles.orderContent}>
          <View style={styles.orderDetails}>
            <Text style={styles.productName}>{item.name || 'Product Name N/A'}</Text>
            <Text style={styles.quantity}>Quantity: {item.quantity || 0}</Text>
          </View>
        </View>
      ))}

      <View style={styles.orderFooter}>
        <View style={styles.totalAndDetails}>
          <Text style={styles.totalAmount}>
            Total Price: {orderGroup.currency === "SGD" ? "S$" : "MYR"} {orderGroup.totalAmount}
          </Text>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  orderItem: {
    backgroundColor: '#EBF3D1',
    borderRadius: 10,
    padding: 16,
    marginBottom: 10,
    width: '95%',
    alignSelf: 'center',
  },
  orderHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 10,
  },
  orderId: {
    color: '#0C5E52',
    fontWeight: 'bold',
    fontSize: 16,
  },
  acceptButton: {
    backgroundColor: '#FF7B5F',
    paddingVertical: 4,
    paddingHorizontal: 8,
    borderRadius: 5,
    alignItems: 'center',
  },
  completeButton: {
    backgroundColor: '#FFA07A',
    paddingVertical: 4,
    paddingHorizontal: 8,
    borderRadius: 5,
    alignItems: 'center',
  },
  completedButton: {
    backgroundColor: '#15b33f',
    paddingVertical: 4,
    paddingHorizontal: 8,
    borderRadius: 5,
    alignItems: 'center',
  },
  buttonText: {
    color: 'white',
    fontWeight: '500',
  },
  orderContent: {
    marginBottom: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#DDD',
    paddingBottom: 10,
  },
  orderDetails: {
    justifyContent: 'center',
  },
  productName: {
    color: '#0C5E52',
    fontWeight: 'bold',
    fontSize: 16,
    marginBottom: 4,
  },
  quantity: {
    color: '#666',
    fontSize: 14,
    marginBottom: 4,
  },
  itemPrice: {
    color: '#666',
    fontSize: 14,
  },
  orderFooter: {
    paddingTop: 10,
    marginTop: 10,
  },
  totalAndDetails: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  totalAmount: {
    color: '#0C5E52',
    fontWeight: 'bold',
    fontSize: 16,
  },
  viewDetails: {
    color: '#0C5E52',
    textDecorationLine: 'underline',
  },
});

export default Order;