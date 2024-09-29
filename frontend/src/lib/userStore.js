import { create } from "zustand";
import { doc, getDoc } from "firebase/firestore";
import { FirebaseDb } from "./firebase";

const db = FirebaseDb;

export const useUserStore = create((set, get) => ({
  currentUser: null,
  userRole: "",
  isLoading: true,
  fetchUserInfo: async (uid) => {
    if (!uid) return set({ currentUser: null, userRole: "", isLoading: false });

    set({ isLoading: true });
    try {
      const userRole = get().userRole;
      if (userRole) {
        const docRef = doc(db, userRole, uid);
        const docSnap = await getDoc(docRef);
  
        if (docSnap.exists()) {
          set({ currentUser: docSnap.data(), isLoading: false });
        } else {
          set({ currentUser: null, isLoading: false });
        }
      }
      else {
        set({ currentUser: null, isLoading: false });
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
