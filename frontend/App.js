
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
  const { currentUser, isLoading, fetchUserInfo } = useUserStore();
  const backgroundImage = require("./assets/imgs/backGroundImage.png");

  const navTheme = {
    ...DefaultTheme,
    colors: {
      ...DefaultTheme.colors,
      background: 'transparent',
    },
  }

  useEffect(() => {
    console.log("Setting up auth state listener");
    const unSub = onAuthStateChanged(FirebaseAuth, (user) => {
      console.log("Auth state changed. User:", user ? user.uid : "null");
      if (user) {
        fetchUserInfo(user.uid);
      } else {
        // Handle the case when user is null (logged out)
        fetchUserInfo("");
      }
    });
    return () => {
      unSub();
    };
  }, [fetchUserInfo]);

  if (isLoading) return <Text style={styles.loading}>Loading...</Text>;

  // determine which page to load
  let currentPage;

  console.log("Current user:", currentUser);
  if (currentUser) {
    console.log("User type:", currentUser.type);
    switch (currentUser.type) {
      case "customer":
        currentPage = <CustomerPages />;
        break;
      case "wholesaler":
        currentPage = <WholesalerPages />;
        break;
      default:
        currentPage = <AuthPage />;
    }
  } else{
    currentPage = <AuthPage />;
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
    /* resizeMode: "cover",*/
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
