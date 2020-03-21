import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import CountryPage from "./CountryPage";
import MainPage from "./MainPage";
import * as serviceWorker from "./serviceWorker";
import { Route, Link, BrowserRouter as Router, Switch } from "react-router-dom";

fetch("/api/countries")
  .then(res => res.json())
  .then(countries => {
    const routing = (
      <Router>
        <div className="index">
          <Switch>
            <Route
              path="/country/:country"
              render={props => <CountryPage {...props} countries={countries} />}
            />
            <Route
              path="/"
              render={props => <MainPage {...props} countries={countries} />}
            />
          </Switch>
        </div>
      </Router>
    );

    ReactDOM.render(routing, document.getElementById("root"));
  });

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
