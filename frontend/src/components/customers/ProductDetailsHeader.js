import React from 'react';
import { StyleSheet, TouchableOpacity, View, Text } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { useNavigation } from '@react-navigation/native';

const ProductDetailsHeader = ({ name, desc }) => {
  const navigation = useNavigation();

  return (
    <View style={styles.header}>
      <TouchableOpacity onPress={() => navigation.goBack()}>
        <Ionicons name="arrow-back" size={24} color="#0C5E52" />
      </TouchableOpacity>
      <View styles={styles.row}>
        <Text style={styles.headerTitle}>{name}</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
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
    fontSize: 16,
    fontWeight: 'bold',
    color: '#0C5E52',
    textAlign: 'left',
    paddingHorizontal: 10,
  },
  row: {
    flexDirection: 'row',
  },
});

export default ProductDetailsHeader;