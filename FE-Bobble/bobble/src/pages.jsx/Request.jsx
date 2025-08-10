import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

const Request = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const { requests: initialRequests = [], number = '' } = location.state || {};
  const [requests, setRequests] = useState(initialRequests);

  const updateRequestStatus = async (reqId, status) => {
    try {
      const response = await fetch(`/api/contact/request/status/${reqId}/${status}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error(`Failed to update status to ${status}`);
      }

      const action = status === 1 ? 'Accepted' : 'Rejected';
      alert(`${action} request ID: ${reqId}`);

      setRequests(prevRequests => prevRequests.filter(req => req.id !== reqId));
    } catch (error) {
      alert(`Error: ${error.message}`);
    }
  };

  const handleAccept = (reqId) => {
    updateRequestStatus(reqId, 1);
  };

  const handleReject = (reqId) => {
    updateRequestStatus(reqId, 5);
  };

  return (
    <div
      style={{
        minHeight: '100vh',
        backgroundColor: '#121212',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        padding: '1rem',
        boxSizing: 'border-box',
        margin: -8,
      }}
    >
      <div
        style={{
          height: '660px',
          border: '2px solid #ccc',
          borderRadius: '8px',
          maxWidth: '650px',
          width: '100%',
          padding: '2rem',
          boxSizing: 'border-box',
          fontFamily: 'Arial, sans-serif',
          backgroundColor: '#fff',
          boxShadow: '0 4px 12px rgba(0,0,0,0.3)',
          display: 'flex',
          flexDirection: 'column',
        }}
      >
        {/* Header */}
        <div
          style={{
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            marginBottom: '1rem',
            borderBottom: '1px solid #ccc',
            paddingBottom: '0.5rem',
          }}
        >
          <h2 style={{ margin: 0, color: '#333' }}>Requests</h2>
          <button
            onClick={() => navigate(-1)}
            style={{
              padding: '0.4rem 0.8rem',
              fontSize: '0.85rem',
              backgroundColor: '#f44336',
              color: 'white',
              border: 'none',
              borderRadius: '4px',
              cursor: 'pointer',
            }}
          >
            ← Back
          </button>
        </div>

        {requests.length === 0 ? (
          <div
            style={{
              flexGrow: 1,
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              color: '#777',
              fontSize: '1.2rem',
              fontWeight: '600',
              fontStyle: 'italic',
              userSelect: 'none',
              textAlign: 'center',
              padding: '1rem',
            }}
          >
            No requests found !
          </div>
        ) : (
          <div
            style={{
              overflowY: 'auto',
              flexGrow: 1,
              paddingRight: '0.5rem',
            }}
          >
            {requests.map((req) => (
              <div
                key={req.id}
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'space-between',
                  gap: '0.75rem',
                  padding: '0.3rem 0.75rem',
                  border: '1px solid #ddd',
                  borderRadius: '8px',
                  marginBottom: '0.5rem',
                  backgroundColor: '#f9f9f9',
                  boxShadow: '0 1px 3px rgba(0,0,0,0.05)',
                  transition: 'background-color 0.3s',
                  userSelect: 'none',
                }}
                onMouseEnter={e => (e.currentTarget.style.backgroundColor = '#f1f1f1')}
                onMouseLeave={e => (e.currentTarget.style.backgroundColor = '#f9f9f9')}
              >
                <div
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    gap: '0.75rem',
                    flexGrow: 1,
                  }}
                >
                  <div
                    style={{
                      width: '40px',
                      height: '40px',
                      borderRadius: '50%',
                      backgroundColor: '#007bff',
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      color: 'white',
                      fontSize: '1rem',
                      fontWeight: 'bold',
                      flexShrink: 0,
                    }}
                  >
                    {req.memberName?.charAt(0).toUpperCase()}
                  </div>

                  <div style={{ display: 'flex', flexDirection: 'column' }}>
                    <div style={{ fontSize: '0.9rem', fontWeight: 'bold', color: '#333' }}>
                      {req.memberName}
                    </div>
                    <div style={{ fontSize: '0.75rem', color: '#666' }}>{req.memberNumber}</div>
                  </div>
                </div>

                <div style={{ display: 'flex', gap: '0.5rem' }}>
                  <button
                    onClick={() => handleAccept(req.id)}
                    style={{
                      padding: '0.3rem 0.7rem',
                      backgroundColor: '#4CAF50',
                      color: 'white',
                      border: 'none',
                      borderRadius: '4px',
                      cursor: 'pointer',
                      fontSize: '0.8rem',
                    }}
                  >
                    Accept
                  </button>
                  <button
                    onClick={() => handleReject(req.id)}
                    style={{
                      padding: '0.3rem 0.7rem',
                      backgroundColor: '#f44336',
                      color: 'white',
                      border: 'none',
                      borderRadius: '4px',
                      cursor: 'pointer',
                      fontSize: '0.8rem',
                    }}
                  >
                    Reject
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default Request;
