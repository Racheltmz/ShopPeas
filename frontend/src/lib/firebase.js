import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
import { getFirestore } from "firebase/firestore";

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyCkKEdXB1aSesX4j0deg_Ye0Qvb-B8D-Jk",
    authDomain: "shoppeasauthentication.firebaseapp.com",
    projectId: "shoppeasauthentication",
    storageBucket: "shoppeasauthentication.appspot.com",
    messagingSenderId: "275125552426",
    appId: "1:275125552426:web:f80aacd62c98a76e648a53",
};

// Initialize Firebase
export const FirebaseApp = initializeApp(firebaseConfig);
export const FirebaseAuth = getAuth(FirebaseApp);
export const FirebaseDb = getFirestore(FirebaseApp);