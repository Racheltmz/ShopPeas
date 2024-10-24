import React, { useState } from 'react';
import { Modal, StyleSheet, Text, View, TouchableOpacity } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { useUserStore } from '../../lib/userStore';
import wholesalerService from "../../service/WholesalerService";

const RatingModal = ({ visible, onClose, wholesaler, tid, onRated }) => {
  const { userUid } = useUserStore();
  const [rating, setRating] = useState(0);

  const handleRating = (selectedRating) => {
    setRating(selectedRating);
  };

  const handleSubmit = () => {
    wholesalerService.rateWholesaler(userUid, wholesaler, tid, rating);
    setTimeout(() => {
      onClose();
      setRating(0); // Reset rating after closing
    }, 500);
    onRated(true);
  };

  return (
    <Modal
      animationType="slide"
      transparent={true}
      visible={visible}
      onRequestClose={onClose}
    >
      <View style={styles.centeredView}>
        <View style={styles.modalView}>
          <TouchableOpacity style={styles.closeButton} onPress={onClose}>
            <Text style={styles.closeButtonText}>✕</Text>
          </TouchableOpacity>
          <Text style={styles.modalTitle}>Leave a Rating for :</Text>
          <Text style={styles.wholesalerName}>{wholesaler?.wholesalerName}</Text>
          <Text style={styles.modalSubtitle}>Share your experience with the wholesaler to help other buyers.</Text>
          <View style={styles.ratingContainer}>
            {[1, 2, 3, 4, 5].map((star) => (
              <TouchableOpacity
                key={star}
                onPress={() => handleRating(star)}
              >
                <Ionicons 
                  name={star <= (rating) ? "star" : "star-outline"} 
                  size={40} 
                  color={star <= (rating) ? "#FFD700" : "#808080"} 
                  style={styles.starIcon}
                />
              </TouchableOpacity>
            ))}
          </View>
          <TouchableOpacity
            style={styles.submitButton}
            onPress={handleSubmit}
          >
            <Text style={styles.submitButtonText}>Submit</Text>
          </TouchableOpacity>
        </View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  centeredView: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  modalView: {
    margin: 20,
    backgroundColor: 'white',
    borderRadius: 20,
    padding: 35,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
    width: '80%',
  },
  closeButton: {
    position: 'absolute',
    right: 10,
    top: 10,
  },
  closeButtonText: {
    fontSize: 24,
    color: '#0C5E52',
  },
  modalTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginBottom: 5,
  },
  wholesalerName: {
    textAlign: 'center',
    fontSize: 22,
    fontWeight: 'bold',
    color: '#0C5E52',
    marginBottom: 10,
  },
  modalSubtitle: {
    fontSize: 14,
    color: '#777',
    textAlign: 'center',
    marginBottom: 20,
  },
  ratingContainer: {
    flexDirection: 'row',
    justifyContent: 'center',
    marginBottom: 20,
  },
  starIcon: {
    marginHorizontal: 5,
  },
  submitButton: {
    backgroundColor: '#B5D75F',
    paddingHorizontal: 30,
    paddingVertical: 10,
    borderRadius: 20,
  },
  submitButtonText: {
    color: '#0C5E52',
    fontWeight: 'bold',
    fontSize: 16,
  },
});

export default RatingModal;