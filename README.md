# ShopPeas
SC2006 Software Engineering Project as a Smart Nation Initiative

# Note

Create virtual env in backend folder. Pip install libraries only after activating the virtual env.

    cd backend
    python -m venv env
    source ./env/bin/activate (mac)
    env\Scripts\activate (windows)

Run frontend, go to http://127.0.0.1/5000.

    python app.py

When you install a python library in the venv, update the requirements.txt file so someone else can update their version.

    pip freeze > requirements.txt (updated packages)

    pip install -r requirements.txt (install packages)