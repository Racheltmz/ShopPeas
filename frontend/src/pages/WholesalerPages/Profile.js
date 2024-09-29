import { SafeAreaView, StyleSheet, Text, View } from 'react-native'

const Profile = () => {
  return (
    <SafeAreaView style = {styles.container}>
        <View>
            <Text>PROFILE PAGE HERE</Text>
        </View>
    </SafeAreaView>
  )
}

export default Profile;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'white',
      },
})