import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from firebase_admin import auth
import argparse
import pandas as pd

parser = argparse.ArgumentParser()

# Use a service account
cred = credentials.Certificate('shoppeasauthentication-firebase-adminsdk-x6pk7-d9624e3bf1.json')
default_app = firebase_admin.initialize_app(cred)
db = firestore.client()

auth.set_custom_user_claims('63HeN3mxkZbF5bcQZgLAEyZWY2f2', {'consumer': True})