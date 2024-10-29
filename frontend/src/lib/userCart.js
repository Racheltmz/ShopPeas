import { create } from "zustand";
import cartService from "../service/CartService";
import transactionService from "../service/TransactionService";

export const useCart = create((set, get) => ({
  cartId: null,
  cart: [],
  isLoading: false,
  error: null,

  // TODO: might remove this
  // Helper function to format cart data for backend
  formatCartForBackend: (cartData) => ({
    "cart_items": cartData.map((group) => ({
      wholesaler: group.wholesaler,
      uen: group.uen,
      items: group.items.map((item) => ({
        name: item.name,
        quantity: item.quantity,
        unit_price: item.price,
      })),
    })),
  }),

  fetchCart: async (userUid) => {
    set({ isLoading: true, error: null });

    await cartService.getCart(userUid)
      .then((res) => {
        const transformedCart = res.cart.map((group) => ({
          items: Array.isArray(group.items) ? group.items : [],
          location: group.location || {},
          wholesaler: group.wholesaler || "",
          uen: group.uen || "",
        }));

        set({
          cart: transformedCart,
          isLoading: false,
        });
      })
      .catch((err) => {
        if (err.status === 404) {
          set({
            cart: [],
            isLoading: false,
          });
        } else {
          set({
            cart: [],
            isLoading: false,
            error: err.message || "Failed to fetch cart",
          });
        }
      })
  },

  addItem: async (uid, data, productName, newQuantity) => {
    set({ isLoading: true, error: null });

    // Send update to backend
    await cartService.addToCart(uid, data)
      .then(() => {
        const currentState = get();
        const updatedCart = currentState.cart
          .map((wholesaler) => {
            if (wholesaler.uen === data.uen) {
              return {
                ...wholesaler,
                items: wholesaler.items
                  .map((item) => {
                    if (item.name === productName) {
                      // TODO: POTENTIALLY CAN TRY UPDATE QUANTITY by calling the fn
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
        // Update local state if backend update successful
        set({
          cart: updatedCart,
          isLoading: false,
        });
      })
      .catch((err) => {
        console.error("Error adding item:", err);
        set({
          isLoading: false,
          error: err.message || "Failed to add item",
        });
      })
  },

  updateItemQuantity: async (uid, data, productName, newQuantity) => {
    set({ isLoading: true, error: null });

    await cartService.updateCart(uid, data)
      .then(() => {
        const currentState = get();
        const updatedCart = currentState.cart
          .map((wholesaler) => {
            if (wholesaler.uen === data.uen) {
              return {
                ...wholesaler,
                items: wholesaler.items
                  .map((item) => {
                    if (item.name === productName) {
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

        // Update local state if backend update successful
        set({
          cart: updatedCart,
          isLoading: false,
        });
      })
      .catch((err) => {
        console.error("Error updating quantity:", err);
        set({
          isLoading: false,
          error: err.message || "Failed to update quantity",
        });
      })
  },

  removeItem: async (uid, deleteData, productName) => {
    set({ isLoading: true, error: null });

    await cartService.deleteCartItem(uid, deleteData)
      .then(() => {
        const currentState = get();
        const updatedCart = currentState.cart
          .map((wholesaler) => {
            if (wholesaler.uen === uen) {
              return {
                ...wholesaler,
                items: wholesaler.items.filter((item) => item.name !== productName),
              };
            }
            return wholesaler;
          })
          .filter((wholesaler) => wholesaler.items.length > 0);
        console.log(updatedCart)
        // Update local state if backend update successful
        set({
          cart: updatedCart,
          isLoading: false,
        });
      })
      .catch(() => {
        console.error("Error clearing cart:", error);
        set({
          isLoading: false,
          error: error.message || "Failed to clear cart",
        });
      })
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

    const checkoutData = currentState.formatCartForBackend(currentState.cart);
    console.log(uid, checkoutData);
    await transactionService.checkout(uid, checkoutData)
      .catch((err) => {
        console.log("Checkout failed: ", err)
        set({
          isLoading: false,
          error: err.message || "Failed to checkout",
        });
      })
  }
}));
