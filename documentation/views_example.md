# CountingTableView

Suppose we want to initialize a CountingTableView, a view used to monitor the denominations on the table area of the application.

```java
// Creates a new variable.
CountingTableView countingtableview;

// Match the view to the layout area of the xml file of the view. The name_of_view is the id for this view.
countingTableView = view.findViewById(R.id.<name_of_view>);

// Creates a currency model for the denominations to be initialized to. In this case, the model is Pakistani Rupees, PKR.
CurrencyModel currency = CurrencyModel.loadCurrencyModel("PKR", getResources(), getContext());

// The default app state of the application where the counting table should be initialized.
AppStateModel appState = AppStateModel.getDefault();

countingTableView.initialize(currency, appState);

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
CurrencyScrollbarView currencyScrollbarView = view.findViewById(R.id.currency_scrollbar);

// The currency code. In this case, Pakistani Rupee was used.
String currencyName = "PKR";

// Sets the currency type to the specified currency. Additionally, the denominations for this currency is added to the view.
currencyScrollbarView.setCurrency(currencyName);

// This listener monitors all gestures received on this view.
currencyScrollbarView.setCurrencyScrollbarListener(new CurrencyScrollbarListener() {
        // Handles the currencyScrollBarView events.
}
```

The java snippet above will setup the CurrencyScrollbarView to display the denominations on this view.

# Adding denominations to the counting table.

Suppose we want to add a 50 dollar denomination of Pakistani currency manually to the Counting Table and the current value displayed is "PKR 450". Note that this means there are 4-hundred dollar denominations and a fifty dollar denomination present on the CountingTable:

```java
//Appservice assists in adding the denomination to the total value.
Appservice service;

// Creates a currency model for the denominations to be initialized to. In this case, the model is Pakistani Rupees, PKR.
CurrencyModel currency = CurrencyModel.loadCurrencyModel("PKR", getResources(), getContext());

// Fifty dollars is added to the CountingTableView, and the display is updated to PKR 500. The CountingTable will partition the 500 into a single five hundred dollar denomination instead of adding a fifty dollar denomination to the screen.
service.setValue(service.getValue().add(BigDecimal(50)));

//This function in CountingTableView will update the denomination on screen to suit the total value displayed.
countingTableView.setAppState(service.getAppState());
```
