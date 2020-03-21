import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Typography from "@material-ui/core/Typography";
import { FaArrowUp, FaArrowDown } from "react-icons/fa";
import { Grid } from "@material-ui/core";

const useStyles = makeStyles({
  root: {
    maxWidth: 345
  }
});

export default function CardList({
  country,
  currency,
  image,
  onArrowUp,
  onArrowDown
}) {
  const classes = useStyles();

  return (
    <div>
      <Card className={classes.root}>
        <CardActionArea>
          <CardMedia
            component="img"
            alt={country}
            height="150"
            image={require("./country_img/" + image)}
            title={country}
          />
          <CardContent>
            <Grid container>
              <Grid container item xs={8}>
                <Typography
                  gutterBottom
                  align="center"
                  variant="h5"
                  component="h2"
                >
                  {currency}
                </Typography>
              </Grid>
              <Grid container item xs={2}>
                <FaArrowUp onClick={onArrowUp} />
              </Grid>
              <Grid container item xs={2}>
                <FaArrowDown onClick={onArrowDown} />
              </Grid>
            </Grid>
          </CardContent>
        </CardActionArea>
      </Card>
    </div>
  );
}
