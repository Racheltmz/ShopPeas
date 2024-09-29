import { StatusBar } from "expo-status-bar";
import { useState, useEffect } from "react";
import { StyleSheet, Text, View, ImageBackground } from "react-native";
import AuthPage from "./src/pages/AuthPage";
import { useUserStore } from "./src/lib/userStore";
import { onAuthStateChanged } from "firebase/auth";
import { FirebaseAuth } from "./src/lib/firebase";
import CustomerPages from "./src/pages/UserPages/CustomerPages";
import WholesalerPages from "./src/pages/UserPages/WholesalerPages";
import { DefaultTheme, NavigationContainer } from "@react-navigation/native";

export default function App() {
  const { currentUser, isLoading, fetchUserInfo, userRole, resetUser } = useUserStore();
  const backgroundImage = require("./assets/imgs/backGroundImage.png");

  const navTheme = {
    ...DefaultTheme,
    colors: {
      ...DefaultTheme.colors,
      background: 'transparent',
    },
  }

  useEffect(() => {
    const unSub = onAuthStateChanged(FirebaseAuth, (user) => {
      if (user) {
        fetchUserInfo(user.uid);
      } else {
        // Handle the case when user is null (logged out)
        resetUser();
      }
    });
    return () => {
      unSub();
    };
  }, [fetchUserInfo, resetUser]);

  if (isLoading) return <Text style={styles.loading}>Loading...</Text>;

  // determine which page to load
  let currentPage;

  if (currentUser && userRole) {
    switch (userRole) {
      case "consumer":
        currentPage = <CustomerPages />;
        break;
      case "wholesaler":
        currentPage = <WholesalerPages />;
        break;
      default:
        currentPage = <Text>Unknown user type</Text>;
    }
    return (
      <ImageBackground
        source={backgroundImage}
        style={styles.backgroundImage}
      >
        <NavigationContainer theme={navTheme}>
          <View style={styles.contentContainer}>{currentPage}</View>
        </NavigationContainer>
      </ImageBackground>
    );
  } else {
    currentPage = <AuthPage />;

    return <View style={styles.container}>{currentPage}</View>;
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  backgroundImage: {
    flex: 1,
    width: "100%",
    height: "100%",
    resizeMode: "cover",
  },
  contentContainer: {
    flex: 1,
    backgroundColor: "transparent",
  },
  loading: {
    fontSize: 18,
    textAlign: "center",
    marginTop: 50,
  },
});
