import React, { useState } from 'react';
import { StyleSheet, View, Text, TouchableOpacity, Modal, TextInput} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { SafeAreaView } from 'react-native-safe-area-context';

const WholesalerProduct = ({ index, name, price, unit, stock, description, onRemove, onEdit}) => {
    const[removeVisible, setRemoveVisible] = useState(false);
    const[editVisible, setEditVisible] = useState(false);
    const [editedName, setEditedName] = useState(name);
    const [editedPrice, setEditedPrice] = useState(price.toString());
    const [editedDescription, setEditedDescription] = useState(description);
    const [editedStock, setEditedStock] = useState(stock.toString());

    const handleRemove = () => {
        onRemove(index);
        setRemoveVisible(false);
    };

    const handleEdit = () => {
        onEdit(index, {
            name: editedName,
            price: parseFloat(editedPrice),
            description: editedDescription,
            stock: parseInt(editedStock),
        });
        setEditVisible(false);
    };


    return (
        <View style={styles.productItem}>
            <View style={styles.productImage} />
            <View style={styles.productInfo}>
                <Text style={styles.productName}>{name}</Text>
                <Text style={styles.productPrice}>${price.toFixed(2)}</Text>
                <Text style={styles.productUnit}>{unit}</Text>
                <Text style={styles.productDescription}>{description}</Text>
            </View>
            <View style={styles.productActions}>
                <Text style={styles.stockText}>{stock} left in stock</Text>
                <View style={styles.actionIcons}>
                    <TouchableOpacity  onPress={()=>setRemoveVisible(true)}>
                        <Ionicons name="trash-outline" size={28} color="#0C5E52" />
                    </TouchableOpacity>
                    <TouchableOpacity onPress={()=>setEditVisible(true)}>
                        <Ionicons name="create-outline" size={28} color="#0C5E52" />
                    </TouchableOpacity>
                </View>
            </View>
            <Modal
                visible = {removeVisible}
                transparent = {true}
                animationType="fade"
            >
                <View style={styles.removeOverlay}>
                    <View style={styles.removePopup}>
                        <Text style={styles.removeText}>Confirm Removal of Product?</Text>
                        <View style={styles.removeButtons}>
                            <TouchableOpacity 
                                style={styles.removeButton} 
                                onPress={() => setRemoveVisible(false)}
                            >
                                <Text style={styles.removeButtonText}>Cancel</Text>
                            </TouchableOpacity>
                            <TouchableOpacity 
                                style={styles.removeButton}
                                onPress={handleRemove}
                            >
                                <Text style={styles.removeButtonText}>Confirm</Text>
                            </TouchableOpacity>
                        </View>
                    </View>
                </View>
            </Modal>
            <Modal
                visible = {editVisible}
                transparent = {true}
                animationType="fade"
            >
                <View style = {styles.editOverlay}>
                    <View style = {styles.editPopup}>
                        <View style = {styles.editHeaderContainer}>
                            <Text style = {styles.editHeader}>Edit Product Details</Text>
                            <TouchableOpacity style = {styles.editClose} onPress={() => setEditVisible(false)}>
                                <Ionicons name="close-circle-outline" size={28} color="#0C5E52" />
                            </TouchableOpacity>
                        </View>
                        <View style = {styles.editContainer}>
                            <Text style={styles.editInputLabel}>Product Name:</Text>
                            <TextInput
                                style={styles.editInput}
                                value={editedName}
                                onChangeText={setEditedName}
                                placeholder="Product Name"
                            />
                            <Text style={styles.editInputLabel}>Price:</Text>
                            <TextInput
                                style={styles.editInput}
                                value={editedPrice}
                                onChangeText={setEditedPrice}
                                placeholder="Price"
                                keyboardType="numeric"
                            />
                            <Text style={styles.editInputLabel}>Description:</Text>
                            <TextInput
                                style={styles.editInput}
                                value={editedDescription}
                                onChangeText={setEditedDescription}
                                placeholder="Description"
                            />
                            <Text style={styles.editInputLabel}>Stock:</Text>
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
        </View>
    )
}

const styles = StyleSheet.create({
    productItem: {
        flexDirection: 'row',
        backgroundColor: '#EBF3D1',
        margin: 10,
        padding: 20,
        borderRadius: 10,
    },
    productImage: {
        width: 60,
        height: 60,
        backgroundColor: '#E0E0E0',
        borderRadius: 5,
    },
    productInfo: {
        flex: 1,
        marginLeft: 15,
    },
    productName: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    productPrice: {
        fontSize: 16,
        color: '#0C5E52',
    },
    productUnit: {
        fontSize: 14,
        color: 'gray',
    },
    productDescription: {
        fontSize: 14,
        color: 'gray',
    },
    productActions: {
        justifyContent: 'space-between',
    },
    stockText: {
        fontSize: 12,
        color: '#0C5E52',
    },
    actionIcons: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        marginTop: 10,
    },
    footer: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        backgroundColor: 'white',
        paddingVertical: 10,
    },
    footerItem: {
        alignItems: 'center',
    },
    footerText: {
        fontSize: 12,
        color: '#0C5E52',
    },
    sellText: {
        color: '#FF9F67',
    },
    removeOverlay: {
        flex: 1,
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        justifyContent: 'flex-start',
        alignItems: 'center',
    },
    removePopup: {
        backgroundColor: 'white',
        padding: 20,
        borderRadius: 20,
        alignItems: 'center',
        alignSelf: 'center',
        marginTop: '90%',
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
    removeText: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    removeButtons: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        width: '100%',
    },
    removeButton:{
        marginTop: '5%',
    },
    removeButtonText:{
        color: '#0C5E52',
    },
    editOverlay: {
        flex: 1,
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        justifyContent: 'flex-start',
        alignItems: 'center',
    },
    editPopup: {
        backgroundColor: 'white',
        padding: 20,
        borderRadius: 20,
        alignItems: 'center',
        alignSelf: 'center',
        marginTop: '90%',
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
        left: "110%",
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
    stockControl: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 5,
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
    },
    editButtonText:{
        color: '#0C5E52',
        fontSize: 15,
    },
});

export default WholesalerProduct;