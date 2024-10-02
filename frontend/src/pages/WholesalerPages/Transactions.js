import { SafeAreaView, StyleSheet, Text, View, TextInput, TouchableOpacity, Image} from 'react-native'
import { Ionicons } from '@expo/vector-icons';
import { useState } from 'react';

const Transactions = () => {
  const [visible, setVisible]=useState(false);
  const [selected, setSelected] = useState(defaultValue);

  const onItemPress = (item) => {
    setSelected(item);
    setVisible(false);
  };

  return (
    <SafeAreaView style = {styles.container}>
        <View style={styles.searchBar}>
            <Ionicons name="search" size={24} color="#0C5E52" />
            <TextInput 
            style={styles.searchInput}
            placeholder="Search Orders"
            placeholderTextColor="#0C5E52"
            />
            <TouchableOpacity>
            <Ionicons name="person-circle-outline" size={28} color="#0C5E52" />
            </TouchableOpacity>
        </View>
        <View style={styles.header}>
          <View style={styles.headerTextContainer}>
              <Text style={styles.headerTitle}>Happy Wholesaler</Text>
              <Text style={styles.subHeaderTitle}>Transactions</Text>
          </View>
          <Image
              source={require('../../../assets/imgs/pea.png')}
              style={styles.headerImage}
          />
        </View>
    </SafeAreaView>
  )
}

export default Transactions;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  pageTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    padding: 15,
    backgroundColor: 'white',
  },
  searchBar: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#E2ECEA',
    margin: 10,
    padding: 10,
    borderRadius: 25,
  },
  searchInput: {
    flex: 1,
    marginLeft: 10,
    color: '#0C5E52',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginLeft: 20,
    marginTop: 5,
  },
  headerTextContainer: {
    flex: 1,
  },
  headerTitle: {
    fontFamily: "Noto Sans",
    fontSize: 25,
    fontWeight: 'normal',
    color: '#0C5E52',
    marginLeft: 5,
  },
  subHeaderTitle: {
    fontFamily: "Noto Sans",
    fontSize: 35,
    fontWeight: 'bold',
    color: '#0C5E52',
    margin: 5,
  },
  headerImage: {
    width: '18%', 
    height: '100%',
    marginRight: "28.2%",
  },
})