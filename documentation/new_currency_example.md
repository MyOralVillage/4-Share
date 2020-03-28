## Adding a new Currency

Suppose we want to add a new currency type, say Japanese Yen:

- (Optional) Search for the country code from [this website](https://www.currency-iso.org/dam/downloads/lists/list_one.xml). This is to ensure that the symbol presented in the application matches the new added currency. In this case, the currency code is JPY.

# Admin Panel

- Add an entry for the currency, Japanese Yen, to the database table. Then add it to each relevant country in the countries table's currencies column. This can be added through the init.sql or through an sql client:

```sql
/* Add the entry to the currencies table.*/
INSERT INTO currencies VALUES ('JPY');

/* Add the country associated with the currency if the country has not been added.*/
INSERT INTO countries VALUES ('JP', 'japan', 'JPY', 'JPY');
```

# Mobile Application

- Add an image to denote the currency type to the drawable folder. Name the image country_code_in_lowercase.png. In this case, the image will be named jpy.png.

- In the currency.xml file at location ./android-app/cashcalculatormodule/src/main/res/values, add a new array with the Japanese Yen images:

```xml
<array name="currency_JPY">
    <item>10000</item>
    <item>@drawable/currency_jpy_10000</item>
    <item>@drawable/currency_jpy_10000</item>
    <item>0.6</item>
    <item>0.0</item>

    <item>5000</item>
    <item>@drawable/currency_jpy_5000</item>
    <item>@drawable/currency_jpy_5000</item>
    <item>0.6</item>
    <item>0.0</item>

    <item>2000</item>
    <item>@drawable/currency_jpy_2000</item>
    <item>@drawable/currency_jpy_2000</item>
    <item>0.6</item>
    <item>0.0</item>

    <item>1000</item>
    <item>@drawable/currency_jpy_1000</item>
    <item>@drawable/currency_jpy_1000_folded</item>
    <item>1.0</item>
    <item>0.012</item>

    <item>500</item>
    <item>@drawable/currency_jpy_500</item>
    <item>@drawable/currency_jpy_500_folded</item>
    <item>1.0</item>
    <item>0.024</item>

    <item>100</item>
    <item>@drawable/currency_jpy_100</item>
    <item>@drawable/currency_jpy_100_folded</item>
    <item>1.0</item>
    <item>0.048</item>

    <item>50</item>
    <item>@drawable/currency_jpy_50</item>
    <item>@drawable/currency_jpy_50_folded</item>
    <item>1.0</item>
    <item>0.06</item>

    <item>10</item>
    <item>@drawable/currency_jpy_10</item>
    <item>@drawable/currency_jpy_10</item>
    <item>0.65</item>
    <item>0.0</item>

    <item>5</item>
    <item>@drawable/currency_jpy_5</item>
    <item>@drawable/currency_jpy_5</item>
    <item>0.55</item>
    <item>0.0</item>

    <item>1</item>
    <item>@drawable/currency_jpy_1</item>
    <item>@drawable/currency_jpy_1</item>
    <item>0.85</item>
    <item>0.0</item>
</array>
```

    Note that in the array above, the name is equal to the "currency_" + currency code(JPY).
    - Every (i-1) + 1 element, where i = 1,2,...,n and n is the number of denomination, is the value of the denomination being added.
    - Every (i-1) + 2 element, where i = 1,2,...,n and n is the number of denomination, is the location of the image of the denomination being added that appears in the CurrencyScrollBar. These images must be added manually to the drawable folder.
    - Every (i-1) + 3 element, where i = 1,2,...,n and n is the number of denomination, is the location of the image of the denomination being added that appears in the CountingTable. These images must be added manually to the drawable folder.
    - Every (i-1) + 4 element, where i = 1,2,...,n and n is the number of denomination, is the scale factor of the denomination being added to adjust the size of the image on screen in the CountingTable.
    - Every (i-1) + 5 element, where i = 1,2,...,n and n is the number of denomination, is the offset in inches of the denomination being added to adjust the format of the image in the CurrencyScrollBar.

- (Optional) Search for the 2 letter country code from [this website](https://www.iso.org/obp/ui/#search). This is to ensure that the 2 letter country code presented in the application matches the new added currency. In this case, the 2 letter country code is JP.

- (Optional) Add the 3 letter currency code and 2 letter country code to the list of items in the array located in countries.xml, in case this country was not updated in the admin panel. Place the items in the specific location that you wish for this new currency to be ordered as the default currency order:

```xml
<array name="default_country_order">
    //Higher preference currency codes.

    <item> JPY </item>
    <item> JP </item>

    //Lower preference currency codes.
</array>
```

The currency will now be added so if the country is chosen in the settings is Japan, or the device's language is set to Japanese, then the following will be initialized on screen. The preference in getting a currency order for a specified currency code in the model is:

1. Attempt to connect to the endpoint of the admin panel to read the currency for a country.
2. If preference 1 fails, then check to see if there is an order stored in the database file, db.json.
3. If all above fails, use the default currrency order specified in the CurrencyService.java.
