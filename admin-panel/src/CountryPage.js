import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import arrayMove from "array-move";
import { DragDropContext, Droppable, Draggable } from "react-beautiful-dnd";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1
  },
  drop: {
    flexGrow: 1,
    display: "inline-block"
  },
  card: {
    display: "flex",
    width: 350,
    "margin-bottom": "25px",
    padding: 15
  },
  details: {
    display: "flex",
    flexDirection: "column"
  },
  cover: {
    width: 150,
    height: 100
  },
  content: {
    flex: "1 0 auto"
  },
  title: {
    flexGrow: 1
  },
  appBar: {
    backgroundColor: "indigo"
  }
}));

const getListStyle = isDraggingOver => ({
  background: isDraggingOver ? "lightgrey" : "white",
  padding: 8,
  width: 250
});

function FormInfo(props) {
  const classes = useStyles();

  const countries = props.countries;
  const order = props.order;
  const names = require("../node_modules/currency-format/currency-format.json");

  const rows = order.map((currency, i) => (
    <Draggable key={currency} draggableId={currency} index={i}>
      {(provided, snapshot) => {
        const country = countries.filter(
          country => country.currency === currency
        )[0];
        return (
          <Card
            className={classes.card}
            ref={provided.innerRef}
            {...provided.draggableProps}
            {...provided.dragHandleProps}
          >
            <CardMedia
              className={classes.cover}
              image={require(`../node_modules/svg-country-flags/svg/${country.code.toLowerCase()}.svg`)}
              title={country.name}
            />
            <div className={classes.details}>
              <CardContent className={classes.content}>
                <Typography component="h6" variant="h6" align="left">
                  {names[currency].name}
                </Typography>
                <Typography variant="subtitle1" align="left">
                  {currency}
                </Typography>
              </CardContent>
            </div>
          </Card>
        );
      }}
    </Draggable>
  ));

  return (
    <div className={classes.drop}>
      <DragDropContext onDragEnd={props.onDragEnd}>
        <Droppable droppableId="droppable">
          {(provided, snapshot) => (
            <div
              {...provided.droppableProps}
              ref={provided.innerRef}
              style={getListStyle(snapshot.isDraggingOver)}
            >
              {rows}
              {provided.placeholder}
            </div>
          )}
        </Droppable>
      </DragDropContext>
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
          onDragEnd={async result => {
            if (!result.destination) {
              return;
            }

            const updatedOrder = arrayMove(
              this.state.order,
              result.source.index,
              result.destination.index
            );
            await postJson(
              `/api/currencies/${this.state.country}`,
              updatedOrder
            );

            this.setState({ ...this.state, order: updatedOrder });
          }}
        />
      </div>
    );
  }
}

export default CountryPage;
