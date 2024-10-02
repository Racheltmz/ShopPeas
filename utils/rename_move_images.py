import os
import pandas as pd
import shutil

# Load the products.csv file which contains product information
product_info_df = pd.read_csv('./utils/data/products.csv')

# Define the paths 
unzipped_image_folder = './utils/unzipped_images/products/'  # Folder where your unzipped product folders are located
target_image_folder = './utils/Images/'

# Step 3: Iterate through each product in products.csv
for index, row in product_info_df.iterrows():
    # Construct the folder name using 'name' and 'package size'
    product_name_folder = f"{row['name']} {row['package_size']}"  # e.g., '3A 100% Pure Black Sesame Oil 320ml'
    
    # Define the folder path in the unzipped images directory
    product_folder_path = os.path.join(unzipped_image_folder, product_name_folder)

    # Check if the product folder exists
    if os.path.exists(product_folder_path):
        # Assuming the image inside each product folder is named 'image.jpg'
        # Adjust the image name if necessary (e.g., if it has a different file extension)
        current_image_path = os.path.join(product_folder_path, 'Image_1.jpg')  # Modify 'image.jpg' if needed
        new_image_name = f"{row['pid']}.jpg"  # Rename the image using the pid
        target_image_path = os.path.join(target_image_folder, new_image_name)

        # Check if the image file exists before moving
        if os.path.exists(current_image_path):
            # Move and rename the image
            shutil.move(current_image_path, target_image_path)
            print(f"Renamed and moved: {current_image_path} to {target_image_path}")
        else:
            print(f"Image not found for product: {product_name_folder}")
    else:
        print(f"Folder not found for product: {product_name_folder}")

print("Image renaming and moving process completed!")