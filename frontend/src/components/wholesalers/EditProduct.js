import React, { useState } from 'react';
import { View, StyleSheet, Text, TouchableOpacity, Modal, TextInput } from "react-native"
import { Ionicons } from '@expo/vector-icons';

const EditProduct = ({visible, onClose, onEdit, initialPrice, initialStock}) => {
    const [editedPrice, setEditedPrice] = useState(initialPrice.toString());
    const [editedStock, setEditedStock] = useState(initialStock.toString());

    const handleEdit = () => {
        onEdit({
            price: parseFloat(editedPrice),
            stock: parseInt(editedStock),
        });
        onClose();
    };

    return(
        <Modal
            visible = {visible}
            transparent = {true}
            animationType="fade"
        >
            <View style = {styles.editOverlay}>
                <View style = {styles.editPopup}>
                    <View style = {styles.editHeaderContainer}>
                        <Text style = {styles.editHeader}>Edit Product Details</Text>
                        <TouchableOpacity style = {styles.editClose} onPress={onClose}>
                            <Ionicons name="close-circle-outline" size={28} color="#0C5E52" />
                        </TouchableOpacity>
                    </View>
                    <View style = {styles.editContainer}>
                        <Text style={styles.editInputLabel}>Price:</Text>
                        <TextInput
                            style={styles.editInput}
                            value={editedPrice}
                            onChangeText={setEditedPrice}
                            placeholder="Price"
                            keyboardType="numeric"
                        />
                        <Text style={styles.editStockLabel}>Stock:</Text>
                        <View style={styles.stockControl}>
                            <TouchableOpacity style={styles.stockButton} onPress={() => setEditedStock((prev) => (parseInt(prev) - 1).toString())}>
                                <Text style={styles.stockButtonText}>-</Text>
                            </TouchableOpacity>
                            <TextInput
                                style={styles.stockInput}
                                value={editedStock}
                                onChangeText={setEditedStock}
                                keyboardType="numeric"
                            />
                            <TouchableOpacity style={styles.stockButton} onPress={() => setEditedStock((prev) => (parseInt(prev) + 1).toString())}>
                                <Text style={styles.stockButtonText}>+</Text>
                            </TouchableOpacity>
                        </View>
                    </View>
                    <View style={styles.editButtons}>
                        <TouchableOpacity 
                            style={styles.editButton}
                            onPress={handleEdit}
                        >
                            <Text style={styles.editButtonText}>Update</Text>
                        </TouchableOpacity>
                    </View>
                </View>
            </View>
        </Modal>
    )
}

export default EditProduct;   

const styles = StyleSheet.create({
    editOverlay: {
        flex: 1,
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        justifyContent: 'flex-start',
        alignItems: 'center',
    },
    editPopup: {
        backgroundColor: 'white',
        padding: '5%',
        borderRadius: '20%',
        borderWidth: '10%',
        borderColor: '#EBF3D1',
        alignItems: 'center',
        alignSelf: 'center',
        marginTop: '70%',
        width: '80%',
        shadowColor: "#000",
        shadowOffset: {
            width: 0,
            height: 2,
        },
        shadowOpacity: 0.25,
        shadowRadius: 3.84,
        elevation: 5,
    },
    editHeaderContainer: {
        flexDirection: 'row',
    },
    editHeader: {
        fontSize: 23,
        left:"30%",
        marginBottom: "5%",
    },
    editClose:{
        left: "60%",
    },
    editInputLabel: {
        fontSize: 16,
        color: '#0C5E52',
        marginBottom: 5,
        marginTop: 10,
    },
    editInput: {
        borderWidth: 1,
        borderColor: '#0C5E52',
        borderRadius: 5,
        padding: 10,
        marginBottom: 15,
        width: 270,
    },
    editStockLabel:{
        fontSize: 16,
        color: '#0C5E52',
        marginBottom: '3%',
        marginTop: '3%',
        alignSelf: 'center',
    },
    stockControl: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 5,
        alignSelf: 'center',
    },
    stockButton: {
        width: 30,
        height: 30,
        backgroundColor: '#0C5E52',
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 15,
    },
    stockButtonText: {
        color: 'white',
        fontSize: 20,
    },
    stockInput: {
        borderWidth: 1,
        borderColor: '#0C5E52',
        borderRadius: 5,
        padding: 5,
        width: 50,
        textAlign: 'center',
        marginHorizontal: 10,
    },
    editButtons: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        width: '100%',
    },
    editButton:{
        marginTop: '5%',
        backgroundColor: '#EBF3D1',
        borderRadius: 10,
        borderWidth: 2,
        borderColor: '#B8C99B',
    },
    editButtonText:{
        color: '#0C5E52',
        fontSize: 20,
        paddingVertical: 10,
        paddingHorizontal: 20,
    },
})