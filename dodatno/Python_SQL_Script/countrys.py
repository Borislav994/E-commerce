import requests
import json

# API endpoint to get the list of countries
url = "https://restcountries.com/v2/all"

# Send a GET request to the API
response = requests.get(url)

# Get the JSON data from the API response
countries = json.loads(response.text)

# Open a file to write the INSERT statements
with open("insert_countries.sql", "w") as file:
    for country in countries:
        code = country["alpha2Code"]
        name = country["name"]
        id = country["numericCode"]
        # Write the INSERT statement to the file
        file.write(f"INSERT INTO country (id, code, name) VALUES ('{id}','{code}','{name}');\n")

# Close the file
file.close()
print("Insert statements generated successfully.")