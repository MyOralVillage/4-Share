import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Typography from "@material-ui/core/Typography";
import { useHistory } from "react-router-dom";

const useStyles = makeStyles({
  root: {
    maxWidth: 345
  }
});

export default function CardList(props) {
  const classes = useStyles();

  const history = useHistory();
  return (
    <div
      onClick={() => history.push("/country/" + props.country.toLowerCase())}
    >
      <Card className={classes.root}>
        <CardActionArea>
          <CardMedia
            component="img"
            alt={props.country}
            height="150"
            image={require(`../node_modules/svg-country-flags/svg/${props.code.toLowerCase()}.svg`)}
            title={props.country}
          />
          <CardContent>
            <Typography gutterBottom align="left" variant="h5" component="h2">
              {props.country}
            </Typography>
            <Typography
              variant="body2"
              align="left"
              color="textSecondary"
              component="p"
            >
              Default: {props.currency} currency
            </Typography>
          </CardContent>
        </CardActionArea>
      </Card>
    </div>
  );
}
