import { View, StyleSheet, Modal, Text, TouchableOpacity} from "react-native"

const RemoveProduct = ({visible, onClose, onConfirm}) => {

    return (
        <Modal
            visible = {visible}
            transparent = {true}
            animationType="fade"
        >
            <View style={styles.removeOverlay}>
                <View style={styles.removePopup}>
                    <Text style={styles.removeText}>Confirm Removal of Product?</Text>
                    <View style={styles.removeButtons}>
                        <TouchableOpacity 
                            style={styles.removeButton} 
                            onPress={() => onClose()}
                        >
                            <Text style={styles.removeButtonText}>Cancel</Text>
                        </TouchableOpacity>
                        <TouchableOpacity 
                            style={styles.removeButton}
                            onPress={() => onConfirm()}
                        >
                            <Text style={styles.removeButtonText}>Confirm</Text>
                        </TouchableOpacity>
                    </View>
                </View>
            </View>
        </Modal>
    )
}

const styles = StyleSheet.create({
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
        borderWidth: '10%',
        borderColor: '#EBF3D1',
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
})

export default RemoveProduct;   