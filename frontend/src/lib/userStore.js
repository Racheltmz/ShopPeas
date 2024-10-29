import { create } from "zustand";
import { doc, getDoc } from "firebase/firestore";
import { FirebaseAuth, FirebaseDb } from "./firebase";

const db = FirebaseDb;

export const useUserStore = create((set, get) => ({
  currentUser: null,
  userRole: "",
  userAddress: null,
  paymentDetails: null,
  isLoading: true,
  userUid: "",
  rerender: false,
  fetchUserInfo: async (uid) => {
    if (!uid) return set({ currentUser: null, userRole: "", isLoading: false });

    set({ isLoading: true });
    try {
      const userRole = get().userRole;
      if (userRole) {
        const docRef = doc(db, userRole, uid);
        const docSnap = await getDoc(docRef);
        
        // if user exists
        if (docSnap.exists()) {
          const user = FirebaseAuth.currentUser;
          const signupDate = new Date(user.metadata.creationTime);
          // Format the date as "DD-MM-YYYY"
          const formattedSignupDate = signupDate
            .toLocaleDateString("en-GB", {
              day: "2-digit",
              month: "2-digit",
              year: "numeric",
            })
            .split("/")
            .join("-");

          // Merge the formatted signup date with the existing user data
          const userData = {
            ...docSnap.data(),
            signupDate: formattedSignupDate,
          };

          set({ currentUser: userData, isLoading: false, userUid: uid });
        } else {
          set({ currentUser: null, isLoading: false });
        }

        let params = ""

        if (userRole == "consumer") {
          params = uid;
        }
        else {
          params = get().currentUser["uen"];
        }
        const addressDocRef = doc(db, userRole + "_address", params);
        const addressDocSnap = await getDoc(addressDocRef);

        if (docSnap.exists()) {
          set({ userAddress: addressDocSnap.data(), isLoading: false });
        } else {
          set({ userAddress: null, isLoading: false });
        }

        // get bank account details
        const paymentDetailsDocRef = doc(db, userRole + "_account", params);
        const paymentDetailsDocSnap = await getDoc(paymentDetailsDocRef);

        if (docSnap.exists()) {
          set({
            paymentDetails: paymentDetailsDocSnap.data(),
            isLoading: false,
          });
        } else {
          set({ paymentDetails: null, isLoading: false });
        }
      } else {
        set({
          currentUser: null,
          paymentDetails: null,
          userAddress: null,
          isLoading: false,
        });
      }
    } catch (err) {
      console.log(err);
      return set({ currentUser: null, isLoading: false });
    }
  },
  updateUserType: (userType) => {
    set({ userRole: userType });
  },
  resetUser: () => {
    set({ currentUser: null, userRole: "", isLoading: false });
  },
}));
