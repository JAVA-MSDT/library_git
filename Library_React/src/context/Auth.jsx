import decode from 'jwt-decode';

const Auth = () => {

  const token = localStorage.getItem('token');
  if (!token) {
    return false;
  }

  try {
    const { exp } = decode(token);

    if (exp < new Date().getTime() / 1000) {
      return false;
    }

  } catch (e) {
    return false;
  }

  return true;
}

export default Auth;
