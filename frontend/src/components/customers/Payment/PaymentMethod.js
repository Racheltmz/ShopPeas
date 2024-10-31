import { React, useState, useEffect }  from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView, FlatList} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';
import { useUserStore } from '../../../lib/userStore';
import { Divider } from 'react-native-paper';
import paymentService from '../../../service/PaymentService';

const PaymentMethod = () => {
  const navigation = useNavigation();
  const { userUid, updateCardNumbers, cardNumbers } = useUserStore(); 
  const [selectedPayment, setSelectedPayment] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleDeleteCard = async (card_number) => {
    await paymentService.deletePayment(userUid, card_number);
    await loadPaymentMethods(userUid);
  };
  
  const loadPaymentMethods = async (userUid) => {
    try {
      setLoading(true);
      setError(null);
      const paymentsList = await paymentService.getPayment(userUid);
      updateCardNumbers(paymentsList.card_numbers);
      if (paymentsList.card_numbers && paymentsList.card_numbers.length > 0) {
        setSelectedPayment(paymentsList.card_numbers[0]);
      }
    } catch (err) {
      setError('Failed to load existing payment methods. Please try again later.');
    } finally {
      setLoading(false);
    }
  };
  
  useEffect(() => {
    const unsubscribe = navigation.addListener('focus', () => {
      if (userUid) {
        loadPaymentMethods(userUid);
      }
    });
    if (userUid) {
      loadPaymentMethods(userUid);
    }
    return unsubscribe;
  }, [navigation, userUid]);

  const handleAddCard = () => {
    navigation.navigate('AddCard');
  };

  const Item = ({item}) => (
    <TouchableOpacity 
      style={[
        styles.item,
        selectedPayment === item && styles.selectedItem
      ]}
      onPress={() => setSelectedPayment(item)}
    >
    <View style={styles.leftContainer}>
      <Ionicons name={selectedPayment === item ? "radio-button-on" : "radio-button-off"} size={24} color="#0C5E52" style={styles.radioIcon} />
      <Ionicons name="card-outline" size={24} color="#0C5E52" style={styles.cardIcon}/>
      <View style={styles.cardDetails}>
        <Text style={styles.cardType}> •••• •••• •••• {item.slice(-4)}</Text>
      </View>
    </View>
    <TouchableOpacity 
        onPress={() => handleDeleteCard(item)}
        style={styles.deleteButton}
      >
        <Ionicons 
          name="trash-outline" 
          size={24} 
          color="#0C5E52" 
        />
      </TouchableOpacity>
    </TouchableOpacity>
  );

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
            data={cardNumbers}
            renderItem={({ item }) => <Item item={item} />}
            keyExtractor={item => item}
            ListEmptyComponent={() => (
              <Text style={styles.cardNumber}>
                No payment methods found
              </Text>
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
  loadingText:{
    fontSize: 14,
    color: '#666',
    padding: 16, 
    textAlign: 'center' 
  },
  item:{
    flexDirection: 'row',
    padding: '5%',
    alignItems: 'center',
    justifyContent: 'space-between',
    backgroundColor: 'white',
  },
  leftContainer:{
    flexDirection: 'row',
    alignItems: 'center',
    flex: 1,
  },
  cardIcon: {
    marginLeft: '3%',
  },
  cardDetails: {
    flex: 1,
  },
  cardType: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#0C5E52',
  },
  addCardButton: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'white',
    padding: '5%',
    borderRadius: 8,
  },
  addCardText: {
    marginLeft: 16,
    fontSize: 16,
    color: '#0C5E52',
  },
});

export default PaymentMethod;