import pandas as pd

# df= pd.read_csv('./data/wholesalerproducts.csv')

# df.drop('swp_id', axis=1, inplace=True)

# df.to_csv('./data/wholesalerproducts.csv', index=False)
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


docs = db.collection("wholesalerproducts").stream()

for doc in docs:
    print(f"{doc.id} => {doc.to_dict()}")