'''
Web scrape images from HealthierChoiceSymbolProductListasofFeb2023.csv dataset
'''
# Import libraries
from bing_image_downloader import downloader
import pandas as pd
import concurrent.futures

# Get queries from dataset
df = pd.read_csv('./data/HealthierChoiceSymbolProductListasofFeb2023.csv')
queries = []
for i in range(len(df)):
    queries.append(df.iloc[i]['brand_and_product_name'] + ' ' + df.iloc[i]['package_size'])

# Download images while handling timeouts 
def download_image(query, timeout=60):
    try:
        # Use a future to handle the timeout
        with concurrent.futures.ThreadPoolExecutor(max_workers=1) as executor:
            future = executor.submit(downloader.download, query, limit=1, output_dir='downloaded_images', adult_filter_off=True, force_replace=False, timeout=timeout)
            # Wait for the result or timeout
            result = future.result(timeout=timeout)
    except concurrent.futures.TimeoutError:
        print(f"Timeout occurred for query: {query}")
    except Exception as e:
        print(f"An error occurred: {e}")

# Download images
for query in queries:
    download_image(query, timeout=10)
