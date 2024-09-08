import { StyleSheet, Text, View } from "react-native";

const FilteredProducts = (props) => {
  return (
    <View style={styles.productsContainer}>
      <Text>HELLo</Text>
      <Text>WHATS POPPING</Text>
    </View>
  );
};

export default FilteredProducts;

const styles = StyleSheet.create({
  productsContainer: {
    flex: 2,
    marginHorizontal: "auto",
    marginTop: 20,
    height: 400,
    width: 400,
    alignItems: "center",
    // backgroundColor: "red",
  },
});
