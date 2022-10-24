import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage, FieldProps } from 'formik';
import * as Yup from 'yup';

import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Autocomplete, { AutocompleteRenderInputParams } from '@mui/material/Autocomplete/Autocomplete';

import { createUser, editUser } from 'services/users.service';
import { UserModel } from 'models/user.model';

export interface UserEditorProps {
  open: boolean;
  onClose: () => void;
  onSubmit: () => Promise<any>;
  data: UserModel | null;
}

export function UserEditor({ open, onClose, onSubmit: afterSubmit, data }: UserEditorProps) {
  const [isLoading, setIsLoading] = useState(false);

  const onSubmit = async (values: UserModel) => {
    try {
      setIsLoading(true);

      if (data && data.id) {
        await editUser(data.id, values);
      } else {
        await createUser(values);
      }

      await afterSubmit();
      onClose();
      setIsLoading(false);
    } catch (e) {
      setIsLoading(false);
    }
  };

  return (
    <Dialog maxWidth="md" fullWidth onClose={onClose} open={open}>
      <DialogTitle>{data?.id ? 'Edit user' : 'Add user'}</DialogTitle>
      <DialogContent>
        <Formik<UserModel>
          initialValues={{ email: '', name: '', role: 'USER', password: '', ...data } as UserModel}
          validationSchema={Yup.object().shape({
            email: Yup.string().email().required(),
            name: Yup.string().required(),
            role: Yup.string(),
          })}
          validateOnChange={false}
          onSubmit={onSubmit}
        >
          <Form>
            <Stack mt={1} spacing={2}>
              <Box>
                <Field as={TextField} name="email" label="Email" fullWidth />
                <ErrorMessage name="email" />
              </Box>
              <Box>
                <Field as={TextField} name="name" label="Name" fullWidth />
                <ErrorMessage name="name" />
              </Box>
              {!data?.id ? (
                <Box>
                  <Field as={TextField} type="password" name="password" label="Password" fullWidth />
                  <ErrorMessage name="password" />
                </Box>
              ) : null}

              <Box>
                <Field name="role">
                  {(props: FieldProps<string>) => (
                    <Autocomplete
                      options={['USER', 'ADMIN']}
                      value={props.field.value}
                      onChange={(e, value) => {
                        props.form.setFieldValue('role', value);
                      }}
                      renderInput={(params: AutocompleteRenderInputParams) => (
                        <TextField {...params} fullWidth label="Role" />
                      )}
                    />
                  )}
                </Field>
                <ErrorMessage name="role" />
              </Box>

              <Button type="submit" variant="contained" disabled={isLoading}>
                Save
              </Button>
            </Stack>
          </Form>
        </Formik>
      </DialogContent>
    </Dialog>
  );
}
