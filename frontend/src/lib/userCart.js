import { create } from "zustand";
// const db = FirebaseDb;

const DUMMY_CART_ITEMS = [  
    {
        wholesaler: "Happy Wholesaler",
        // get location and distance using api and apply in the item itself in actual implementation
        location: "Bishan",
        distance: 39,
        items: [
            {
                name: "Bok Choy",
                quantity: 1,
                price: 1.29,
            },
            {
                name: "Rolled Oats",
                quantity: 1,
                price: 1.29,
            },
        ]
    },
    {
        wholesaler: "Cheap Wholesaler",
        location: "Serangoon",
        distance: 45,
        items: [
            {
                name: "Spinach",
                quantity: 3,
                price: 1.31,
            },
        ]
    },
    {
        wholesaler: "Quality Buy",
        location: "Woodlands",
        distance: 39,
        items: [
            {
                name: "Spinach",
                quantity: 10,
                price: 1.21,
            },

        ]
    },
]

// calls backend to retrieve cart information of user
export const useCart = create((set) => ({
    cartId: null,
    cart: [], // temporary Dummy
    
    fetchCart: (cartItem) => set((state) => {
        return { cart: cartItem }; 
    }),

    // wholesaler: { wholesaler, location, distance/timeaway}
    // item: { name, price }
    addItem: (wholesaler, item, quantity) => set((state) => {
      const updatedCart = [...state.cart];
      const wholesalerIndex = updatedCart.findIndex(w => w.wholesaler === wholesaler.wholesaler);
  
      if (wholesalerIndex !== -1) {
        const itemIndex = updatedCart[wholesalerIndex].items.findIndex(i => i.name === item.name);
        if (itemIndex !== -1) {
          // Item exists, increase quantity
          updatedCart[wholesalerIndex].items[itemIndex].quantity += quantity;
        } else {
          // Item doesn't exist, add new item
          updatedCart[wholesalerIndex].items.push({ ...item, quantity: quantity });
        }
      } else {
        // Wholesaler doesn't exist, add new wholesaler with item
        updatedCart.push({
          ...wholesaler,
          items: [{ ...item, quantity: quantity }]
        });
      }
  
      return { cart: updatedCart };
    }),
  
    removeItem: (wholesalerName, itemName) => set((state) => {
      const updatedCart = state.cart.map(wholesaler => {
        if (wholesaler.wholesaler === wholesalerName) {
          return {
            ...wholesaler,
            items: wholesaler.items.filter(item => item.name !== itemName)
          };
        }
        return wholesaler;
      }).filter(wholesaler => wholesaler.items.length > 0);
      
      return { cart: updatedCart }; 
    }),
    
    updateItemQuantity: (wholesalerName, itemName, newQuantity) => set((state) => {
      const updatedCart = state.cart.map(wholesaler => {
        if (wholesaler.wholesaler === wholesalerName) {
          return {
            ...wholesaler,
            items: wholesaler.items.map(item => {
              if (item.name === itemName) {
                return { ...item, quantity: Math.max(0, newQuantity) };
              }
              return item;
            }).filter(item => item.quantity > 0)
          };
        }
        return wholesaler;
      }).filter(wholesaler => wholesaler.items.length > 0);
      return { cart: updatedCart };
    }),
  
    clearCart: () => set({ cart: [] }),
  
    getTotal: () => {
      const state = useCart.getState();
      return state.cart.reduce((total, wholesaler) => {
        return total + wholesaler.items.reduce((subTotal, item) => {
          return subTotal + item.price * item.quantity;
        }, 0);
      }, 0);
    },
  }));
