import React, { useState } from 'react';
import { Image, StyleSheet, View, Text, TouchableOpacity, Modal} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import EditProduct from './EditProduct';
import RemoveProduct from './RemoveProduct';

const WholesalerProduct = ({ index, name, price, unit, stock, image_url, onRemove, onEdit}) => {
    const[removeVisible, setRemoveVisible] = useState(false);
    const[editVisible, setEditVisible] = useState(false);

    const handleRemove = () => {
        onRemove(index);
        setRemoveVisible(false);
    };

    const handleEdit = (updatedProduct) => {
        onEdit(index, updatedProduct);
        setEditVisible(false);
    };

    return (
        <View style={styles.productItem}>
            <View style={styles.imageContainer}>
                <Image source={{ uri: image_url }} style={styles.productImage} />
            </View>
            <View style={styles.productInfo}>
                <Text style={styles.productName}>{name}</Text>
                <Text style={styles.productPrice}>${price.toFixed(2)}</Text>
                <Text style={styles.productUnit}>{unit}</Text>
            </View>
            <View style={styles.productActions}>
                <Text style={styles.stockText}>{stock} left in stock</Text>
                <View style={styles.actionIcons}>
                    <TouchableOpacity style={styles.actionIcon} onPress={()=>setRemoveVisible(true)}>
                        <Ionicons name="trash-outline" size={28} color="#0C5E52" />
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.actionIcon} onPress={()=>setEditVisible(true)}>
                        <Ionicons name="create-outline" size={28} color="#0C5E52" />
                    </TouchableOpacity>
                </View>
            </View>

            <RemoveProduct 
                visible={removeVisible}
                onClose={() => setRemoveVisible(false)}
                onConfirm={handleRemove}
            />

            <EditProduct 
                visible={editVisible}
                onClose={() => setEditVisible(false)}
                onEdit={handleEdit}
                initialPrice={price}
                initialStock={stock}
            />
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
    imageContainer: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    productImage: {
        width: 75,
        height: 75,
        borderRadius: 5,
    },
    productInfo: {
        flex: 1,
        marginHorizontal: 16,
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
    productActions: {
        justifyContent: 'space-between',
    },
    stockText: {
        fontSize: 12,
        color: '#0C5E52',
    },
    actionIcons: {
        flexDirection: 'row',
        justifyContent: 'flex-end',
        marginTop: 10,
    },
    actionIcon: {
        marginLeft: 15,
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
});

export default WholesalerProduct;