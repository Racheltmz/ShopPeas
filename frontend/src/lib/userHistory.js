import { create } from "zustand";
import transactionService from "../service/TransactionService";

export const useHistory = create((set, get) => ({
  history: [],
  loading: false,
  error: null,
  lastFetched: null,

  fetchHistory: async (userUid, force = false) => {
    const state = get();
    // Always fetch if forced, or if we don't have data
    if (force || !state.history.length) {
      set({ loading: true, error: null });
      try {
        const res = await transactionService.viewOrderHistory(userUid);
        const data = res.sort((a, b) => new Date(b.date) - new Date(a.date));
        set({ 
          history: data, 
          loading: false,
          lastFetched: new Date().toISOString()
        });
      } catch (err) {
        if (err.status === 404) {
          set({ history: [], loading: false });
        } else {
          set({ error: err.message, loading: false });
          console.error(err);
        }
      }
    }
  },

  // Force refresh after new order
  refreshAfterNewOrder: async (userUid) => {
    const { fetchHistory } = get();
    await fetchHistory(userUid, true); 
  },

  updateRating: (tid) => {
    set((state) => ({
      history: state.history.map(order => ({
        ...order,
        orders: order.orders.map(wholesaler => {
          if (wholesaler.tid === tid) {
            return { ...wholesaler, rated: true };
          }
          return wholesaler;
        })
      }))
    }));
  }
}));