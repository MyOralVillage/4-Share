# CountingTableView

Suppose we want to initialize a CountingTableView, a view used to monitor the denominations on the table area of the application.
```java
// Creates a new variable. 
CountingTableView countingtableview;

// Match the view to the layout area of the xml file of the view. The name_of_view is the id for this view. 
countingTableView = view.findViewById(R.id.<name_of_view>);

// This initializes the all the denominations for the table.
countingTableView.initialize(currency_of_denomination, mode_of_the_application);

// This listener monitors all the gestures received on this view.
countingTableView.setListener(new CountingTableListener() {
                    // Handles the countingTableView events.
}
```
The java snippet above will setup the CountingTableView so that you can handle any events that the user performs within the counting table, along with initializing its state and currency.


# CurrencyScrollbarView

Suppose we want to initialize a CurrencyScrollbarView, a view used to create an area where all the possible unique denominations for a currency is displayed.

```java
// Creates a new currencyScrollbarView. Upon initialization, the view layout is created.
currencyScrollbarView = view.findViewById(R.id.currency_scrollbar);

// Sets the currency type to the specified currency. Additionally, the denominations for this currency is added to the view.
String currencyName = "PKR"; // Pakistani Rupee
currencyScrollbarView.setCurrency(currencyName);

// This listener monitors all gestures received on this view.
currencyScrollbarView.setCurrencyScrollbarListener(new CurrencyScrollbarListener() {
        // Handles the currencyScrollBarView events.
}
```

The java snippet above will setup the CurrencyScrollbarView to display the denominations on this view.
