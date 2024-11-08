import pandas as pd
import random
from datetime import date
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import argparse
import pandas as pd

parser = argparse.ArgumentParser()

# Use a service account
FIREBASE_CONFIG = '<firebase-config-json-file>'
cred = credentials.Certificate(FIREBASE_CONFIG)
default_app = firebase_admin.initialize_app(cred)
db = firestore.client()

wholesalerproducts = pd.read_csv('./data/wholesalerproducts.csv')
consumer_id = pd.read_csv('./data/consumeraccount.csv')['uid']
wholesalers = pd.read_csv('./data/wholesalers.csv')['uen']

num_carts = 3
num_orders_cart = [1, 2, 3] # for each consumer, preallocated
cart_records = 6
num_history = 3
num_orders_history = [1, 1, 1] # for each consumer, preallocated
history_records = 3

def choose_wholesaler_products(uen, num_products, type='cart'):
    products = wholesalerproducts[wholesalerproducts['uen'] == uen].sample(num_products).reset_index(drop=True)
    
    products_dict = {}
    for i in range(len(products)):
        if type == 'cart':
            products_dict[i] = {
                'swp_id': products.iloc[i]['pid'], # i need swp id
                'quantity': random.randint(1, 5)
            }
        elif type == 'history':
            products_dict[i] = {
                'swp_id': products.iloc[i]['pid'], # i need swp id
                'price': products.iloc[i]['price'],
                'quantity': random.randint(1, 5)
            }

    # get total price of products
    total_price = sum([random.randint(1, 5) * products.iloc[i]['price'] for i in range(num_products)])

    return products_dict, f'{total_price:.2f}',

def gen_transactions():
    status = ['IN-CART', 'PENDING-ACCEPTANCE']    
    gen_records = []
    chosen_consumer = []
    for cnt in num_orders_cart:
        for _ in range(cnt):
            chosen_consumer.append(consumer_id[cnt])

    for i in range(cart_records):
        start_date = date.today().replace(day=1, month=1).toordinal()
        end_date = date.today().toordinal()
        random_day = date.fromordinal(random.randint(start_date, end_date)) # from the past
        chosen_wholesaler = random.choice(wholesalers)
        num_products = random.randint(1, 3)
        products, total_price = choose_wholesaler_products(chosen_wholesaler, num_products, type='cart')

        gen_records.append({
            'uen': chosen_wholesaler,
            'uid': chosen_consumer[i],
            'products': products,
            'total_price': total_price,
            'date': random_day,
            'status': status[0]
        })

    chosen_consumer_history = []
    for cnt in num_orders_history:
        for i in range(cnt):
            chosen_consumer_history.append(consumer_id[i])

    for i in range(history_records):
        start_date = date.today().replace(day=1, month=1).toordinal()
        end_date = date.today().toordinal()
        random_day = date.fromordinal(random.randint(start_date, end_date)) # from the past
        chosen_wholesaler = random.choice(wholesalers)
        num_products = random.randint(1, 3)
        products, total_price = choose_wholesaler_products(chosen_wholesaler, num_products, type='history')

        gen_records.append({
            'uen': chosen_wholesaler,
            'uid': chosen_consumer_history[i],
            'products': products,
            'total_price': total_price,
            'date': random_day,
            'status': status[1]
        })

    df = pd.DataFrame(gen_records)
    df.to_csv('./data/transactions.csv', index=False)

def gen_cart():
    transactions = pd.read_csv('./data/transactions.csv')
    transaction_orders = transactions[transactions['status'] == 'IN-CART']
    gen_records = []
    count = 0
    swp = []

    for i in range(num_carts):
        gen_records.append({
            'uid': consumer_id[i],
            'orders': [],
            'total_price': 0,
        })
        count += num_orders_cart[i]
    df = pd.DataFrame(gen_records)
    df.to_csv(f'./data/shoppingcart.csv', index=False)

def gen_order_history():
    transactions = pd.read_csv('./data/transactions.csv')
    transaction_orders = transactions[transactions['status'] == 'PENDING-ACCEPTANCE']
    gen_records = []
    count = 0

    for i in range(num_history):
        start_date = date.today().replace(day=1, month=1).toordinal()
        end_date = date.today().toordinal()
        random_day = date.fromordinal(random.randint(start_date, end_date)) # from the past

        gen_records.append({
            'uid': consumer_id[i],
            'orders': [],
            'total_price': 0,
            'date': random_day,
        })
        count += num_orders_history[i]
    df = pd.DataFrame(gen_records)
    df.to_csv(f'./data/orderhistory.csv', index=False)

def gen_wholesalerproducts():
    docs = db.collection("products").stream()
    productid = [doc.id for doc in docs]
    gen_records = []

    for wholesaler in wholesalers:
        for product in random.sample(productid, 10):
            gen_records.append({
                'uen': wholesaler,
                'pid': product,
                'price': round(random.uniform(0, 50.00), 2),
                'stock': random.randint(50, 500),
            })
    df = pd.DataFrame(gen_records)
    df.to_csv(f'./data/wholesalerproducts.csv', index=False)

task = 'transactions'
if task == 'wholesalerproducts':
    gen_wholesalerproducts()
elif task == 'transactions':
    gen_transactions()
elif task == 'cart':
    gen_cart()
elif task == 'orderHistory':
    gen_order_history()