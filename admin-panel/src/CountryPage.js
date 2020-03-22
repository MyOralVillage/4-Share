import React from "react";
import CardList from "./CountryCardList.js";
import { makeStyles } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import arrayMove from "array-move";

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1
  },
  title: {
    flexGrow: 1
  },
  appBar: {
    backgroundColor: "indigo"
  }
}));

function FormRow({ countries, onArrowUp, onArrowDown }) {
  return countries.map((country, i) => (
    <Grid
      key={i.toString()}
      item
      xs={0}
      container
      spacing={0}
      justify="center"
      style={{ "padding-top": "50px" }}
    >
      <CardList
        country={country.name}
        currency={country.currency}
        code={country.code}
        onArrowUp={() => onArrowUp(country.currency)}
        onArrowDown={() => onArrowDown(country.currency)}
      />
    </Grid>
  ));
}

function FormInfo(props) {
  const classes = useStyles();

  const countries = props.countries;
  const order = props.order;

  const rows = order.map((currency, i) => (
    <Grid key={"row" + i.toString()} container item xs={0} spacing={0}>
      <FormRow
        countries={countries.filter(country => country.currency === currency)}
        onArrowUp={props.onArrowUp}
        onArrowDown={props.onArrowDown}
      />
    </Grid>
  ));

  return (
    <div className={classes.root}>
      <Container>
        <Grid container spacing={1}>
          {rows}
        </Grid>
      </Container>
    </div>
  );
}

function MainBar(props) {
  const classes = useStyles();
  const name =
    props.country.charAt(0).toUpperCase() +
    props.country.slice(1).toLowerCase();

  return (
    <div className={classes.root}>
      <AppBar position="center" className={classes.appBar}>
        <Toolbar>
          <Typography variant="h6" className={classes.title}>
            Cash Calculator Administration - {name}
          </Typography>
        </Toolbar>
      </AppBar>
    </div>
  );
}

function postJson(url, data) {
  return fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  });
}

class CountryPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      countries: props.countries,
      country: props.match.params.country,
      order: []
    };
  }

  async componentDidMount() {
    const response = await fetch(`/api/currencies/${this.state.country}`);
    const json = await response.json();
    const newOrder = json["currencies"];
    this.setState({
      order: newOrder,
      countries: this.state.countries,
      country: this.state.country
    });
  }

  render() {
    return (
      <div>
        <MainBar country={this.state.country} />
        <FormInfo
          country={this.state.country}
          countries={this.state.countries}
          order={this.state.order}
          onArrowUp={async currency => {
            const index = this.state.order.indexOf(currency);
            if (index > 0) {
              const updatedOrder = arrayMove(
                this.state.order,
                index,
                index - 1
              );
              await postJson(
                `/api/currencies/${this.state.country}`,
                updatedOrder
              );
              this.setState({ ...this.state, order: updatedOrder });
            }
          }}
          onArrowDown={async currency => {
            const index = this.state.order.indexOf(currency);
            if (index < this.state.order.length - 1) {
              const updatedOrder = arrayMove(
                this.state.order,
                index,
                index + 1
              );
              await postJson(
                `/api/currencies/${this.state.country}`,
                updatedOrder
              );
              this.setState({ ...this.state, order: updatedOrder });
            }
          }}
        />
      </div>
    );
  }
}

export default CountryPage;
