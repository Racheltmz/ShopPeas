import React, { useState } from 'react';
import { StyleSheet, View, TouchableOpacity } from 'react-native';
import { Dropdown } from 'react-native-element-dropdown';
import { Ionicons } from '@expo/vector-icons';

const OrderDropdown = ({ options, defaultValue, onSelect }) => {
  const [value, setValue] = useState(defaultValue);
  const [isFocus, setIsFocus] = useState(false);

  return (
    <View style={styles.container}>
      <Dropdown
        style={[styles.dropdown, isFocus]}
        placeholderStyle={styles.placeholderStyle}
        selectedTextStyle={styles.selectedTextStyle}
        iconStyle={styles.iconStyle}
        data={options}
        maxHeight={300}
        labelField="label"
        valueField="value"
        value={value}
        onFocus={() => setIsFocus(true)}
        onBlur={() => setIsFocus(false)}
        onChange={item => {
          setValue(item.value);
          setIsFocus(false);
          onSelect(item.value);
        }}
        renderLeftIcon={() => (
          <Ionicons
            style={styles.icon}
            color={'#0C5E52'}
            name="list-outline"
            size={20}
          />
        )}
      />
      <TouchableOpacity style={styles.filterButton}>
        <Ionicons name="funnel-outline" size={24} color="#0C5E52" />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingVertical: 8,
  },
  dropdown: {
    flex: 1,
    height: 50,
    borderColor: '#E2ECEA',
    borderWidth: 0.5,
    borderRadius: 8,
    paddingHorizontal: 8,
    backgroundColor: '#E2ECEA',
  },
  icon: {
    marginRight: 5,
  },
  placeholderStyle: {
    fontSize: 16,
    color: '#0C5E52',
  },
    selectedTextStyle: {
    fontSize: 16,
    color: '#0C5E52',
  },
  iconStyle: {
    width: 20,
    height: 20,
  },
  filterButton: {
    padding: 10,
    backgroundColor: '#E2ECEA',
    borderRadius: 8,
    marginLeft: 10,
  },
});

export default OrderDropdown;