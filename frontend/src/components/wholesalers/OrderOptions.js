import { useState } from "react";
import { View } from "react-native"

const OrderOptions = ({ options, defaultValue, renderContent }) => {
    const[visible, setVisible]=useState(false);
    const[selected, setSelected]=useState(defaultValue);

    return (
    <View style={styles.optionsContainer}>
        <View style={styles.optionsHeader}>
        <TouchableOpacity
            style={styles.dropdown}
            onPress={() => setVisible(true)}
        >
            <Text style={styles.dropdownText}>{selected}</Text>
            <Ionicons name="chevron-down" size={24} color="#0C5E52" />
        </TouchableOpacity>
        <TouchableOpacity style={styles.filterButton}>
            <Ionicons name="funnel-outline" size={24} color="#0C5E52" />
        </TouchableOpacity>
        </View>

        <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => setModalVisible(false)}
        >
        <View style={styles.modalView}>
            <FlatList
            data={options}
            renderItem={({ item }) => (
                <TouchableOpacity
                style={styles.option}
                onPress={() => onItemPress(item)}
                >
                <Text style={styles.optionText}>{item}</Text>
                </TouchableOpacity>
            )}
            keyExtractor={(item) => item}
            />
        </View>
        </Modal>

        <View style={styles.content}>
        {renderContent(selected)}
        </View>
    </View>
    )
}

export default OrderOptions;