import { useNavigate } from 'react-router-dom';

function ConnectButton() {
  const navigate = useNavigate();

  const handleConnect = async () => {
    try {
      const popup = window.open(`http://localhost:3001/auth/google`, 'googleLogin', 'height=800,width=600');
  
      // Listen for messages from the popup window
      window.addEventListener('message', event => {
        // If the event was not sent from the popup window, return
        if (event.source !== popup) {
          return;
        }
  
        // If the message type is 'access_token', set the access token in local storage and redirect to /
        console.log('Received message:', event.data);
        if (event.data.type === 'access_token') {
          const accessToken = event.data.data.accessToken;
          localStorage.setItem('access_token', accessToken);
          console.log('Access token stored in local storage:', accessToken);
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
