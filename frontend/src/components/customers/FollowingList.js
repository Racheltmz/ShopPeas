import React from 'react';
import { StyleSheet, View, Text, FlatList } from 'react-native';
import { Ionicons } from '@expo/vector-icons';

const FollowingList = () => {
  // DUMMY FOLLOWING LIST
  const followingList = [
    { id: '1', name: 'Happy Wholesaler', location: 'Bishan', distance: 39, rating: 4.9 },
  ];

  const renderItem = ({ item }) => (
    <View style={styles.wholesalerContainer}>
      <View>
        <Text style={styles.wholesalerName}>{item.name}</Text>
        <Text style={styles.wholesalerLocation}>
          <Ionicons name="location" size={16} color="red" />
          {item.location}, {item.distance} Minutes away
        </Text>
      </View>
      <Text style={styles.rating}>{item.rating} ⭐</Text>
    </View>
  );

  return (
    <FlatList
      data={followingList}
      renderItem={renderItem}
      keyExtractor={item => item.id}
      style={styles.list}
    />
  );
};

const styles = StyleSheet.create({
  list: {
    padding: 10,
  },
  wholesalerContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: '#e6f3e6',
    borderRadius: 10,
    padding: 15,
    marginBottom: 10,
  },
  wholesalerName: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  wholesalerLocation: {
    color: '#666',
  },
  rating: {
    fontWeight: 'bold',
  },
});

export default FollowingList;