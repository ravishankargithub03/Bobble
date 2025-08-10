import React, { useState, useEffect, useRef } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import EmojiPicker from 'emoji-picker-react';
import axios from 'axios';

const Messages = ({ onMessagesUpdate }) => {
  const location = useLocation();
  const navigate = useNavigate();

  const {
    messages = [],
    groupName = '',
    groupCode = '',
    groupId = 0,
    number = '',
    isGroup = false,
  } = location.state || {};

  const [inputValue, setInputValue] = useState('');
  const [showEmojiPicker, setShowEmojiPicker] = useState(false);

  const scrollContainerRef = useRef(null);

  // Scroll to bottom on mount and when messages change
  useEffect(() => {
    if (scrollContainerRef.current) {
      scrollContainerRef.current.scrollTop = scrollContainerRef.current.scrollHeight;
    }
  }, [messages]);

  const handleSend = async () => {
    if (inputValue.trim() === '') return;

    try {
      const response = await fetch('/api/message/save', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          messageId: 0,
          senderId: Number(number),
          receiverId: isGroup ? [Number(groupId)] : [Number(groupId)],
          content: inputValue,
          isGroup,
        }),
      });

      if (!response.ok) {
        const error = await response.text();
        throw new Error(`API Error: ${error}`);
      }

      setInputValue('');

      // Fetch updated messages after sending
      let res;
      if (isGroup) {
        res = await axios.get(`/api/message/group/get/all/${number}/${groupId}`);
      } else {
        res = await axios.get(`/api/message/onetoone/get/all/${number}/${groupId}`);
      }

      if (onMessagesUpdate) {
        onMessagesUpdate(res.data);
      }
    } catch (err) {
      console.error('Failed to send message:', err);
    }
  };

  const onEmojiClick = (emojiData) => {
    setInputValue((prev) => prev + emojiData.emoji);
    setShowEmojiPicker(false);
  };

  const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    const dateObj = new Date(dateString);
    return dateObj.toLocaleDateString(undefined, options);
  };

  // Sort messages oldest → newest
  const sortedMessages = [...messages].sort((a, b) => {
    const dateA = new Date(`${a.date}T${a.time}`);
    const dateB = new Date(`${b.date}T${b.time}`);
    return dateA - dateB;
  });

  let lastDate = null;

  return (
    <div
      style={{
        backgroundColor: '#121212',
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
          fontFamily: 'Arial, sans-serif',
          display: 'flex',
          flexDirection: 'column',
          height: '660px',
          boxSizing: 'border-box',
          backgroundColor: '#fff',
          position: 'relative',
          border: 'none',
          color: '#000',
          borderRadius: '8px',
          maxWidth: '650px',
          width: '100%',
          padding: '2rem',
          overflow: 'hidden',
        }}
      >
        {/* Header */}
        <header
          style={{
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            marginBottom: 24,
            borderBottom: '1px solid #ccc',
            paddingBottom: 8,
          }}
        >
          <div style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
            <h2 style={{ margin: 0 }}>{groupName}</h2>
            {isGroup && groupCode && <small style={{ color: '#666' }}>[{groupCode}]</small>}
          </div>
          <button
            onClick={() => navigate(-1)}
            style={{
              padding: '4px 8px',
              fontSize: 14,
              backgroundColor: '#f44336',
              color: 'white',
              border: 'none',
              borderRadius: 4,
              cursor: 'pointer',
              lineHeight: 1.2,
            }}
            aria-label="Go Back"
          >
            ← Back
          </button>
        </header>

        {/* Scrollable messages container */}
        <section
          ref={scrollContainerRef}
          style={{
            flexGrow: 1,
            overflowY: 'auto',
            paddingRight: 8,
            marginBottom: 16,
            display: 'flex',
            flexDirection: 'column',
          }}
        >
          {sortedMessages.length === 0 ? (
            <p style={{ textAlign: 'center', color: '#888' }}>No messages !</p>
          ) : (
            sortedMessages.map(({ messageId, myContent, myChat, isGroup, time, date }) => {
              const showDateHeader = lastDate !== date;
              lastDate = date;

              return (
                <React.Fragment key={messageId}>
                  {showDateHeader && (
                    <div
                      style={{
                        textAlign: 'center',
                        margin: '12px 0',
                        color: '#555',
                        fontSize: '0.7rem',
                        fontWeight: 500,
                        fontFamily: '"Inter", sans-serif',
                        padding: '6px 12px',
                        backgroundColor: '#f4f4f4ff',
                        borderRadius: '4px',
                        // border removed
                      }}
                    >
                      {formatDate(date)}
                    </div>
                  )}

                  <div
                    style={{
                      display: 'flex',
                      flexDirection: 'column',
                      alignItems: myChat ? 'flex-end' : 'flex-start',
                      marginBottom: 8,
                    }}
                  >
                    <div
                      style={{
                        maxWidth: '50%',
                        backgroundColor: myChat
                          ? '#0056b3'
                          : isGroup
                            ? '#cbcbd6ff'
                            : '#d4cfcfff',
                        color: myChat ? '#fff' : '#000',
                        padding: '8px 12px',
                        borderRadius: myChat ? '15px 15px 0 15px' : '15px 15px 15px 0',
                        boxShadow: '0 1px 2px rgba(0,0,0,0.2)',
                        fontSize: 14,
                        wordBreak: 'break-word',
                        display: 'flex',
                        alignItems: 'flex-end',
                        gap: 6,
                      }}
                    >
                      <span>{myContent}</span>
                      {time && (
                        <span
                          style={{
                            fontSize: '0.6rem',
                            color: myChat ? '#cfd8dc' : '#555',
                            whiteSpace: 'nowrap',
                            marginBottom: 1,
                            userSelect: 'none',
                          }}
                        >
                          {time.substring(0, 5)}
                        </span>
                      )}
                    </div>
                  </div>
                </React.Fragment>
              );
            })
          )}
        </section>

        {/* Input & Buttons */}
        <footer
          style={{
            display: 'flex',
            alignItems: 'center',
            gap: 8,
            position: 'relative',
          }}
        >
          <input
            type="text"
            placeholder="Type your message..."
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === 'Enter') handleSend();
            }}
            style={{
              flexGrow: 1,
              padding: '8px 12px',
              fontSize: 16,
              borderRadius: 4,
              border: '1px solid #ccc',
              outline: 'none',
            }}
            aria-label="Message input"
          />

          {/* Emoji toggle */}
          <button
            type="button"
            onClick={() => setShowEmojiPicker((v) => !v)}
            style={{
              padding: '6px 10px',
              fontSize: 20,
              backgroundColor: '#e5e5ea',
              border: 'none',
              borderRadius: 4,
              cursor: 'pointer',
              userSelect: 'none',
              lineHeight: 1,
            }}
            aria-label="Toggle emoji picker"
          >
            😊
          </button>

          {/* Send button */}
          <button
            onClick={handleSend}
            style={{
              padding: '8px 16px',
              fontSize: 16,
              backgroundColor: '#90ee90',
              color: 'black',
              border: 'none',
              borderRadius: 4,
              cursor: 'pointer',
              userSelect: 'none',
            }}
            aria-label="Send message"
          >
            Send
          </button>

          {/* Emoji picker popup */}
          {showEmojiPicker && (
            <div
              style={{
                position: 'absolute',
                bottom: '3.5rem',
                right: '3rem',
                zIndex: 1000,
              }}
            >
              <EmojiPicker onEmojiClick={onEmojiClick} />
            </div>
          )}
        </footer>
      </div>
    </div>
  );
};

export default Messages;
