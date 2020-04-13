import React from 'react';
import ReactDOM from 'react-dom';
import {
  BrowserRouter, Switch,
} from 'react-router-dom';

import Main from './layouts/Main';


const App = () => (
  <>
    <BrowserRouter basename="ui">
      <Switch>
        <Main />
      </Switch>
    </BrowserRouter>
  </>
);

export default App;

ReactDOM.render(<App />, document.getElementById('main'));
