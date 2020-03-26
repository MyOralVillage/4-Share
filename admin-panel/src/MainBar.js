import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";

class MainBar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      msg: props.msg
    };
  }

  render() {
    return (
      <div style={{ flexGrow: 1 }}>
        <AppBar position="center" style={{ backgroundColor: "indigo" }}>
          <Toolbar>
            <Typography variant="h6" style={{ flexGrow: 1 }}>
              Cash Calculator Administration{this.state.msg}
            </Typography>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}

export default MainBar;
