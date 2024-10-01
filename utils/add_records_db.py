import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from firebase_admin import auth
import argparse
import pandas as pd

parser = argparse.ArgumentParser()

# Use a service account
cred = credentials.Certificate('shoppeasauthentication-firebase-adminsdk-x6pk7-0e6ec030a9.json')
default_app = firebase_admin.initialize_app(cred)
db = firestore.client()

parser = argparse.ArgumentParser(
    description="Data Generation for ShopPeas"
)

parser.add_argument(
    '--collection',
    type=str,
    help='Which collection to insert the records into'
)

parser.add_argument(
    '--input_csv',
    type=str,
    help='Path to input csv file containing the records'
)

# Create consumers/wholesalers
# role: either 'consumer' or 'wholesaler'
def registerUsers(df, role):
    for _, record in df.iterrows():
        if role == 'consumer':
            displayName = record['first_name'] + ' ' + record['last_name']
        elif role == 'wholesaler':
            displayName = record['name']
        
        # Inserts record into firebase authentication
        user = auth.create_user(
            email=record['email'],
            email_verified=False,
            phone_number=record['phone_number'],
            password=record['password'],
            display_name=displayName,
            disabled=False)
        
        # Sets user's role for role-based authentication
        auth.set_custom_user_claims(user.uid, {role: True})
 
        # Adds user details to respective role collections
        if role == 'consumer':
            db.collection(collection).document(user.uid).set({
                "firstName": record['firstName'],
                "lastName": record['lastName'],
                "email": record['email'],
                "phoneNumber": record['phoneNumber'],
            })
        elif role == 'wholesaler':
            db.collection(collection).document(user.uid).set({
                "uen": record['UEN'],
                "name": record['name'],
                "email": record['email'],
                "phoneNumber": record['phoneNumber'],
                "currency": record['currency'],
                "rating": record['rating'],
                "numRatings": record['numRatings'],
            })

        print(f'Sucessfully created new {role}: {user.uid}')

def wholesalerAccount(df, collection):
    for _, record in df.iterrows():
        db.collection(collection).document(record['uen']).set({
            "bank": record['bank'],
            "bank_account_name": record['bank_account_name'],
            "bank_account_no": record['bank_account_no'],
        })
    print(f'Sucessfully created new wholesaler record.')

def wholesalerAddress(df, collection):
    for _, record in df.iterrows():
        if record['unit_no'] == 'null':
            record['unit_no'] = None
        if record['building_name'] == 'null':
            record['building_name'] = None
        db.collection(collection).document(record['uen']).set({
            "street_name": record['street_name'],
            "unit_no": record['unit_no'],
            "building_name": record['building_name'],
            "city": record['city'],
            "postal_code": record['postal_code'],
        })
    print(f'Sucessfully created new wholesaler address.')

def consumerAccount(df, collection):
    for _, record in df.iterrows():
        db.collection(collection).document(record['uid']).set({
            "uid": record['uid'],
            "name": record['name'],
            "card_no": record['card_no'],
            "expiry_date": record['expiry_date'],
            "cvv": record['cvv'],
        })
    print(f'Sucessfully created new consumer account.')

def consumerAddress(df, collection):
    for _, record in df.iterrows():
        if record['unit_no'] == 'null':
            record['unit_no'] = None
        if record['building_name'] == 'null':
            record['building_name'] = None
        db.collection(collection).document(record['uid']).set({
            "uid": record['uid'],
            "street_name": record['street_name'],
            "unit_no": record['unit_no'],
            "building_name": record['building_name'],
            "city": record['city'],
            "postal_code": record['postal_code'],
        })
    print(f'Sucessfully created new consumer address.')

def transactions(df, collection):
    for _, record in df.iterrows():
        db.collection(collection).document(record['tid']).set({
            "uid": record['uid'],
            "orders": record['orders'].split('|'),
            "total_price": record['total_price'],
            "date": record['date'],
            "status": record['status'],
        })
    print(f'Sucessfully created transaction records.')

def orders(df, collection):
    for _, record in df.iterrows():
        db.collection(collection).document(record['oid']).set({
            "swp_id": record['swp_id'],
            "quantity": record['quantity'],
            "price": record['price'],
            "type": record['type'],
        })
    print(f'Sucessfully created order records.')

def product(df, collection):
    for _, record in df.iterrows():
        if record['package_size'] == 'null':
            record['package_size'] = None
        if pd.isnull(record['Image']) or record['Image'] == 'null':
            image_url = None  # Set image URL to None if not available
        else:
            image_url = record['Image']
        db.collection(collection).document(str(record['pid'])).set({
            "name": record['name'],  # Product name
            "package_size": record['package_size'],  # Package size
            "image_url": image_url  # Image URL from the CSV file
        })
    print(f'Sucessfully created and added products.')

def wholesalerProduct(df, collection):
    for _, record in df.iterrows():
        if record['price'] == 'null':
            record['price'] = None
        if record['stock'] == 'null':
            record['stock'] = None
        db.collection(collection).document(record['swp_id']).set({
            "uen": record['uen'],
            "pid": record['pid'],
            "price": record['price'],
            "stock": record['stock'],
        })
    print(f'Sucessfully created and added wholesaler products.')


def shoppingCart(df, collection):
    for _, record in df.iterrows():
        db.collection(collection).document(record['cid']).set({
            "uid": record['uid'],
            "orders": record['orders'].split('|'),
            "total_price": record['total_price'],
        })
    print(f'Sucessfully added entries into the shopping cart.')


# Parse argument
args = parser.parse_args()
collection = args.collection
ds = args.input_csv
df = pd.read_csv(ds)

if (collection == 'wholesaler' or collection == 'consumer'):
    registerUsers(df, collection)
elif (collection == 'wholesaler_account'):
    wholesalerAccount(df, collection)
elif(collection == 'wholesaler_address'):
    wholesalerAddress(df, collection)
elif (collection == 'consumer_account'):
    consumerAccount(df, collection)
elif(collection == 'consumer_address'):
    consumerAddress(df, collection)
elif(collection == 'transactions'):
    transactions(df, collection)
elif(collection == 'orders'):
    orders(df, collection)
elif (collection == 'product'):
    product(df, collection)
elif(collection == 'wholesaler_products'):
    wholesalerProduct(df, collection)
elif(collection == 'shopping_cart'):
    shoppingCart(df, collection)