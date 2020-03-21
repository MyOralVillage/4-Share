# Cash Calculator Fragment

Suppose we want to initialize a CashCalculatorFragment, the fragment of the main application.

```java
// Creates the CashCalculator fragment and initialize it to the fragment identified by the id "CountingTableFragment" when inflated from the XML.
CashCalculatorFragment fragment = (CashCalculatorFragment) getSupportFragmentManager().findFragmentById(R.id.CountingTableFragment);

if (fragment != null){
	//Initializes the fragment, setting the denominations to PakistanRupees, PKR
    fragment.initialize("PKR");
}
```

The java snippet above will setup the fragment so that you can create the Cash Calculator application.
