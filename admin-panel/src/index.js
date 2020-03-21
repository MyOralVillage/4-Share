import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import CountryPage from "./CountryPage";
import * as serviceWorker from "./serviceWorker";
import { Route, Link, BrowserRouter as Router, Switch } from "react-router-dom";

const countries = [
  { country: "Canada", currency: "CAD", image: "Canada.png" },
  { country: "Bangladesh", currency: "BDT", image: "Bangladesh.png" },
  { country: "India", currency: "INR", image: "India.png" },
  { country: "Pakistan", currency: "PKR", image: "Pakistan.png" },
  { country: "Kenya", currency: "KES", image: "Kenya.png" },
  { country: "United States", currency: "USD", image: "United_States.png" }
];

const routing = (
  <Router>
    <Switch>
      <Route
        path="/country/:country"
        render={props => <CountryPage {...props} countries={countries} />}
      />
      <Route path="/" component={App} />
    </Switch>
  </Router>
);

ReactDOM.render(routing, document.getElementById("root"));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
