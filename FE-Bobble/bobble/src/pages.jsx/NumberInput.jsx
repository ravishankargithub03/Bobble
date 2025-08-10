import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const NumberInput = () => {
  const [number, setNumber] = useState('');
  const navigate = useNavigate();

  const handleClick = async () => {
    if (!number) return alert('Please enter a number');

    try {
      const res = await axios.get(`/api/message/by/get/all/${number}`);
      const results = res.data.responsePayload || [];
      navigate('/home', { state: { results, number } });
    } catch (err) {
      console.error(err);
      alert('API call failed');
    }
  };

  return (
    <div
      style={{
        backgroundColor: '#121212', // dark background outside
        minHeight: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        padding: '2rem',
        boxSizing: 'border-box',
        margin: -8,
      }}
    >
      <div
        style={{
          height: '660px',
          maxWidth: '650px',
          width: '100%',
          margin: '0 auto',
          padding: '2rem',
          boxSizing: 'border-box',
          fontFamily: 'Arial, sans-serif',
          borderRadius: '8px',
          backgroundImage:
            'url("https://images.unsplash.com/photo-1657632843433-e6a8b7451ac6?q=80&w=2024&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")',
          backgroundSize: 'cover',
          backgroundPosition: 'center',
          backgroundRepeat: 'no-repeat',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          // border removed here
        }}
      >
        <div
          style={{
            backgroundColor: 'rgba(255, 255, 255, 0.6)',
            borderRadius: '6px',
            padding: '2rem',
            width: '100%',
            maxWidth: '400px',
            boxSizing: 'border-box',
            textAlign: 'center',
            boxShadow: '0 0 10px rgba(0,0,0,0.2)',
          }}
        >
          <h2>Enter Your Number</h2>
          <input
            inputMode="numeric"
            pattern="[0-9]*"
            value={number}
            onChange={(e) => setNumber(e.target.value)}
            placeholder="Enter your number"
            style={{
              padding: '0.75rem',
              width: '100%',
              border: '1px solid #ccc',
              borderRadius: '4px',
              outline: 'none',
              boxSizing: 'border-box',
              marginTop: '1rem',
            }}
          />
          <button
            onClick={handleClick}
            style={{
              marginTop: '1rem',
              padding: '0.75rem 1rem',
              cursor: 'pointer',
              backgroundColor: '#007bff',
              color: 'white',
              border: 'none',
              borderRadius: '4px',
              width: '100%',
            }}
          >
            Enter
          </button>
        </div>
      </div>
    </div>
  );
};

export default NumberInput;
