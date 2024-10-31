import React, { useState } from 'react';
import { View, Text, ScrollView, StyleSheet, TouchableOpacity, TextInput} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { useUserStore } from '../../../lib/userStore';
import { Ionicons } from '@expo/vector-icons';
import { Divider } from 'react-native-paper';
import paymentService from '../../../service/PaymentService';
import CustomAlert from '../../utils/Alert';

const AddCard = () => {
  const navigation = useNavigation();
  const { userUid, updateCardNumbers } = useUserStore();
  const [alertVisible, setAlertVisible] = useState(false);
  const [customAlert, setCustomAlert] = useState({ title: '', message: '', onConfirm: () => { } });

  const showAlert = (title, message, onConfirm) => {
      setCustomAlert({ title, message, onConfirm });
      setAlertVisible(true);
  };

  const [formData, setFormData] = useState({
    card_no: '',
    cvv: '',
    expiry_date: '',
    name: ''
  });

  const validateCardNumber = (number) => {
    return number.replace(/\s/g, '').match(/^[0-9]{16}$/);
  };

  const validateExpiryDate = (date) => {
    return date.match(/^(0[1-9]|1[0-2])\/([0-9]{2})$/);
  };

  const validateCVV = (cvv) => {
    return cvv.match(/^[0-9]{3,4}$/);
  };

  const handleSubmit = async () => {
    if (!validateCardNumber(formData.card_no)) {
      showAlert("Invalid Card Number", "Please enter a valid 16-digit card number.", () => setAlertVisible(false));
      return;
    }
    if (!validateExpiryDate(formData.expiry_date)) {
      showAlert("Invalid Expiry Date", "Please enter a valid expiry date (MM/YY).", () => setAlertVisible(false));
      return;
    }
    if (!validateCVV(formData.cvv)) {
      showAlert("Invalid CVV", "Please enter a valid CVV (3 or 4 digits).", () => setAlertVisible(false));
      return;
    }
    if (formData.name.trim().length === 0) {
      showAlert("Invalid Name", "Please enter the name on the card.", () => setAlertVisible(false));
      return;
    }

    try{
        const paymentDetails = {
            card_no: formData.card_no.replace(/\s/g, ''),
            cvv: formData.cvv,
            expiry_date: formData.expiry_date,
            name: formData.name.trim()
        };

        await paymentService.addCard(userUid, paymentDetails)
        const newPaymentsList = await paymentService.getPayment(userUid);
        updateCardNumbers(newPaymentsList);

        showAlert("Success!", "Card successfully added!");
    } catch(err){
      alert("Addition of card failed: " + err.message);
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Ionicons name="arrow-back" size={24} color="#0C5E52" />
        </TouchableOpacity>
        <View style={styles.headerTitleContainer}>
          <Text style={styles.headerTitle}>Add Card</Text>
        </View>
      </View>
      <ScrollView>
        <Divider />
      <View style={styles.form}>
        <Text style={styles.label}>Card Number:</Text>
        <TextInput
          style={styles.input}
          value={formData.card_no}
          onChangeText={(text) => setFormData({...formData, ['card_no']:text.replace(/\s/g, '').replace(/(\d{4})/g, '$1 ').trim()})}
          placeholder="1234 5678 9012 3456"
          keyboardType="numeric"
          maxLength={19}
        />
        <View style={styles.row}>
          <View style={styles.halfWidth}>
            <Text style={styles.label}>Expiry Date (MM/YY):</Text>
            <TextInput
              style={styles.input}
              value={formData.expiry_date}
              onChangeText={(text) => {
                text = text.replace(/\D/g, '');
                if (text.length > 2) {
                  text = text.slice(0, 2) + '/' + text.slice(2);
                }
                setFormData({...formData, ['expiry_date']:text});
              }}
              placeholder="MM/YY"
              maxLength={5}
              keyboardType="numeric"
            />
          </View>
          <View style={styles.halfWidth}>
            <Text style={styles.label}>CVV:</Text>
            <TextInput
              style={styles.input}
              value={formData.cvv}
              onChangeText={(text) => {
                setFormData({...formData, ['cvv']: text})
              }}
              placeholder="123"
              keyboardType="numeric"
              maxLength={4}
            />
          </View>
        </View>
        <Text style={styles.label}>Name on Card:</Text>
        <TextInput
          style={styles.input}
          value={formData.name}
          onChangeText={(text) => {
            setFormData({...formData, ['name']: text})
          }}
          placeholder="John Doe"
        />
      </View>
      <TouchableOpacity style={styles.submitButton} onPress={handleSubmit}>
        <Text style={styles.submitButtonText}>Submit</Text>
        <Ionicons name="arrow-forward" size={24} color="white" />
      </TouchableOpacity>
      </ScrollView>
      <CustomAlert
          visible={alertVisible}
          title={customAlert.title}
          message={customAlert.message}
          onConfirm={() => {
          setAlertVisible(false);
          customAlert.onConfirm;
          navigation.goBack();
          }}
      />
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
    marginTop: 40,
    marginBottom: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginBottom: 20,
  },
  form: {
    backgroundColor: 'white',
    borderRadius: 10,
    padding: 20,
  },
  label: {
    fontSize: 14,
    color: '#0C5E52',
    marginBottom: 5,
  },
  input: {
    borderWidth: 1,
    borderColor: '#0C5E52',
    borderRadius: 5,
    padding: 10,
    fontSize: 16,
    marginBottom: 15,
  },
  row: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  halfWidth: {
    width: '48%',
  },
  submitButton: {
    flexDirection: 'row',
    justifyContent: 'center',
    backgroundColor: '#0C5E52',
    padding: 16,
    margin: 16,
    borderRadius: 10,
  },
  submitButtonText: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'white',
    marginRight: 8,
  },
});

export default AddCard;