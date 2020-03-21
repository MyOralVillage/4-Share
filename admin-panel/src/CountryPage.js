import React from "react";
import CardList from "./CardList";
import { makeStyles } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";

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

function FormRow(props) {
  const items = [];
  for (var i = 0; i < props.countries.length; i++) {
    items.push(
      <Grid
        key={i.toString()}
        item
        xs={4}
        container
        spacing={0}
        direction="column"
        justify="center"
        style={{ minHeight: "50vh" }}
      >
        <CardList
          country={props.countries[i].country}
          currency={props.countries[i].currency}
          image={props.countries[i].image}
        />
      </Grid>
    );
  }

  return <React.Fragment>{items}</React.Fragment>;
}

async function FormInfo(props) {
  const classes = useStyles();

  const country = props.country;
  const countries = props.countries;
  const response = await fetch(
    `https://localhost:5000/api/currencies/${country}`
  );
  const json = await response.json();
  const order = json["currencies"];

  const rows = [];
  for (let i = 0; i < order.length; i++) {
    let current_country = [];
    for (let j = 0; j < countries.length; j++) {
      if (countries[j].currency === order[i]) {
        current_country.push(countries[j]);
        break;
      }
    }
    rows.push(
      <Grid key={"row" + i.toString()} container item xs={12} spacing={3}>
        <FormRow countries={current_country} />
      </Grid>
    );
  }

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

  return (
    <div className={classes.root}>
      <AppBar position="static" className={classes.appBar}>
        <Toolbar>
          <Typography variant="h6" className={classes.title}>
            Cash Calculator Administration - props.country
          </Typography>
        </Toolbar>
      </AppBar>
    </div>
  );
}

class CountryPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      countries: props.countries,
      country: props.match.params.country
    };
  }

  render() {
    return (
      <div>
        <MainBar country={this.state.country} />
        <FormInfo
          country={this.state.country}
          countries={this.state.countries}
        />
      </div>
    );
  }
}

export default CountryPage;
