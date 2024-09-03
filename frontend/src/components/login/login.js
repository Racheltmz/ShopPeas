import { useState} from 'react';

const login = () => {

    const [isLoading, setIsLoading] = useState(false);

    const handleLogin = async(e) => {
        e.preventDefault();

    }


    return (
        <View style={styles.container}>
            
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#fff',
      alignItems: 'center',
      justifyContent: 'center',
    },
  });
  