import { useState } from 'react';
import { useLocation } from 'react-router-dom';


const Register = (props) => {
  const styles = {
    form: {
      fontFamily: 'Montserrat, sans-serif',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      width: '100%',
      maxWidth: '400px',
      margin: '0 auto',
      padding: '20px',
      backgroundColor: '#f7f7f7',
      borderRadius: '10px',
    },
    input: {
      fontFamily: 'Montserrat, sans-serif',
      width: '93%',
      padding: '10px',
      marginBottom: '10px',
      borderRadius: '5px',
      border: '1px solid #ddd',
    },
    select: {
      fontFamily: 'Montserrat, sans-serif',
      width: '100%',
      padding: '10px',
      marginBottom: '10px',
      borderRadius: '5px',
      border: '1px solid #ddd',
      backgroundColor: 'rgba(255, 255, 255)',
    },
    button: {
      fontFamily: 'Montserrat, sans-serif',
      width: '100%',
      padding: '10px',
      backgroundColor: '#4CAF50',
      color: 'white',
      fontSize: '16px',
      fontWeight: 'bold',
      borderRadius: '5px',
      border: 'none',
      cursor: 'pointer',
    },
    label: {
      fontSize:'20px',
      color: 'black',
      fontFamily: 'Montserrat, sans-serif',
    },
  };
  
  const newStyles = {
    container: {
      backgroundImage: 'url("https://images.pexels.com/photos/803975/pexels-photo-803975.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1")',
      backgroundSize: 'cover',
      backgroundPosition: 'center',
      backgroundRepeat: 'no-repeat',
      height: '100vh',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
    },
    card: {
      width: '320px',
      padding: '30px',
      borderRadius: '10px',
      backgroundColor: 'rgba(255, 255, 255, 0.7)',
      boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.3)',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    },
  };

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);

  const email = queryParams.get('email');
  const first_name = queryParams.get('first_name');
  const last_name = queryParams.get('last_name');
  const accessToken = queryParams.get('access_token');

  const [formData, setFormData] = useState({
    email: email || '',
    firstName: first_name || '',
    lastName: last_name || '',
    birthDate: '',
    phoneNumber: '',
    gender: '',
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await fetch(`https://backend.zain.ovh/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`,
        },
        body: JSON.stringify(formData),
      });
  
      const data = await response.json();
      console.log(data)
      if (response.ok) {
        // Send the access token to the main window using postMessage
        // Try one or the other if it doesn't work
        window.opener.postMessage({ type: 'access_token', data: { accessToken } }, "https://backend.zain.ovh/auth/google/callback");
        //window.parent.postMessage({ type: 'access_token', data: { accessToken } }, "https://backend.zain.ovh/auth/google/callback");
        
        // Close the popup window
        window.close();
      } else {
        // Handle error
      }
    } catch (error) {
      console.error(error);
    }
  };
  
  

  return (
    <div style={newStyles.container}>
      <div style={newStyles.card}>
        <form onSubmit={handleSubmit}>
        <label style={styles.label}>Email address</label>
            <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
            style={styles.input}
            />
            <label style={styles.label}>First name</label>
            <input
            type="text"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            required
            style={styles.input}
            />
            <label style={styles.label}>Last name</label>
            <input
            type="text"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
            required
            style={styles.input}
            />
            <label style={styles.label}>Birth date</label>
            <input
            type="date"
            name="birthDate"
            value={formData.birthDate}
            onChange={handleChange}
            required
            style={styles.input}
            />
            <label style={styles.label}>Phone number</label>
            <input
            type="tel"
            name="phoneNumber"
            value={formData.phoneNumber}
            onChange={handleChange}
            required
            style={styles.input}
            />
            <label style={styles.label}>Gender</label>
            <select name="gender" value={formData.gender} onChange={handleChange} required style={styles.select}>
                <option value="">Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Non-Binary">Non-Binary</option>
            </select>
            <button type="submit" style={styles.button}>Submit</button>
        </form>
      </div>
    </div>
  );
};

export default Register;
