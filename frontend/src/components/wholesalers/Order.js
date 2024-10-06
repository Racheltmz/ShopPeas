import React from 'react';
import { View, Text, Image, TouchableOpacity, StyleSheet } from 'react-native';

const Order = ({ order, onAccept, onComplete }) => {
  const isToBeAccepted = order.status === 'to_be_accepted';
  const isToBeCompleted = order.status === 'to_be_completed';

  const handleAccept = () => {
    if (isToBeAccepted) {
      onAccept(order.id);
    }
  };

  const handleComplete = () => {
    if (isToBeCompleted) {
      onComplete(order.id);
    }
  };

  return (
    <View style={styles.orderItem}>
      <View style={styles.orderHeader}>
        <Text style={styles.orderId}>OID: {order.id}</Text>
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
      </View>
      <View style={styles.orderContent}>
        <View style={styles.orderPic}>
        </View>
        <View style={styles.orderDetails}>
          <Text style={styles.productName}>{order.productName}</Text>
          <Text style={styles.quantity}>Qty: {order.quantity}</Text>
        </View>
      </View>
      <View style={styles.orderFooter}>
        <View style={styles.paidStatus}>
          <Text style={styles.paidText}>{order.paid}</Text>
          <Text style={styles.dateText}>{order.date}</Text>
        </View>
        <View style={styles.buyerInfo}>
          <Text style={styles.buyerName}>Buyer: {order.buyerName}</Text>
          <Text style={styles.pickupMethod}>{order.pickupMethod}</Text>
        </View>
        <View style={styles.totalAndDetails}>
          <Text style={styles.totalAmount}>Total: S${order.total.toFixed(2)}</Text>
          <TouchableOpacity>
            <Text style={styles.viewDetails}>View more details &gt;</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  orderItem: {
    backgroundColor: 'w',
    backgroundColor: '#EBF3D1',
    borderRadius: 10,
    padding: '5%',
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
    fontWeight: 'bold',
  },
  acceptButton: {
    backgroundColor: '#FFA07A',
    padding: 5,
    borderRadius: 5,
  },
  completeButton: {
    backgroundColor: '#FFA07A',
    padding: 5,
    borderRadius: 5,
  },
  buttonText: {
    color: 'white',
  },
  orderContent: {
    flexDirection: 'row',
    marginBottom: 10,
  },
  productImage: {
    width: 50,
    height: 50,
    marginRight: 10,
  },
  orderDetails: {
    justifyContent: 'center',
  },
  productName: {
    fontWeight: 'bold',
  },
  quantity: {
    color: '#666',
  },
  orderFooter: {
    borderTopWidth: 1,
    borderTopColor: '#DDD',
    paddingTop: 10,
  },
  paidStatus: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 5,
  },
  paidText: {
    backgroundColor: '#90EE90',
    padding: 2,
    borderRadius: 3,
  },
  dateText: {
    color: '#666',
  },
  buyerInfo: {
    marginBottom: 5,
  },
  buyerName: {
    fontWeight: 'bold',
  },
  pickupMethod: {
    color: '#666',
  },
  totalAndDetails: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  totalAmount: {
    fontWeight: 'bold',
  },
  viewDetails: {
    color: '#0C5E52',
    textDecorationLine: 'underline',
  },
});

export default Order;