import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles({
  root: {
    maxWidth: 345
  }
});

export default function CardList(props) {
  const classes = useStyles();

  return (
    <div onClick={() => alert(props.country.toLowerCase())}>
      <Card className={classes.root}>
        <CardActionArea>
          <CardMedia
            component="img"
            alt={props.country}
            height="150"
            image={require("./country_img/" + props.image)}
            title={props.country}
          />
          <CardContent>
            <Typography gutterBottom align="center" variant="h5" component="h2">
              {props.currency}
            </Typography>
          </CardContent>
        </CardActionArea>
      </Card>
    </div>
  );
}
