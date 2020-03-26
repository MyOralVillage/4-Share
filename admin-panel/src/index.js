import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import MainBar from "./MainBar";
import CountryPage from "./CountryPage";
import MainPage from "./MainPage";
import * as serviceWorker from "./serviceWorker";
import { Route, BrowserRouter as Router, Switch } from "react-router-dom";

fetch("/api/countries")
  .then(res => res.json())
  .then(countries => {
    const routing = (
      <Router>
        <div className="index">
          <Switch>
            <Route
              path="/country/:code"
              exact
              render={props => <CountryPage {...props} countries={countries} />}
            />
            <Route
              path="/"
              exact
              render={props => <MainPage {...props} countries={countries} />}
            />
            <Route render={props => <MainBar msg={" - Not found"} />} />
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
