import { setLocale } from 'yup';

setLocale({
  mixed: {
    required: 'This field is required!',
  },
  string: {
    email: 'Invalid email address!',
    url: 'Invalid url format!',
  },
});
