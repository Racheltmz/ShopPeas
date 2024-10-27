import { create } from "zustand";
import cartService from "../service/CartService";

export const useCart = create((set, get) => ({
  cartId: null,
  cart: [],
  isLoading: false,
  error: null,

  // Helper function to format cart data for backend
  formatCartForBackend: (cartData) => ({
    "cart items": cartData.map((group) => ({
      wholesaler: group.wholesaler,
      items: group.items.map((item) => ({
        name: item.name,
        quantity: item.quantity,
        unit_price: item.price,
      })),
    })),
  }),

  fetchCart: async (userUid) => {
    set({ isLoading: true, error: null });

    try {
      const response = await cartService.getCart(userUid);

      const transformedCart = response.cart.map((group) => ({
        items: Array.isArray(group.items) ? group.items : [],
        location: group.location || {},
        wholesaler: group.wholesaler || "",
      }));

      set({
        cart: transformedCart,
        isLoading: false,
      });
    } catch (error) {
      console.error("Error fetching cart:", error);
      set({
        cart: [],
        isLoading: false,
        error: error.message || "Failed to fetch cart",
      });
    }
  },

  updateItemQuantity: async (wholesalerName, itemName, newQuantity, uid) => {
    set({ isLoading: true, error: null });

    try {
      const currentState = get();
      const updatedCart = currentState.cart
        .map((wholesaler) => {
          if (wholesaler.wholesaler === wholesalerName) {
            return {
              ...wholesaler,
              items: wholesaler.items
                .map((item) => {
                  if (item.name === itemName) {
                    return { ...item, quantity: Math.max(0, newQuantity) };
                  }
                  return item;
                })
                .filter((item) => item.quantity > 0),
            };
          }
          return wholesaler;
        })
        .filter((wholesaler) => wholesaler.items.length > 0);
      
      const backendData = currentState.formatCartForBackend(updatedCart);

      await cartService.updateCart(backendData, uid);

      // Update local state if backend update successful
      set({
        cart: updatedCart,
        isLoading: false,
      });
    } catch (error) {
      console.error("Error updating quantity:", error);
      set({
        isLoading: false,
        error: error.message || "Failed to update quantity",
      });
    }
  },

  removeItem: async (wholesalerName, itemName, uid) => {
    set({ isLoading: true, error: null });

    try {
      const currentState = get();
      const updatedCart = currentState.cart
        .map((wholesaler) => {
          if (wholesaler.wholesaler === wholesalerName) {
            return {
              ...wholesaler,
              items: wholesaler.items.filter((item) => item.name !== itemName),
            };
          }
          return wholesaler;
        })
        .filter((wholesaler) => wholesaler.items.length > 0);

      // Format cart data for backend
      const backendData = currentState.formatCartForBackend(updatedCart, uid);

      // Send update to backend
      await cartService.updateCart(backendData);

      // Update local state if backend update successful
      set({
        cart: updatedCart,
        isLoading: false,
      });
    } catch (error) {
      console.error("Error removing item:", error);
      set({
        isLoading: false,
        error: error.message || "Failed to remove item",
      });
    }
  },

  addItem: async (data, uid) => {
    set({ isLoading: true, error: null });

    try { 
      // Send update to backend
      await cartService.addToCart(data, uid);

      // Update local state if backend update successful
      set({
        cart: updatedCart,
        isLoading: false,
      });
      // show that success
      return true;
    } catch (error) {
      console.error("Error adding item:", error);
      set({
        isLoading: false,
        error: error.message || "Failed to add item",
      });
    }
  },

  clearCart: async (uid) => {
    set({ isLoading: true, error: null });

    try {
      const currentState = get();
      // Send empty cart to backend
      const backendData = currentState.formatCartForBackend([]);
      await cartService.updateCart(backendData);

      set({
        cart: [],
        isLoading: false,
      });
    } catch (error) {
      console.error("Error clearing cart:", error);
      set({
        isLoading: false,
        error: error.message || "Failed to clear cart",
      });
    }
  },

  getTotal: () => {
    const currentState = get();
    return currentState.cart.reduce((total, wholesaler) => {
      return (
        total +
        wholesaler.items.reduce((subTotal, item) => {
          return subTotal + item.price * item.quantity;
        }, 0)
      );
    }, 0);
  },

  checkout: async (uid) => {
    const currentState = get();
    try {
      const backendData = currentState.formatCartForBackend(...currentState.cart);
      
    } catch(error) {
      console.log("Checkout failed: ", error)
      set({
        isLoading: false,
        error: error.message || "Failed to checkout",
      });
    }
  }
}));
