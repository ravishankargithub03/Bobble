import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const Contacts = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { contacts = [], number: myId = '' } = location.state || {};
  const [inputMemberId, setInputMemberId] = useState('');
  const [inputMemberNumber, setInputMemberNumber] = useState('');
  const [localContacts, setLocalContacts] = useState(contacts);

  const handleAddNumber = async () => {
    if (!inputMemberNumber.trim()) {
      alert('Please enter a valid memberNumber');
      return;
    }

    try {
      // Avoid adding the current user (myId)
      if (inputMemberNumber === myId) {
        alert('You cannot add yourself as a contact.');
        return;
      }

      const payload = {
        myId,
        memberNumber: inputMemberNumber,
      };

      const res = await axios.post('/api/contact/save', payload);

      alert('Contact added successfully!');

      // Refresh contacts after adding
      const refreshed = await axios.get(`/api/contact/get/all/${myId}`);
      setLocalContacts(refreshed.data.responsePayload || []);
      setInputMemberNumber(''); // Clear input field after adding
    } catch (err) {
      console.error('Error adding contact:', err);
      alert('Failed to add contact.');
    }
  };

  const handleContactClick = async (contact) => {
    try {
      const res = await axios.get(`/api/message/onetoone/get/all/${myId}/${contact.memberNumber}`);
      const messages = res.data.responsePayload || [];

      navigate('/messages', {
        state: {
          messages,
          groupName: contact.memberName || 'Unknown',
          groupCode: contact.memberNumber || '',
          groupId: contact.memberNumber,
          number: myId,
        },
      });
    } catch (err) {
      console.error('Failed to load messages:', err);
      alert('Failed to load messages');
    }
  };

  return (
    <div
      style={{
        minHeight: '100vh',
        backgroundColor: '#121212',
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
          padding: '2rem',
          borderRadius: '8px',
          backgroundColor: '#fff',
          overflowY: 'auto',
          fontFamily: 'Arial, sans-serif',
          boxSizing: 'border-box',
          color: '#000',
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
          <h2 style={{ margin: 0 }}>Contacts</h2>
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

        {/* Input & Add */}
        <div style={{ display: 'flex', marginBottom: '1rem', gap: '0.5rem' }}>
          <input
            type="text"
            placeholder="Enter Number"
            value={inputMemberNumber}
            onChange={(e) => setInputMemberNumber(e.target.value)}
            style={{
              flex: 1,
              padding: '0.5rem',
              borderRadius: '4px',
              border: '1px solid #ccc',
              fontSize: '1rem',
            }}
          />
          <button
            onClick={handleAddNumber}
            style={{
              padding: '0.5rem 1rem',
              fontSize: '0.9rem',
              backgroundColor: '#007bff',
              color: '#fff',
              border: 'none',
              borderRadius: '4px',
              cursor: 'pointer',
            }}
          >
            Add
          </button>
        </div>

        {/* Contact List */}
        <div style={{ flexGrow: 1, overflowY: 'auto' }}>
          {localContacts.length === 0 ? (
            <div
              style={{
                height: '90%',
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
              No contacts found !
            </div>
          ) : (
            localContacts.map((contact) => (
              <div
                key={contact.id || contact.memberNumber}
                onClick={() => handleContactClick(contact)}
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  gap: '0.75rem',
                  padding: '0.5rem 1rem',
                  border: '1px solid #ddd',
                  borderRadius: '8px',
                  marginBottom: '0.5rem',
                  backgroundColor: '#f9f9f9',
                  boxShadow: '0 1px 3px rgba(0,0,0,0.05)',
                  color: '#000',
                  cursor: 'pointer',
                  userSelect: 'none',
                }}
                onMouseEnter={(e) => (e.currentTarget.style.backgroundColor = '#f1f1f1')}
                onMouseLeave={(e) => (e.currentTarget.style.backgroundColor = '#f9f9f9')}
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
                  }}
                >
                  {contact.memberName?.charAt(0).toUpperCase() || '?'}
                </div>

                <div>
                  <div style={{ fontWeight: 'bold', fontSize: '1rem' }}>
                    {contact.memberName}
                  </div>
                  <div style={{ color: '#666', fontSize: '0.85rem' }}>
                    {contact.memberNumber}
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
};

export default Contacts;
