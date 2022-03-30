import { Box, Typography } from "@material-ui/core";
import { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
// import Login from "./Login";
import App from "./App";
// import SignUp from "./SignUp";

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      Todo App
    </Typography>
  );
}

class AppRouter extends Component {
  render() {
    return (
      <div>
        <Router>
          <div>
            <Switch>
              {/* <Route path="/login">
                <Login />
              </Route>
              <Route path="/signup">
                <SignUp />
              </Route> */}
              <Route path="/">
                <App />
              </Route>
            </Switch>
          </div>
          <Box mt={5}>
            <Copyright />
          </Box>
        </Router>
      </div>
    );
  }
}

export default AppRouter;
