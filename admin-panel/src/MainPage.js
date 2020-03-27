import React from "react";
import CardList from "./CardList";
import MainBar from "./MainBar";
import { makeStyles } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import Container from "@material-ui/core/Container";

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1
  }
}));

function FormRow(props) {
  return props.countries.map((country, i) => (
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
        country={country.name}
        currency={country.def}
        code={country.code}
      />
    </Grid>
  ));
}

function FormInfo(props) {
  const classes = useStyles();

  const rows = props.countries.map((_, i) => (
    <Grid key={"row" + i.toString()} container item xs={12} spacing={3}>
      <FormRow countries={props.countries.slice(3 * i, 3 * i + 3)} />
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

class MainPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      countries: props.countries
    };
  }

  render() {
    return (
      <div>
        <MainBar msg={""} />
        <FormInfo countries={this.state.countries} />
      </div>
    );
  }
}

export default MainPage;
