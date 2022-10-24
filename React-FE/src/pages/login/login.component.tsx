import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { useHistory } from 'react-router-dom';
import * as Yup from 'yup';

import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import Alert from '@mui/material/Alert';
import Stack from '@mui/material/Stack';

import { LoginCredentials } from 'models/credentials.model';
import { login } from 'services/auth.service';

export function Login() {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const history = useHistory();

  const onSubmit = async (values: LoginCredentials) => {
    try {
      setIsLoading(true);
      setError('');

      const res = await login(values);

      localStorage.setItem('token', res.token);
      history.go(0);
    } catch (e) {
      setError('Invalid username or password!');
      setIsLoading(false);
    }
  };

  return (
    <Formik<LoginCredentials>
      initialValues={{ userName: '', password: '' }}
      validationSchema={Yup.object().shape({
        userName: Yup.string().required(),
        password: Yup.string().required(),
      })}
      validateOnChange={false}
      onSubmit={onSubmit}
    >
      <Form>
        <Box height="100vh" width="100vw" display="flex" alignItems="center" justifyContent="center" p={2}>
          <Grid container justifyContent="center">
            <Grid item xs={12} md={6} xl={4}>
              <Paper>
                <Box p={2}>
                  <Stack spacing={2}>
                    <Typography variant="h4">Login</Typography>
                    <Box>
                      <Field as={TextField} name="userName" label="User name" fullWidth />
                      <ErrorMessage name="userName" />
                    </Box>
                    <Box>
                      <Field as={TextField} type="password" name="password" label="Password" fullWidth />
                      <ErrorMessage name="password" />
                    </Box>
                    {error ? <Alert severity="error">{error}</Alert> : null}
                    <Button type="submit" variant="contained" fullWidth disabled={isLoading}>
                      Login
                    </Button>
                  </Stack>
                </Box>
              </Paper>
            </Grid>
          </Grid>
        </Box>
      </Form>
    </Formik>
  );
}
