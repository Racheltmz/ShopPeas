import pandas as pd
import os
import firebase_admin
from firebase_admin import credentials, storage

# Initialize Firebase with the correct paths 
cred = credentials.Certificate('shoppeasauthentication-firebase-adminsdk-x6pk7-0e6ec030a9.json')
default_app = firebase_admin.initialize_app(cred, {'storageBucket': "shoppeasauthentication.appspot.com"})

# paths to the images and and products csv file 
image_folder = 'Images/'
csv_path = 'data/products.csv'

# Function to upload images to firebase storage and get the public url 
def upload_image_to_firebase(image_path, image_name):
    bucket = storage.bucket()  # Access the default bucket automatically
    blob = bucket.blob(f'images/{image_name}')  # Store images under 'images/' folder in Firebase Storage
    blob.upload_from_filename(image_path)
    blob.make_public()  # Make the image publicly accessible
    return blob.public_url  # Return the public URL of the image

# Convert the csv file to a pandas dataframe 
products_df = pd.read_csv(csv_path)

# **Ensure the Image column is of string type**
if 'Image' not in products_df.columns:
    products_df['Image'] = ""  # Create the Image column if it does not exist

# Convert the 'Image' column to string type 
products_df['Image'] = products_df['Image'].astype(str)

# Loop through the dataframe and map the images 
for index, row in products_df.iterrows():
    pid = row['pid']  # Get the product ID
    image_name = f"{pid}.jpg"  # Assume the image name matches the product ID, e.g., 'P1001.jpg'
    image_path = os.path.join(image_folder, image_name)  # Construct the full path to the image

    if os.path.exists(image_path):
        # If the image exists, upload it to Firebase and get the URL
        image_url = upload_image_to_firebase(image_path, image_name)
        
        # Update the 'Image' column in the DataFrame with the image URL
        products_df.at[index, 'Image'] = image_url
        print(f"Successfully uploaded {image_name} and updated URL: {image_url}")
    else:
        print(f"Image {image_name} not found. Skipping...")

products_df.to_csv(csv_path, index=False)
print(f"Successfully updated {csv_path} with image URLs.")