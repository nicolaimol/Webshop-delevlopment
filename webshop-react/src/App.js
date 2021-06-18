import logo from './logo.svg';
import {BrowserRouter as Router, Route, Link, Switch} from 'react-router-dom'
import './App.css';

import Navbar from './components/Navbar'
import Footer from './components/Footer'
import Login from './components/Login'
import ProductList from './components/ProductList'
import Profile from './components/Profile'

function App() {
  return (
    <Router>
      <Switch>
        <div className="App">
          <Navbar />
            <Route path="/login">
              <Login />
            </Route>
            <Route path="/products">
              <ProductList />
            </Route>
            <Route path="/profile">
              <Profile />
            </Route>
          <Footer />
        </div>
      </Switch>
    </Router>
  );
}

export default App;
