import requests
import os
      
url = "https://www.onemap.gov.sg/api/auth/post/getToken"
      
payload = {
        "email": 'racheltanaiml@gmail.com',
        "password": 'puU6XQTZq@D2kKN'
      }
      
response = requests.request("POST", url, json=payload)
      
print(response.text)