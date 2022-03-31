import {
  Paper,
  List,
  Container,
  AppBar,
  Toolbar,
  Button,
  Grid,
  Typography,
} from "@material-ui/core";
import { Component } from "react";
import AddTodo from "./AddTodo";
import "./App.css";
import Todo from "./Todo";
// import { call, signout } from "./service/ApiService";
import { call } from "./service/ApiService";
import axios from "axios";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      items: [],
    };
  }

  componentDidMount() {
    call("/todo", "GET", null).then((response) => {
      return this.setState({ items: response.data });
    });
  }

  request = async () => {
    console.log("request..");
    let ret = await axios
      .get("http://localhost:8080/todo")
      .then((res) => console.log(res));
    console.log(ret);
    return ret;
  };

  add = (item) => {
    console.log("item:", item);
    call("/todo", "POST", item).then((response) => {
      console.log("add resp:", response);
      this.setState({ items: response.data });
    });
  };
  delete = (item) => {
    console.log("delete:", item);
    call("/todo", "DELETE", item).then((response) => {
      console.log("after items:", response.data);
      this.setState({ items: response.data });
    });
  };
  update = (item) => {
    call("/todo", "PUT", item).then((response) => {
      this.setState({ items: response.data });
    });
  };
  render() {
    const todoItems = this.state.items.length > 0 && (
      <Paper style={{ margin: 16 }}>
        <List>
          {this.state.items.map((item, index) => (
            <Todo
              item={item}
              key={item.todoId}
              delete={this.delete}
              update={this.update}
            />
          ))}
        </List>
      </Paper>
    );
    const navigationBar = (
      <AppBar position="static">
        <Toolbar>
          <Grid justifyContent="space-between" container>
            <Grid item>
              <Typography variant="h6">오늘의 할일</Typography>
            </Grid>
          </Grid>
        </Toolbar>
      </AppBar>
    );
    const todoListPage = (
      <div>
        {navigationBar}
        <Container maxWidth="md">
          <AddTodo add={this.add} />
          <div className="TodoList">{todoItems}</div>
          <Button onClick={this.request}>Axios Request</Button>
        </Container>
      </div>
    );

    return <div className="App">{todoListPage}</div>;
  }
}

export default App;
