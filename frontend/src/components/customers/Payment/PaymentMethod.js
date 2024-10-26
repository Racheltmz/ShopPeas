import { React, useState, useEffect }  from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView, FlatList} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';
import { useUserStore } from '../../../lib/userStore';
import { Divider } from 'react-native-paper';
import paymentService from '../../../service/PaymentService';

const PaymentMethod = () => {
  const navigation = useNavigation();
  const { userUid, paymentDetails } = useUserStore(); 
  const [selectedPayment, setSelectedPayment] = useState(null);
  const [payments, setPayments] = useState([]); 
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleAddCard = () => {
    navigation.navigate('AddCard');
  };

  const loadPaymentMethods = async (userUid) => {
    try {
      setLoading(true);
      setError(null);
      const paymentsList = await paymentService.getPayment(userUid);
      setPayments(paymentsList);
    } catch (err) {
      setError('Failed to load existing payment methods. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  const Item = ({item}) => (
    <TouchableOpacity 
      style={[
        styles.item,
        selectedPayment === item && styles.selectedItem
      ]}
      onPress={() => setSelectedPayment(item)}
    >
      <Ionicons 
        name="card-outline" 
        size={24} 
        color="#0C5E52" 
        style={styles.cardIcon} 
      />
      <View style={styles.cardDetails}>
        <Text style={styles.cardType}>Card ending in {item}</Text>
      </View>
    </TouchableOpacity>
  );

  useEffect(() => {
    if (userUid) {
      loadPaymentMethods(userUid);
    }
  }, [userUid]);

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Ionicons name="arrow-back" size={24} color="#0C5E52" />
        </TouchableOpacity>
        <View style={styles.headerTitleContainer}>
          <Text style={styles.headerTitle}>Payment Method</Text>
        </View>
      </View>
      <View>
        <Divider />
        <Text style={styles.sectionTitle}>Existing payment methods</Text>
        {loading ? (
          <Text style={styles.loadingText}>Loading payment methods...</Text>
        ) : (
          <FlatList
            data={payments}
            renderItem={({ item }) => <Item item={item} />}
            keyExtractor={item => item}
            ListEmptyComponent={() => (
              <Text style={[styles.cardNumber, { padding: 16, textAlign: 'center' }]}>
                No payment methods found
              </Text>
            )}
          />
            )}
          />
        )}
        <Text style={styles.sectionTitle}>Add a new credit/debit card</Text>
        <TouchableOpacity style={styles.addCardButton} onPress={handleAddCard}>
          <Ionicons name="add-circle-outline" size={24} color="#0C5E52" />
          <Text style={styles.addCardText}>Add Card</Text>
        </TouchableOpacity>
      </View>
      </View>
      <TouchableOpacity style={styles.addButton} onPress={handleAddCard}>
        <Text style={styles.addButtonText}>Add</Text>
        <Ionicons name="arrow-forward" size={24} color="white" />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    paddingTop: "5%",
  },
  header: {
    flexDirection: 'row',
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
    marginLeft: 10,
  },
  backButton: {
    margin: 16,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginLeft: 16,
    marginTop: 16,
    marginBottom: 8,
  },
  cardItem: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'white',
    padding: 16,
    marginHorizontal: 16,
    borderRadius: 8,
  },
  cardIcon: {
    marginRight: 16,
  },
  cardDetails: {
    flex: 1,
  },
  cardType: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  cardNumber: {
    fontSize: 14,
    color: '#666',
  },
  addCardButton: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'white',
    padding: 16,
    marginHorizontal: 16,
    borderRadius: 8,
  },
  addCardText: {
    marginLeft: 16,
    fontSize: 16,
    color: '#0C5E52',
  },
  addButton: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#0C5E52',
    padding: 16,
    margin: 16,
    borderRadius: 10,
  },
  addButtonText: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'white',
    marginRight: 8,
  },
});

export default PaymentMethod;