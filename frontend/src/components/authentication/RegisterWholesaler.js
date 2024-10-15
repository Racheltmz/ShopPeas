import React, { useState } from 'react';
import { Text, StyleSheet, View, TextInput, TouchableOpacity, Image} from 'react-native';
import { createUserWithEmailAndPassword } from 'firebase/auth';
import { FirebaseAuth, FirebaseDb } from '../../lib/firebase';
import { setDoc, doc } from 'firebase/firestore';
import { Ionicons } from '@expo/vector-icons';
import WholesalerDetails from './WholesalerDetails';
import WholesalerAddress from './WholesalerAddress';
import WholesalerBank from './WholesalerBank';
import WholesalerPassword from './WholesalerPassword';
import Alert from './Alert';

const RegisterWholesaler = ({onBackPress, onRegComplete}) => {
    const [step, setStep] = useState(1);
    const [isLoading, setIsLoading] = useState(false);
    const [alertVisible, setAlertVisible] = useState(false);
    const [customAlert, setCustomAlert] = useState({ title: '', message: '', onConfirm: () => {} });
    const [formData, setFormData] = useState({
        name: '',
        uen: '',
        email: '',
        street_name: '',
        unit_no: '',
        building_name: '',
        city: '',
        postal_code: '',
        bank_account_name: '',
        account_no: '',
        bank: '',
        password: '',
        confirm_password: '',
    });

    const handleInputChange = (field, value) => {
        setFormData({ ...formData, [field]: value });
    }

    const validatePassword = (password) => {
        const regex = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{12,}$/;
        return regex.test(password);
    };

    const showAlert = (title, message, onConfirm) => {
        setCustomAlert({ title, message, onConfirm });
        setAlertVisible(true);
    };

    const handleRegister = async () => {
        if (formData.password !== formData.confirm_password) {
            showAlert("Error", "Passwords do not match", () => setAlertVisible(false));
            return;
        }
      
        if (!validatePassword(formData.password)) {
            showAlert("Error", "Password does not meet the required criteria", () => setAlertVisible(false));
            return;
        }

        setIsLoading(true);
        try {
            const res = await createUserWithEmailAndPassword(FirebaseAuth, formData.email, formData.password);
            await setDoc(doc(FirebaseDb, "users", res.user.uid), {
                name: formData.name,
                uen: formData.uen,
                email: formData.email,
                address: `${formData.street_name}, ${formData.unit_no}, ${formData.building_name}, ${formData.city}, ${formData.postal_code}`,
                bank_account_name: formData.bank_account_name,
                account_no: formData.account_no,
                bank: formData.bank,
                created: new Date(),
                id: res.user.uid,
                role: "wholesaler",
            });
            showAlert(
                "Registration Successful",
                "Your account has been created successfully!",
                () => {
                    setAlertVisible(false);
                    onRegComplete();
                }
            );
        } catch (err) {
        console.error(err)
            showAlert("Error", "Registration failed: " + err.message, () => setAlertVisible(false));
        } finally {
            setIsLoading(false);
        }
    };

    const handleNext = () => {
        if (step < 4) {
            setStep(step + 1);
        } else {
            handleRegister();
        }
    };

    const handleBack = () => {
        if (step > 1) {
            setStep(step - 1);
        } else {
            showAlert(
                "Exit Registration",
                "Are you sure you want to exit? All entered data will be lost.",
                () => {
                setFormData(formData);
                onBackPress();
                }
            );
        }
    };

    const renderStep = () => {
        switch(step) {
            case 1:
                return <WholesalerDetails formData={formData} handleInputChange={handleInputChange} />;
            case 2:
                return <WholesalerAddress formData={formData} handleInputChange={handleInputChange} />;
            case 3:
                return <WholesalerBank formData={formData} handleInputChange={handleInputChange} />;
            case 4:
                return <WholesalerPassword formData={formData} handleInputChange={handleInputChange} />;
            default:
                return null;
        }
    };

    return (
        <View style={styles.container}>
            <TouchableOpacity style={styles.backButton} onPress={handleBack}>
                <Ionicons name="arrow-back" size={24} color="#EBF3D1" />
            </TouchableOpacity>
        
            <View style={styles.headerContainer}>
                <Text style={styles.regText}>Register as a Business Owner.</Text>
                <Image
                    source={require('../../../assets/imgs/businessIcon.png')}
                    style={styles.image}
                />
            </View>

            {renderStep()}

            <Text style={styles.stepIndicator}>Step {step}/4</Text>

            <TouchableOpacity style={styles.nextButton} onPress={handleNext} disabled={isLoading}>
                <Text style={styles.nextButtonText}>{step === 4 ? 'REGISTER' : 'NEXT'}</Text>
                <Ionicons name="arrow-forward" size={24} color="#EBF3D1" style={styles.arrowIcon} />
            </TouchableOpacity>

            <Alert
                visible={alertVisible}
                title={customAlert.title}
                message={customAlert.message}
                onConfirm={() => {
                setAlertVisible(false);
                customAlert.onConfirm();
                }}
                onCancel={() => setAlertVisible(false)}
            />
        </View>
    );
};

export default RegisterWholesaler;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    backButton: {
        position: 'absolute',
        top: '0%',
        left: '-7%',
        width: '12%',
        height: '5.2%',
        borderRadius: '100%',
        backgroundColor: '#0C5E52',
        justifyContent: 'center',
        alignItems: 'center',
    },
    headerContainer: {
        flexDirection: 'row',
        width: '100%',
        backgroundColor: '#0C5E5230',
        padding: '6%', 
        borderRadius: '10%',
        marginBottom: '5%',
    },
    regText: {
        color: '#0C5E52',
        fontWeight: 'bold',
        fontSize: '26%',
        alignSelf: 'left',
    },
    image: {
        width: '28%',
        height: "100%",
        marginBottom: '5%',
    },
    stepIndicator: {
        fontSize: '15%',
        alignSelf: 'center',
        color: '#0C5E52',
        fontWeight: 'bold',
        marginTop: '5%'
    },
    nextButton: {
        flexDirection: 'row',
        backgroundColor: '#0C5E52',
        width: '50%',
        height: '5%',
        borderRadius: '20%',
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: '5%',
    },
    nextButtonText: {
        color: '#EBF3D1',
        fontWeight: 'bold',
        alignSelf: 'center'
    },
    arrowIcon: {
        position: 'absolute',
        right: '5%',
    },
});
