import { initializeApp } from "firebase/app";
import { initializeAuth, getAuth, getReactNativePersistence } from "firebase/auth";
import { getFirestore } from "firebase/firestore";
import ReactNativeAsyncStorage from '@react-native-async-storage/async-storage';
import { REACT_APP_API_KEY } from '@env';

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: REACT_APP_API_KEY,
  authDomain: "shoppeasauthentication.firebaseapp.com",
  projectId: "shoppeasauthentication",
  storageBucket: "shoppeasauthentication.appspot.com",
  messagingSenderId: "275125552426",
  appId: "1:275125552426:web:f80aacd62c98a76e648a53",
};

// Initialize Firebase
const FirebaseApp = initializeApp(firebaseConfig);
initializeAuth(FirebaseApp, {
  persistence: getReactNativePersistence(ReactNativeAsyncStorage)
});
const FirebaseAuth = getAuth(FirebaseApp);
const FirebaseDb = getFirestore(FirebaseApp);

export { FirebaseApp, FirebaseAuth, FirebaseDb };