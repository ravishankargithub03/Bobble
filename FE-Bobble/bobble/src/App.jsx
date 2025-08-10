// src/App.jsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NumberInput from './pages.jsx/NumberInput';
import Home from './pages.jsx/Home';
import Message from './pages.jsx/MessagePage';
import Request from './pages.jsx/Request';
import Contacts from './pages.jsx/Contacts';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<NumberInput />} />
        <Route path="/home" element={<Home />} />
        <Route path="/messages" element={<Message />} />
        <Route path="/requests" element={<Request />} />
        <Route path="/contacts" element={<Contacts />} />
      </Routes>
    </Router>
  );
};

export default App;
