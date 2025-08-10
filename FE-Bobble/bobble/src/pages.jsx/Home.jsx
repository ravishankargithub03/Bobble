import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const Home = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { results = [], number = '' } = location.state || {};

  const handleChatClick = async (item) => {
    const {
      groupId,
      groupName,
      groupCode,
      isGroup,
    } = item;

    try {
      let res;
      let messages = [];

      if (isGroup) {
        // Group chat
        res = await axios.get(`/api/message/group/get/all/${number}/${groupId}`);
      } else {
        // One-to-one chat
        res = await axios.get(`/api/message/onetoone/get/all/${number}/${groupId}`);
      }

      if (res.data && res.data.responsePayload) {
        messages = res.data.responsePayload;

        navigate('/messages', {
          state: {
            messages,
            isGroup,
            groupId,
            groupName,
            groupCode: isGroup ? groupCode : null,
            contactNumber: isGroup ? null : groupId,
            contactName: isGroup ? null : groupName,
            number,
          },
        });
      } else {
        alert('Failed to fetch messages');
      }
    } catch (error) {
      console.error('Message fetch error:', error);
      alert('Failed to fetch messages');
    }
  };

  const handleRequestClick = async () => {
    try {
      const res = await axios.get(`/api/contact/request/get/all/${number}`);
      const requests = res.data.responsePayload || [];
      navigate('/requests', { state: { requests, number } });
    } catch (err) {
      alert('Failed to fetch requests');
    }
  };

  const handleContactsClick = async () => {
    try {
      const res = await axios.get(`/api/contact/get/all/${number}`);
      const contacts = res.data.responsePayload || [];
      navigate('/contacts', { state: { contacts, number, results } });
    } catch (err) {
      alert('Failed to fetch contacts');
    }
  };

  return (
    <div
      style={{
        backgroundColor: '#121212',
        minHeight: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        padding: '2rem 0',
        boxSizing: 'border-box',
        margin: -8,
      }}
    >
      <div
        style={{
          height: '660px',
          borderRadius: '8px',
          maxWidth: '650px',
          width: '100%',
          padding: '2rem',
          boxSizing: 'border-box',
          fontFamily: 'Arial, sans-serif',
          overflowY: 'auto',
          backgroundColor: '#fff',
          position: 'relative',
          color: '#000',
        }}
      >
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
          <h2 style={{ margin: 0 }}>Bobble</h2>

          <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
            <button
              onClick={handleRequestClick}
              title="View Requests"
              style={{
                background: 'none',
                border: 'none',
                cursor: 'pointer',
                padding: '6px',
                fontSize: '20px',
                color: '#007bff',
                userSelect: 'none',
              }}
              aria-label="View requests"
            >
              ⊕
            </button>

            <button
              onClick={() => navigate('/')}
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
        </div>

        {/* Chat List (Groups and Contacts) */}
        {results.length === 0 ? (
          <p>No chats found.</p>
        ) : (
          results.map((item) => (
            <div
              key={item.groupId}
              onClick={() => handleChatClick(item)}
              style={{
                display: 'flex',
                alignItems: 'center',
                gap: '0.75rem',
                padding: '0.3rem 0.75rem',
                border: '1px solid #ddd',
                borderRadius: '8px',
                marginBottom: '0.5rem',
                cursor: 'pointer',
                backgroundColor: '#f9f9f9',
                boxShadow: '0 1px 3px rgba(0,0,0,0.05)',
                transition: 'background-color 0.3s',
                color: '#000',
              }}
              onMouseEnter={(e) =>
                (e.currentTarget.style.backgroundColor = '#f1f1f1')
              }
              onMouseLeave={(e) =>
                (e.currentTarget.style.backgroundColor = '#f9f9f9')
              }
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
                  userSelect: 'none',
                }}
              >
                {item.groupName?.charAt(0).toUpperCase() || 'U'}
              </div>

              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  gap: '0.4rem',
                  flexWrap: 'wrap',
                }}
              >
                <div style={{ fontSize: '0.9rem', fontWeight: 'bold' }}>
                  {item.groupName}
                </div>

                {/* Only show groupCode if it's a group and present */}
                {item.isGroup && item.groupCode && (
                  <div style={{ fontSize: '0.75rem', color: '#666' }}>
                    [{item.groupCode}]
                  </div>
                )}
              </div>
            </div>
          ))
        )}

        {/* Floating contacts button */}
        <button
          onClick={handleContactsClick}
          title="View Contacts"
          style={{
            position: 'absolute',
            bottom: '20px',
            right: '20px',
            width: '50px',
            height: '50px',
            borderRadius: '50%',
            color: 'white',
            border: 'none',
            fontSize: '24px',
            cursor: 'pointer',
            backgroundColor: 'lightgray',
          }}
          aria-label="View contacts"
        >
          👤
        </button>
      </div>
    </div>
  );
};

export default Home;
