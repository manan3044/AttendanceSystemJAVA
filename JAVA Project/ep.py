from flask import Flask, jsonify
from flask_restful import Api, Resource
import mysql.connector
import json
sql_pass = "manan@$3044"

try:
    mydatabase = mysql.connector.connect(
        host="localhost",
        user="root",
        password=sql_pass,
        database="AttendanceManagement"
    )

except:
    mydatabase = mysql.connector.connect(
        host="localhost",
        user="root",
        password=sql_pass
    )

# Cursor Initiation
mycursor = mydatabase.cursor()

# Database Creation
mycursor.execute("CREATE DATABASE IF NOT EXISTS AttendanceManagement")
mycursor.execute("USE AttendanceManagement")

mycursor.execute(f"CREATE TABLE IF NOT EXISTS USERDATA (Username VARCHAR(255), Password VARCHAR(255), Category VARCHAR(20))")

mycursor.execute(f"SELECT * FROM USERDATA;")
result = mycursor.fetchall()
# print(result)
columns = [desc[0] for desc in mycursor.description]
json_data = []
for row in result:
    json_data.append(dict(zip(columns, row)))

# Print JSON data
print(json.dumps(json_data, indent=4))

app = Flask(__name__)
api = Api(app)

from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/api/getUserData', methods=['GET'])
def get_user_data():
    # Mock user data
    # user_data = json.dumps(json_data, indent=4)
    return jsonify(json_data)
    # return user_data

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)

# RUN COMMAND = python ep.py