import { useNavigate } from 'react-router-dom';

const BACKEND_URL = 'http://localhost:3001'
function ConnectButton() {
  const navigate = useNavigate();

  async function refreshToken() {
    try {
      const currentAccessToken = localStorage.getItem('access_token');

      const response = await fetch(`${BACKEND_URL}/auth/refresh-token`, {
        headers: {
          'Authorization': `Bearer ${currentAccessToken}`
        }
      });
      const data = await response.json();
  
      // Update the access token in local storage
      localStorage.setItem('access_token', data.access_token);
      window.parent.console.log('refreshed token:', localStorage.getItem('access_token'));
    } catch (error) {
      console.error('Failed to refresh access token:', error);
    }
  }
  
  // Call the refreshToken function every 5 minutes
  setInterval(refreshToken, 5 * 60 * 1000);

  const handleConnect = async () => {
    try {
      const popup = window.open(`${BACKEND_URL}/auth/google`, 'googleLogin', 'height=800,width=600');
  
      // Listen for messages from the popup window
      window.addEventListener('message', event => {
        // If the event was not sent from the popup window, return
        if (event.source !== popup) {
          return;
        }
  
        // If the message type is 'access_token', set the access token in local storage and redirect to /
        console.log('Received message:', event.data);
        if (event.data.type === 'access_token') {
          const access_token = event.data.data.access_token;
          localStorage.setItem('access_token', access_token);
          window.parent.console.log('Access token stored in local storage:', access_token);
          navigate('/');
        }

      });
    } catch (error) {
      console.error(error);
    }
  };
  return (
    <button onClick={handleConnect}>
      Connect with Google
    </button>
  );
}  

export default ConnectButton;
