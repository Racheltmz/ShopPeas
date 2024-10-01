import pandas as pd
import random
from datetime import date

wholesalerproducts = pd.read_csv('./data/wholesalerproducts.csv')
consumer_id = pd.read_csv('./data/consumeraccount.csv')['uid']

def gen_orders():
    cart_records = 100
    transaction_records = 100
    order_type = ['CART', 'TRANSACTION']
    id = 0
    gen_records = []
    for i in range(cart_records):
        chosen_record = wholesalerproducts.sample(1)
        gen_records.append({
            'oid': f'O{(id+i+1):06d}',
            'swp_id': chosen_record['swp_id'].values[0],
            'quantity': random.randint(1, 5),
            'price': chosen_record['price'].values[0],
            'type': order_type[0]
        })

    id = cart_records + 1
    for i in range(transaction_records):
        chosen_record = wholesalerproducts.sample(1)
        gen_records.append({
            'oid': f'O{(id+i+1):06d}',
            'swp_id': chosen_record['swp_id'].values[0],
            'quantity': random.randint(1, 5),
            'price': chosen_record['price'].values[0],
            'type': order_type[1]
        })

    df = pd.DataFrame(gen_records)
    df.to_csv('./data/orders.csv', index=False)

def gen_cart():
    orders = pd.read_csv('./data/orders.csv')
    cart_orders = orders[orders['type'] == 'CART']
    num_carts = 9
    gen_records = []

    for i in range(num_carts):
        chosen_orders = cart_orders[i*10:(i+1)*10+1]
        orders = '|'.join(chosen_orders['oid'].values)
        total = (chosen_orders['price'] * chosen_orders['quantity']).sum()
        gen_records.append({
            'cid': f'C{(i+1):06d}',
            'uid': consumer_id[i],
            'orders': orders,
            'total_price': f'{total:.2f}'
        })
    df = pd.DataFrame(gen_records)
    df.to_csv(f'./data/shoppingcart.csv', index=False)

def gen_transaction():
    orders = pd.read_csv('./data/orders.csv')
    cart_orders = orders[orders['type'] == 'TRANSACTION']
    num_carts = 9
    gen_records = []
    status = ['PENDING-ACCEPTANCE', 'PENDING-COMPLETION', 'COMPLETED']

    for i in range(num_carts):
        start_date = date.today().replace(day=1, month=1).toordinal()
        end_date = date.today().toordinal()
        random_day = date.fromordinal(random.randint(start_date, end_date))

        chosen_orders = cart_orders[i*10:(i+1)*10+1]
        orders = '|'.join(chosen_orders['oid'].values)
        total = (chosen_orders['price'] * chosen_orders['quantity']).sum()
        gen_records.append({
            'tid': f'T{(i+1):06d}',
            'uid': consumer_id[i],
            'orders': orders,
            'total_price': f'{total:.2f}',
            'date': random_day,
            'status': status[random.randint(0, 2)]
        })
    df = pd.DataFrame(gen_records)
    df.to_csv(f'./data/transactions.csv', index=False)

task = 'transaction'
if task == 'order':
    gen_orders()
elif task == 'cart':
    gen_cart()
elif task == 'transaction':
    gen_transaction()