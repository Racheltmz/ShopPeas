import React, { useState } from 'react';
import { Text, StyleSheet, View, TouchableOpacity, Image} from 'react-native';
import { createUserWithEmailAndPassword } from 'firebase/auth';
import { FirebaseAuth, FirebaseDb } from '../../lib/firebase';
import { Dialog, ALERT_TYPE } from 'react-native-alert-notification';
import { Ionicons } from '@expo/vector-icons';
import authService from '../../service/AuthService';
import WholesalerDetails from './WholesalerDetails';
import Address from './Address';
import WholesalerBank from './WholesalerBank';
import Password from './Password';
import Alert from '../utils/Alert';

const RegisterWholesaler = ({onBackPress, onRegComplete}) => {
    const [step, setStep] = useState(1);
    const [isLoading, setIsLoading] = useState(false);
    const [alertVisible, setAlertVisible] = useState(false);
    const [customAlert, setCustomAlert] = useState({ title: '', message: '', onConfirm: () => {} });
    const [formData, setFormData] = useState({
        name: '',
        uen: '',
        email: '',
        phone_number: '',
        currency: 'SGD',
        street_name: '',
        unit_no: '',
        building_name: '',
        city: '',
        postal_code: '',
        bank_account_name: '',
        bank_account_no: '',
        bank: '',
        password: '',
        confirm_password: '',
    });

    const auth = FirebaseAuth;
    const db = FirebaseDb;

    const handleInputChange = (field, value) => {
        setFormData({ ...formData, [field]: value });
    }

    const validatePassword = (password) => {
        const regex = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{12,}$/;
        return regex.test(password);
    };

    const validateEmail = (email) => {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    };

    const validatePhone = (phone_number) => {
        const regex = /^(\+65 )?\d{8}$/; 
        return regex.test(phone_number);
    };

    const validateField = (field, value) => {
        return value.trim() !== '';
    };

    const formatFieldName = (fieldName) => {
        return fieldName
            .split('_')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    };

    const showAlert = (title, message, onConfirm) => {
        setCustomAlert({ title, message, onConfirm });
        setAlertVisible(true);
    };

    const handleRegister = async () => {
        // left building name out of required fields for now
        const requiredFields = ['name', 'uen', 'email', 'phone_number', 'street_name', 'unit_no', 'city', 'postal_code', 'bank_account_name', 'bank_account_no', 'bank', 'password', 'confirm_password'];
        for (let field of requiredFields) {
            if (!validateField(field, formData[field])) {
                showAlert("Error", `${formatFieldName(field)} cannot be empty`, () => setAlertVisible(false));
                return;
            }
        }

        if (!validateEmail(formData.email)) {
            showAlert("Error", "Please enter a valid email address", () => setAlertVisible(false));
            return;
        }

        if (!validatePhone(formData.phone_number)) {
            showAlert("Error", "Please enter a valid 8-digit phone number", () => setAlertVisible(false));
            return;
        }

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
            const res = await createUserWithEmailAndPassword(auth, formData.email, formData.password);

            // API call to add user details into database collections
            authService.register(res.user.uid, "wholesaler", formData)
                .catch((err) => {
                    Dialog.show({
                        type: ALERT_TYPE.DANGER,
                        title: err.status.code,
                        textBody: err.message,
                        button: 'close',
                    })
                })

            // TODO: fix this navigation it jumps to auth page after reg
        } catch (err) {
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
                return <Address formData={formData} handleInputChange={handleInputChange} />;
            case 3:
                return <WholesalerBank formData={formData} handleInputChange={handleInputChange} />;
            case 4:
                return <Password formData={formData} handleInputChange={handleInputChange} />;
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
        width: '95%',
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
