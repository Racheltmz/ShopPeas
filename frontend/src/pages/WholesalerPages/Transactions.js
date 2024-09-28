import { SafeAreaView, StyleSheet, Text, View} from 'react-native'

const Transactions = () => {
  return (
    <SafeAreaView style = {styles.container}>
        <View>
            <Text>TRANSACTIONS PAGE HERE</Text>
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
})