import pandas as pd
import random
from datetime import date
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

wholesalerproducts = pd.read_csv('./data/wholesalerproducts.csv')
consumer_id = pd.read_csv('./data/consumeraccount.csv')['uid']
wholesalers = pd.read_csv('./data/wholesalers.csv')['uen']

def choose_wholesaler_products(uen, num_products):
    products = wholesalerproducts[wholesalerproducts['uen'] == uen].sample(num_products)
    # get total price of products
    total_price = [random.randint(1, 5) * products['price'][i] for i in range(num_products)].sum()

    return '|'.join(products['pid'].values), total_price

def gen_transactions():
    cart_records = 10
    history_records = 9
    status = ['IN-CART', 'PENDING-ACCEPTANCE']
    gen_records = []
    for i in range(cart_records):
        start_date = date.today().replace(day=1, month=1).toordinal()
        end_date = date.today().toordinal()
        random_day = date.fromordinal(random.randint(start_date, end_date)) # from the past
        chosen_wholesaler = random.choice(wholesalers)
        num_products = random.randint(1, 3)
        products, total_price = choose_wholesaler_products(chosen_wholesaler, num_products)

        gen_records.append({
            'uen': chosen_wholesaler,
            'uid': consumer_id[i],
            'products': products, # clarify with saffron currently returning pid but i think it should be an object
            'total_price': total_price,
            'date': random_day,
            'status': status[0]
        })

    for i in range(history_records):
        start_date = date.today().replace(day=1, month=1).toordinal()
        end_date = date.today().toordinal()
        random_day = date.fromordinal(random.randint(start_date, end_date)) # from the past
        chosen_wholesaler = random.choice(wholesalers)
        num_products = random.randint(1, 3)
        products, total_price = choose_wholesaler_products(chosen_wholesaler, num_products)

        gen_records.append({
            'uen': chosen_wholesaler,
            'uid': consumer_id[i],
            'products': products,
            'total_price': total_price,
            'date': random_day,
            'status': status[0]
        })

    df = pd.DataFrame(gen_records)
    df.to_csv('./data/orders2.csv', index=False)

def gen_cart():
    transactions = pd.read_csv('./data/transactions.csv')
    transaction_orders = transactions[transactions['type'] == 'IN-CART']
    num_carts = 5
    num_orders = [1, 2, 3, 2, 2] # for each consumer, preallocated
    gen_records = []
    count = 0

    for i in range(num_carts):
        chosen_orders = transaction_orders[count:num_orders[i]]
        orders = '|'.join(chosen_orders['tid'].values)
        total = (chosen_orders['price'] * chosen_orders['quantity']).sum()

        gen_records.append({
            'uid': consumer_id[i],
            'orders': orders,
            'total_price': f'{total:.2f}',
        })
        count += num_orders[i]
    df = pd.DataFrame(gen_records)
    df.to_csv(f'./data/transactions2.csv', index=False)

def gen_order_history():
    transactions = pd.read_csv('./data/transactions.csv')
    transaction_orders = transactions[transactions['type'] == 'PENDING-ACCEPTANCE']
    num_history = 3
    num_orders = [4, 2, 3] # for each consumer, preallocated
    gen_records = []
    count = 0

    for i in range(num_history):
        chosen_orders = transaction_orders[count:num_orders[i]]
        orders = '|'.join(chosen_orders['tid'].values)
        total = (chosen_orders['price'] * chosen_orders['quantity']).sum()

        gen_records.append({
            'uid': consumer_id[i],
            'orders': orders,
            'total_price': f'{total:.2f}',
            'date': chosen_orders['date'].values[0],
        })
        count += num_orders[i]
    df = pd.DataFrame(gen_records)
    df.to_csv(f'./data/transactions2.csv', index=False)

def gen_wholesalerproducts():
    docs = db.collection("products").stream()
    productid = [doc.id for doc in docs]
    gen_records = []
    for product in productid:
        for wholesaler in wholesalers:
            gen_records.append({
                'uen': wholesaler,
                'pid': product,
                'price': round(random.uniform(0, 50.00), 2),
                'stock': random.randint(50, 500),
            })
    df = pd.DataFrame(gen_records)
    df.to_csv(f'./data/wholesalerproducts2.csv', index=False)

task = 'wholesalerproducts'
if task == 'wholesalerproducts': # plan for this is to create the same 20 products for each of the 5 wholesalers
    gen_wholesalerproducts()
elif task == 'transactions': # will create cart and order history records (in-cart / pending-acceptance only)
    gen_transactions()
elif task == 'cart':
    gen_cart()
elif task == 'orderHistory':
    gen_order_history()